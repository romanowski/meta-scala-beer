package pl.typosafe.scala.meta.pub

import pl.typosafe.scala.meta.pub.menu._
import scala.concurrent.duration._

class BoringScalaPub extends Pub(Map(
  Ale -> 4.seconds,
  Porter -> 10.seconds,
  Milk -> 2.seconds
)) {
  override def order(makeOrder: (Bartender) => Seq[Drink]): Seq[Drink] = {
    val bartender = askBartender
    makeOrder(bartender)
    bartender.wholeOrder
  }
}
