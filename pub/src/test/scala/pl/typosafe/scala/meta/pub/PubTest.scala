package pl.typosafe.scala.meta.pub

import org.scalatest._
import scala.concurrent.duration._
import pl.typosafe.scala.meta.pub.menu._

import scala.concurrent.duration.Duration

/**
 * Author: Krzysztof Romanowski
 */
trait PubTest extends FlatSpec with Matchers {

  def createPub: Pub

  def testOrder(orders: Drink*): Unit = {
    val served = createPub.order(bt =>
      orders.foreach(bt.order)
    )
    served should contain theSameElementsAs orders
  }

  def testOrderAndTime(time: Duration)(orders: Drink*): Unit = {
    val beg = System.nanoTime()
    testOrder(orders: _*)
    val wholeTime = Duration.apply(System.nanoTime() - beg, NANOSECONDS)
    wholeTime should be < time
  }

  it should "gets orders correctly" in {
    testOrder(Ale, Porter)
    testOrder(Ale, Porter, Milk)
    testOrder(Ale, Ale, Ale)
    testOrder(Ale, Porter, Milk, Milk)
  }

  it should "not make to slow" in {
    testOrderAndTime(45 millis)(Ale)
    testOrderAndTime(125 millis)(Ale, Porter, Milk)
    testOrderAndTime(65 millis)(Milk, Milk, Milk)
  }

  it should "fail on missing menu entry" in {
    an[Exception] should be thrownBy testOrder(Pils)
  }
}

trait NiceErrorTests {
  self: PubTest =>

  it should "fail nicely" in {
    an[NoSuchDrinkInMenu] should be thrownBy testOrder(Pils)
  }
}

trait PubSpeedTests {
  self: PubTest =>

  it should "make drink fast" in {
    testOrderAndTime(100 millis)(Ale, Ale, Ale, Ale)
    testOrderAndTime(100 millis)(Ale, Porter, Milk, Ale, Porter, Milk)
    testOrderAndTime(100 millis)(Milk, Milk, Milk, Milk, Milk, Milk, Milk, Milk, Milk, Milk, Milk, Milk)
  }
}

class BoringPubTest
  extends PubTest
  with NiceErrorTests
  with PubSpeedTests {
  override def createPub: Pub = new BoringScalaPub
}

