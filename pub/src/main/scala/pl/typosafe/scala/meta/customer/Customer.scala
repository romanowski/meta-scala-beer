package pl.typosafe.scala.meta
package customer

class Customer {
}

trait HasId {
  def ageFromId: Int
}

trait HasPassport {
  def ageFromPassport: Int

  def age = 25
}

case class LocalCustomer(age: Int) extends Customer

case class CustomerWithId(ageFromId: Int) extends Customer with HasId

case class CustomerWithPassport(ageFromPassport: Int) extends Customer with HasPassport

