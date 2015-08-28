package pl.typosafe.scala.meta.macropub

import java.util.NoSuchElementException

import pl.typosafe.scala.meta.customer.{HasId, CustomerWithId, Customer}
import pl.typosafe.scala.meta.pub.{ToYoungToDrink, NoSuchDrinkInMenu, Pub}
import pl.typosafe.scala.meta.pub.Pub.Bartender
import pl.typosafe.scala.meta.pub.menu.{UnderAge, NoId, Alcoholic, Drink}
import scala.reflect.macros.whitebox.Context

object MacroPub {

  def checkAge(customer: Option[Customer], drink: Drink): Drink = drink match {
    case a: Alcoholic =>
      customer match {
        case Some(withId: HasId) =>
          if ( withId.ageFromId >= 18) drink
          else throw new ToYoungToDrink(withId.ageFromId)
        case _ => throw NoId
      }
    case _ => drink
  }


  def pubMacro(c: Context)(makeOrder: c.Expr[(Bartender) => Unit]): c.Expr[Seq[Drink]] = {
    import c.universe._

    object Order {
      def isOrder(func: Tree) = func.symbol match {
        case ms: MethodSymbolApi =>
          ms.name.toString == "order"
      }

      def unapply(t: Tree): Option[(Tree, Tree)] = t match {
        case Apply(func, List(drink)) if isOrder(func) =>
          Some((func, drink))
        case _ => None
      }
    }


    val customer = c.prefix.tree match {
      case Apply(_, List(customerFromContext)) =>
          q"Option($customerFromContext)"
      case _ =>
        q"None"
    }

    val transformer = new Transformer {
      override def transform(tree: c.universe.Tree): c.universe.Tree = tree match {
        case Order(func, drink) =>
          val newDrinkOrder = q"""
                                  if (Pub.serves.contains($drink))
                                    MacroPub.checkAge($customer, $drink)
                                  else throw new NoSuchDrinkInMenu($drink)
                              """
          c.typecheck(Apply(super.transform(func), List(newDrinkOrder)))
        case _ => super.transform(tree)
      }
    }

    val newOrder = transformer.transform(makeOrder.tree)

    c.Expr(
      q"""
         val bt = Pub.askBartender()
         $newOrder(bt)
         Pub.finalizeOrder(bt)
       """)

  }
}

