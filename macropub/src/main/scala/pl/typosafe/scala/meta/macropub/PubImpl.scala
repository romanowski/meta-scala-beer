package pl.typosafe.scala.meta.macropub

import pl.typosafe.scala.meta.pub.Bartender
import pl.typosafe.scala.meta.pub.menu.Drink
import scala.reflect.macros.whitebox.Context

object PubImpl {


  def pubMacro(c: Context)(makeOrder: c.Expr[(Bartender) => Unit]): c.Expr[Seq[Drink]] = {
    import c.universe._
    c.Expr(q"Seq[Drink]()")

  }
}
