package pl.typosafe.scala.meta.pub.customer

import org.scalatest.{ Matchers, FlatSpec }
import pl.typosafe.scala.meta.customer._
import pl.typosafe.scala.meta.pub.{ UnknownAge, BoringScalaPub, ToYoungToDrink, Pub }
import pl.typosafe.scala.meta.pub.menu._

case class AnyCustomer(pub: Pub)(wants: Seq[Drink]) extends Customer(pub)(wants)

case class AdultWithPassport(pub: Pub)(wants: Seq[Drink]) extends Customer(pub)(wants) with HasPassport {
  override def ageFromPassport: Int = 22
}

case class ChildWithPassport(pub: Pub)(wants: Seq[Drink]) extends Customer(pub)(wants) with HasPassport {
  override def ageFromPassport: Int = 16
}

case class AdultWithId(pub: Pub)(wants: Seq[Drink]) extends Customer(pub)(wants) with HasId {
  override def ageFromId: Int = 25
}

case class ChildWithId(pub: Pub)(wants: Seq[Drink]) extends Customer(pub)(wants) with HasId {
  override def ageFromId: Int = 15
}


trait CustomerTest extends FlatSpec with Matchers {

  def createPub: Pub

  def testCustomer(c: Pub => Seq[Drink] => Customer)(drinks: Seq[Drink]) = {
    c(createPub)(drinks).goToPub() should contain theSameElementsAs (drinks)
  }

  it should "serves milk to anyone" in {
    testCustomer(AnyCustomer.apply)(Seq(Milk))
    testCustomer(AnyCustomer.apply)(Seq(Milk))
    testCustomer(AnyCustomer.apply)(Seq(Milk))
    testCustomer(AnyCustomer.apply)(Seq(Milk))
  }

}

trait AlcoholRestrictedTest {
  self: CustomerTest =>

  it should "not serves alcohol to children with Id" in {
    an[ToYoungToDrink] should be thrownBy testCustomer(ChildWithId.apply)(Seq(Ale))
  }

  it should "serves alcohol to adults with" in {
    testCustomer(AdultWithId.apply)(Seq(Ale))
  }

  it should "not serves alcohol to people without proven age" in {
    an[UnknownAge] should be thrownBy testCustomer(AnyCustomer.apply)(Seq(Ale))
  }

}

trait AlcoholRestrictedTestWithPassports {
  self: CustomerTest =>

  it should "not serves alcohol to children" in {
    an[ToYoungToDrink] should be thrownBy testCustomer(ChildWithPassport.apply)(Seq(Ale))
  }

  it should "serves alcohol to adults" in {
    testCustomer(AdultWithPassport.apply)(Seq(Ale))
  }
}

class BoringPubCustomerTest extends CustomerTest
with AlcoholRestrictedTest
with AlcoholRestrictedTestWithPassports {
  override def createPub: Pub = new BoringScalaPub
}