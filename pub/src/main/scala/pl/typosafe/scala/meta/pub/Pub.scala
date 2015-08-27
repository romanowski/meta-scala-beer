package pl.typosafe.scala.meta.pub

import pl.typosafe.scala.meta.pub.menu.{ Milk, Porter, Ale, Drink }

import scala.collection.SortedMap
import scala.concurrent.duration.Duration
import scala.concurrent.duration._

abstract class Pub() {

  def serves: Map[Drink, Duration] = Map(
    Ale -> 40.millis,
    Porter -> 60.millis,
    Milk -> 20.millis
  )

  private[pub] final def prepare(drink: Drink): Unit = {
    val timeToPrepare = serves(drink).toMillis
    Thread.sleep(timeToPrepare)
  }

  protected final def askBartender = new Bartender(this)
}

case class NoSuchDrinkInMenu(drink: Drink) extends RuntimeException(s"$drink: No such drink in menu. Sorry")

class ToYoungToDrink(age: Int) extends RuntimeException(s"Having $age years is too less to order alcohol")

class UnknownAge extends RuntimeException("No id to prove age.")