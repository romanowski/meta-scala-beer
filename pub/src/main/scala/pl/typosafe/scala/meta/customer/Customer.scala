package pl.typosafe.scala.meta.customer

import pl.typosafe.scala.meta.pub.menu.Drink
import pl.typosafe.scala.meta.pub.{ Bartender, Pub }

/**
 * Author: Krzysztof Romanowski
 */
trait Customer {
  def wants: Drink
}

trait HasId {
  def ageFromId: Int
}

trait HasPassport {
  def ageFromPassport: Int

  def age = 25
}

case class AnyCustomer(wants: Drink) extends Customer

case class LocalCustomer(wants: Drink, age: Int) extends Customer

case class CustomerWithId(wants: Drink, ageFromId: Int) extends Customer with HasId

case class CustomerWithPassport(wants: Drink, ageFromPassport: Int) extends Customer with HasPassport

