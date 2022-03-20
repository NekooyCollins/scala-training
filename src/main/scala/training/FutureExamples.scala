package training

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future, blocking}
import scala.util.{Failure, Success}

object FutureExamples {
  // --------------------------------------------------
  // Basic usage
  // --------------------------------------------------

  // Takes long to run and is unsafe
  def slowComputation(a: Int, b: Int): Int = {
    Thread.sleep(2000) // I'm pretending to be working
    a / b // Actually doing my job
  }

  // Takes even longer to run
  def doTheMath(): Unit = {
    val beginning = System.currentTimeMillis()

    val first = slowComputation(2, 1)
    val second = slowComputation(4, 2)
    val third = slowComputation(6, 3)

    val end = System.currentTimeMillis()

    println(
      s"I'm done! It took me ${end - beginning} ms to finish. " +
        s"The result is: ${first + second + third}"
    )
  }

  // Let's make it asynchronous
  def slowComputationAsync(a: Int, b: Int): Future[Int] = Future {
    slowComputation(a, b)
  }

  // Getting the result
  val result = Await.result(slowComputationAsync(4, 1), Duration.Inf)
  println(s"Result is $result")

  // What if it fails?
  Await.ready(slowComputationAsync(4, 0), Duration.Inf).foreach(i => println(s"Result is $i"))

  // Or times out?
  Await.ready(slowComputationAsync(4, 0), 1.second).foreach(i => println(s"Result is $i"))

  // Fatal exception
  def fatalExceptionInsideFuture(): Future[Int] = {
    def fun(): Int = fun() + fun()

    Future(fun())
  }

  Await
    .ready(fatalExceptionInsideFuture(), Duration.Inf)
    .onComplete {
      case Success(value) => println(s"$value")
      case Failure(value) => println(s"$value")
    }

  // Can we chain async computations?
  def slowComputationsChained(): Future[Int] =
    slowComputationAsync(2, 1).flatMap(result => Future(result + 1))

  // How are failures handled when chaining the futures?
  def slowComputationsChainedFailures(): Future[Int] =
    slowComputationAsync(8, 0).flatMap { result =>
      println(result)
      slowComputationAsync(result, 2)
    }

  // Let's rewrite the doTheMath method to run in parallel and return a result
  def doTheMathInParallel(): Future[Int] = {
    val beginning = System.currentTimeMillis()

    // Will be executed sequentially when moved to for comprehension
    val firstFuture = slowComputationAsync(2, 1)
    val secondFuture = slowComputationAsync(4, 2)
    val thirdFuture = slowComputationAsync(6, 3)

    for {
      first <- firstFuture
      second <- secondFuture
      third <- thirdFuture
    } yield {
      val end = System.currentTimeMillis()
      println(
        s"I'm done! It took me ${end - beginning} ms to finish. " +
          s"The result is: ${first + second + third}"
      )
      first + second + third
    }
  }

  // Question!
  def orderOfCallbacks(): Future[Unit] = {
    val result = Future[Unit](println(s"Future is executed on ${Thread.currentThread()}"))
      .map(_ => println(s"map 1 is executed on ${Thread.currentThread()}"))
      .map(_ => println(s"map 2 is executed on ${Thread.currentThread()}"))
      .map(_ => println(s"map 3 is executed on ${Thread.currentThread()}"))
      .flatMap(_ => Future(println(s"flatmap 1 is executed on ${Thread.currentThread()}")))
      .flatMap(_ => Future(println(s"flatmap 2 is executed on ${Thread.currentThread()}")))
      .flatMap(_ => Future(println(s"flatmap 3 is executed on ${Thread.currentThread()}")))

    result.foreach(_ => println(s"foreach 1, executed on ${Thread.currentThread()}"))
    result.foreach(_ => println(s"foreach 2, executed on ${Thread.currentThread()}"))
    result.foreach(_ => println(s"foreach 3, executed on ${Thread.currentThread()}"))
    result
  }

  // --------------------------------------------------
  // Fun stuff
  // --------------------------------------------------

  // Without blocking we'd risk running out of global pool threads
  def verySlowComputation(id: Int): Future[Unit] = {
    Future {
      blocking {
        (1 to 15).foreach { i =>
          println(s"Future $id doing the iteration $i (${Thread.currentThread()})")
          Thread.sleep(1000)
        }
      }
    }
  }

  (1 to 100).foreach(verySlowComputation)
  Thread.sleep(1000000)

}
