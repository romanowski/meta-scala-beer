# Meta Scala workshop.

Let's create scala pub with macros!

## Rules

We can only change files in:

* pluginsPub/src/main/scala
* macroPubs/src/test/scala


Basically only following files are changed:

* MacroPub.scala 
* IdMakerPlugin.scala

Your goal is to make all tests pass :)

Workshop is split into sequence of task. Ones makred as "optional" can be skipped.
Most tasks have implemented solution. Please check task1, task2, taskN branches (but try first!)

## Setup

Checkout master from https://github.com/romanowski/meta-scala-beer and import workspace to intellij.

Run sbt and play with code!

Useful aliases (I strongly recommend using only then):

* testPub - basic command - cleans, compiles and run tests
* initCompilerPlugin - inits compiler plugin (build plugin jar and restart sbt to pick it up)
* runCompilerPlugin - builds plugin and then use it to compile code
* testCompilerPlugin - as runCompilerPlugin and later run tests

# Tasks

## 1. Implement macro pub story

our goal is to make this code work and compile:

```scala
 story(new CustomerWithId(23)).order {
        bt =>
          bt.order(Ale)
          bt.chat(30.millis)
          bt.order(Ale)
      }
```

To create bartender following methods should be called:
* Pub.createBartender()
* order drinks and chat with bartender
* return result of Pub.finalizeOrder

All this work should be done inside macro.

## 2. Fix error that is thrown when customer orders drink that is not in menu

Also to do in macro.

Beware: This exception contains non existing drink.
Hit: If compilation fails with exceptions "AssertionFailed" that you can typecheck your new code first.

## 3. Add exceptions when child try to buy alcohol

Our macro based order method is always called in such story method context.
First parameter of method call is user so it can be used.

In macros.

## 4. Customers with Passport should be able to buy alcohol

This time please use compiler plugin. HasPassport trait should extends HasId.

## 5.  Test more advanced cases

Just merge branch "advanceTests" and see if all test pass. Fix it or move forward

## 6. Bonus task: Please make order mechanism asychronous