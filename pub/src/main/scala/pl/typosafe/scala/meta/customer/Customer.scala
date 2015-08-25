package pl.typosafe.scala.meta.customer

import pl.typosafe.scala.meta.pub.menu.Drink
import pl.typosafe.scala.meta.pub.{ Bartender, Pub }

/**
 * Author: Krzysztof Romanowski
 */
class Customer(pub: Pub)(wants: Seq[Drink]) {
  final def goToPub(): Seq[Drink] = pub.order(bt => wants.foreach(bt.order))
}

trait HasId {
  def ageFromId: Int
}

trait HasPassport {
  def ageFromPassport: Int
}
