package pl.typosafe.scala.meta.pub

import pl.typosafe.scala.meta.pub.menu.Drink

import scala.collection.SortedMap
import scala.concurrent.duration.Duration

abstract class Pub(serves: Map[Drink, Duration]) {

  var log: SortedMap[Long, Drink] = SortedMap.empty

  def menu = serves.keySet

  private[pub] final def prepare(drink: Drink): Unit = {
    log += (System.nanoTime() -> drink)
    Thread.sleep(serves(drink).toMillis)
  }

  def askBartender = new Bartender(this)

  def order(makeOrder: Bartender => Seq[Drink]): Seq[Drink]
}
