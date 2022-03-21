package rockthejvm.advanced

import scala.util.Try

object DarkSyntaxSugar extends App {
  // syntax sugar #1: method with single parameter
  def singleArgMethod(age: Int): String = s"$age little ducks..."

  val description = singleArgMethod {
    // write some complex code
    42
  }

  val aTryInstance = Try {
    throw new RuntimeException
  }

  // syntax sugar #2: single abstract method
  trait Action {
    // this is a single method trait
    def act(x: Int): Int
  }

  // general way
  val anInstance: Action = new Action {
    override def act(x: Int): Int = x + 1
  }
  val aFunkyInstance: Action = (x: Int) => x + 1  // lambda to single abstract type conversion

  // example Runnables
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hello, scala")
  })
  val aSweeterThread = new Thread(() => println("hello, sweeter scala!"))

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(i: Int): Unit
  }
  val anAbstractInstance: AnAbstractType = (a: Int) => println("sweet~")

  // syntax sugar #3: the :: and #:: methods are special
  val prependedList = 2 :: List(3, 4)
  // 2.::(List(3,4))
  // List(3,4).::2

  // scala spec: last char decides the associativity of method
  val result = 1 :: 2 :: 3 :: List(4, 5) // execute from right to left, reversely

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this
  }
  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // syntax sugar #4: multi-word method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }
  val Lilly = new TeenGirl("Lilly")
  Lilly `and then said` "Scala is so sweet!" // == Lilly.`and then said`("Scala is so sweet!")

  // syntax sugar #5: infix types
  class Composite[A, B]
  val composite: Int Composite String = ??? //  val composite: Composite[Int, String] = ???

  class -->[A, B]
  val towards: Int --> String = ???

  // syntax sugar #6: update() is very special, much like apply(), usually used in mutable collections
  val anArray = Array(1, 2, 3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7)

  // syntax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation
    def member = internalMember // get method
    def member_=(value: Int): Unit = {
      internalMember = value  // set method
    }
  }
  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewrittern as aMutableContainer.member_=(42)
}
