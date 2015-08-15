name := "meta-scala-beer"

version := "1.0"

lazy val pub = project

lazy val customer = project aggregate pub
    