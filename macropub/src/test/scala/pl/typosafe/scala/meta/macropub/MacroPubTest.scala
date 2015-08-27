package pl.typosafe.scala.meta.macropub

import org.scalatest.{ Matchers, FlatSpec }
import pl.typosafe.scala.meta.pub.menu._
import pl.typosafe.scala.meta.pub._
import scala.language.experimental.macros
import scala.concurrent.duration._

/**
 * Author: Krzysztof Romanowski
 */
class Macropub extends Pub {
  def order(makeOrder: (Bartender) => Unit): Seq[Drink] = macro PubImpl.pubMacro
}

class MacroPubTest extends FlatSpec with Matchers {
  def createPub: Macropub = new Macropub


  def testOrder(orders: Drink*)(served: => Seq[Drink]): Unit = {
    val drinksServed = served
    drinksServed should contain theSameElementsAs orders
  }

  def testOrderAndTime(time: Duration)(orders: Drink*)(served: => Seq[Drink]): Unit = {
    val beg = System.nanoTime()
    testOrder(orders: _*)(served)
    val wholeTime = Duration.apply(System.nanoTime() - beg, NANOSECONDS)
    wholeTime should be < time
  }


  it should "make drink fast" in {
    testOrderAndTime(100.millis)(Ale, Ale) {
      createPub.order {
        bt =>
          bt.order(Ale)
          bt.chat(30.millis)
          bt.order(Ale)
      }
    }

    testOrderAndTime(100.millis)(Ale, Porter, Milk, Ale, Porter, Milk) {
      createPub.order {
        bt => bt.order(Ale)
          bt.order(Porter)
          bt.order(Milk)
          bt.order(Ale)
          bt.order(Porter)
          bt.order(Milk)
      }
    }
    testOrderAndTime(100.millis)(Ale, Ale, Ale, Ale) {
      createPub.order {
        bt =>
          bt.order(Ale)
          bt.order(Ale)
          bt.order(Ale)
          bt.order(Ale)
      }
    }
    testOrderAndTime(100.millis)(Ale, Porter, Milk, Ale, Porter, Milk) {
      createPub.order {
        bt =>
          bt.order(Ale)
          bt.order(Porter)
          bt.order(Milk)
          bt.order(Ale)
          bt.order(Porter)
          bt.order(Milk)
      }
    }
  }

  it should "gets orders correctly" in {
    testOrder(Ale, Porter) {
      createPub.order {
        bt =>
          bt.order(Ale)
          bt.order(Porter)
      }
    }
    testOrder(Ale, Porter, Milk) {
      createPub.order {
        barTender =>
          println(barTender.order(Ale))
          Seq(Porter, Milk).foreach(barTender.order)
      }
    }
    testOrder(Ale)(createPub.order(_.order(Ale)))

  }

  it should "chat correctly" in {
    /*testOrderAndTime(45 millis)(Ale)
    testOrderAndTime(125 millis)(Ale, Porter, Milk)
    testOrderAndTime(65 millis)(Milk, Milk, Milk)*/
  }

  it should "fail on missing menu entry" in {
    an[Exception] should be thrownBy testOrder() {
      createPub.order(_.order(Pils))
    }
  }

  it should "fail nicely" in {
    an[NoSuchDrinkInMenu] should be thrownBy testOrder() {
      createPub.order(_.order(Pils))
    }
  }


}