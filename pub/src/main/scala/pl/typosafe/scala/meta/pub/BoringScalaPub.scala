package pl.typosafe.scala.meta.pub

import pl.typosafe.scala.meta.pub.menu._

class BoringScalaPub extends Pub() {
  override def order(makeOrder: (Bartender) => Unit): Seq[Drink] = {
    val bartender = askBartender
    makeOrder(bartender)
    bartender.wholeOrder
  }
}
