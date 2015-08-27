package pl.typosafe.scala.meta.pub

import pl.typosafe.scala.meta.pub.menu.Drink

import scala.concurrent.duration.Duration


final class Bartender(pub: Pub) {

  private var orders: Seq[Drink] = Nil

  def order(drink: Drink): Unit = {
    pub.prepare(drink)
    orders = drink +: orders
  }

  def chat(time: Duration) = Thread.sleep(time.toMillis)

  private[pub] def wholeOrder: Seq[Drink] = orders
}
