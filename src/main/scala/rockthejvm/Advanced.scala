package rockthejvm

import scala.concurrent.Future
import scala.util._
import scala.concurrent.ExecutionContext.Implicits.global

object Advanced extends App {

  /** lazy evaluation **/
  lazy val aLazyValue = 2

  // nothing will be printed out here
  lazy val lazyValueWithSideEffect  ={
    println("I am so lazy!")
    43
  }

  // I am so lazy! -- print now
  val eagerValue = lazyValueWithSideEffect + 1

  /** pseudo-collections: Option, Try **/
  def methodWhichCanReturnNull(): String = "hello, scala!"

  // usually we do not use null in scala, but Option
  val anOption = Option(methodWhichCanReturnNull()) // Some("hello, scala")

  // option = "collection" which contains at most one element: Some(value) or None
  // checking null or not null with Option & pattern match, instead of if/else
  val stringProcessing = anOption match {
    case Some(string) => s"I have obtained a valid string: $string"
    case None => "I obtained nothing"
  }

  def methodWithCanThrowException(): String = throw new RuntimeException
  // "collection" with either a value if the code went well, or an exception
  val aTry = Try(methodWithCanThrowException())

  val anotherStringProcesssing = aTry match {
    case Success(value) => s"I have obtained a valid string: $value"
    case Failure(exception) => s"i have obtained an exception: $exception"
  }

  /** Evaluate something on another thread (asynchronous programming) **/

  // future is a collection which contains a value when it's evaluated
  // future is composable with map, flatMap and filter
  val aFuture = Future {
    println("Loading...")
    Thread.sleep(1000)
    println("Computation finished")
    67
  }

  /** Implicits basics **/

  // #1: implicit arguments
  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val myImplicitInt: Int = 46
  println(aMethodWithImplicitArgs) // aMethodWithImplicitArgs(myImplicitInt) = 47

  // #2: implicit conversions
  implicit class MyRichInteger(n: Int) {
    def isEven() = n % 2 == 0
  }

  println(23.isEven()) // new MyRichInteger(23).isEven()
  // use this carefully!
}
