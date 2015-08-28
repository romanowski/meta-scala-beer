package pl.typosafe.scala.meta.macropub

import pl.typosafe.scala.meta.pub.Pub.Bartender
import pl.typosafe.scala.meta.pub.menu.Drink

import scala.reflect.macros.whitebox.Context

object MacroPub {

  def pubMacro(c: Context)(makeOrder: c.Expr[(Bartender) => Unit]): c.Expr[Seq[Drink]] = {
    import c.universe._
    c.Expr(
      q"""
         val bt = Pub.askBartender()
         $makeOrder(bt)
         Pub.finalizeOrder(bt)
       """)
  }
}

