package pl.typosafe.scala.meta.macropub

import org.scalatest.{Matchers, FlatSpec}
import pl.typosafe.scala.meta.customer._
import pl.typosafe.scala.meta.pub.Pub.Bartender
import pl.typosafe.scala.meta.pub.menu._
import pl.typosafe.scala.meta.pub._
import scala.language.experimental.macros
import scala.concurrent.duration._

class MultiCustomer(age: Int) extends Customer with HasPassport{
  override def ageFromPassport: Int = age - 15

  def ageFromId: Double = age
}

class AdvancedMacroPubTest extends FlatSpec with Matchers {

  def story(who: Customer) = new MacroPubStory(who)

  def testOrder(orders: Drink*)(served: => Seq[Drink]): Unit = {
    val drinksServed = served
    drinksServed should contain theSameElementsAs orders
  }

  def makeOrder: Bartender => Drink => Unit = bt => drink => bt.order(drink)

  it should "gets orders correctly" in {
    testOrder(Ale, Porter) {
      story(new CustomerWithId(23)).order {
        bt =>
          makeOrder(bt)(Ale)
          makeOrder(bt)(Ale)
      }
    }
    testOrder(Ale, Porter, Milk) {
      story(new CustomerWithId(23)).order {
        barTender =>
          println(barTender.order(Ale))
          Seq(Porter, Milk).foreach(makeOrder(barTender))
      }
    }
    testOrder(Ale, Porter)(story(new CustomerWithId(23)).order(_.order(Ale)))
    testOrder(Ale, Porter) {
      story(new CustomerWithId(23)).order {
        bt =>
          bt.order {
            bt.order(Ale)
            Porter
          }
      }
    }
  }


  it should "fail nicely" in {
    an[NoSuchDrinkInMenu] should be thrownBy testOrder() {
      story(new CustomerWithId(23)).order(_.order(Pils))
      story(new CustomerWithId(23)).order(bt => bt.order {
        makeOrder(bt)(Pils)
        Ale
      })
      story(new CustomerWithId(23)).order(makeOrder(_)(Pils))
    }
  }

  it should "check age from Id" in {
    var custCount = 0
    def cust(age : Int) = {
      custCount += 1
      new CustomerWithId(age)
    }


    an[ToYoungToDrink] should be thrownBy testOrder() {
      story(cust(11)).order(_.order(Ale))
    }
    story(cust(21)).order(_.order(Ale))
    story(cust(12)).order(_.order(Milk))

    custCount should equal(3)
  }


  it should "check age from Id 3" in {
    an[ToYoungToDrink] should be thrownBy testOrder() {
      story(new CustomerWithId(12)).order(makeOrder(_)(Ale))
    }
    story(new CustomerWithId(21)).order(makeOrder(_)(Ale))
    story(new CustomerWithId(12)).order(makeOrder(_)(Ale))
  }


  it should "check age from Id 2" in {

    def testStory(age: Int) = story(new CustomerWithId(age))
    an[ToYoungToDrink] should be thrownBy testOrder() {
      testStory(11).order(_.order(Ale))
    }
    testStory(21).order(_.order(Ale))
    testStory(12).order(_.order(Milk))
  }

  it should "check age from id not from passport" in {
    story(new MultiCustomer(23)).order(_.order(Ale))
  }
}