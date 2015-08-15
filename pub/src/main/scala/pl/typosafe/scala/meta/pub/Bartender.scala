package pl.typosafe.scala.meta.pub

import pl.typosafe.scala.meta.pub.menu.Drink


final class Bartender(pub: Pub) {

  private var orders: Seq[Drink] = Nil

  def order(drink: Drink): Unit ={
    pub.prepare(drink)
    orders = drink +: orders
  }

  private[pub] def wholeOrder: Seq[Drink] = orders
}
