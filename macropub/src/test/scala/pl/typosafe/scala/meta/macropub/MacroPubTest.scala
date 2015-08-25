package pl.typosafe.scala.meta.macropub

import pl.typosafe.scala.meta.pub.menu.Drink
import pl.typosafe.scala.meta.pub.{ NiceErrorTests, PubTest, Bartender, Pub }

/**
 * Author: Krzysztof Romanowski
 */
class Macropub extends Pub {
  override def order(makeOrder: (Bartender) => Unit): Seq[Drink] = macro PubImpl.pubMacro
}

class MacroPubTest extends PubTest with NiceErrorTests