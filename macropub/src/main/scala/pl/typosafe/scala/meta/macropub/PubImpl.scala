package pl.typosafe.scala.meta.macropub

import pl.typosafe.scala.meta.pub.Bartender
import pl.typosafe.scala.meta.pub.menu.Drink


object PubImpl {
  import scala.reflect.macros.Context

  def pubMacro(c: Context)(makeOrder: c.Expr[(Bartender) => Unit]):c.Expr[Seq[Drink]] = ???
}
