package pl.typosafe.scala.meta.pub.menu

trait Alcoholic extends Drink

object Whisky extends Alcoholic

object Gin extends Alcoholic

object UnderAge extends RuntimeException("Under age!")

object NoId extends RuntimeException("No ID provided")
