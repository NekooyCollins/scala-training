package rockthejvm.advanced

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

object AsyncNonBlocking {

  // synchronous, blocking
  def blockingFunction(arg: Int): Int = {
    Thread.sleep(10000)
    arg + 42
  }

  blockingFunction(3) // blocking call
  val theMeaningOfLife = 42 // wait for 10 seconds

  // asynchronous, blocking call
  def asyncBlockingFunction(arg: Int): Future[Int] = Future {
    Thread.sleep(10000)
    arg + 42
  }

  asyncBlockingFunction(3)
  val anotherMeaningOfLife = 42 // evaluates immediately

  // asynchronous, NON-blocking
  // what an actor will do: given a message of type String,
  // it will print something out, and the actor will resume to its same behavior.
  def createSimpleActor(): Behaviors.Receive[String] = Behaviors.receiveMessage[String] { someMessage =>
    println(s"Received something: $someMessage")
    Behaviors.same
  }

  val rootActor = ActorSystem(createSimpleActor(), "TestSystem")
  rootActor ! "messgae in a bottle" // enqueuing a message, asynchronous, non-blocking

  // This actor will complete a promise when it receives a message.
  val promiseResolver: ActorSystem[(String, Promise[Int])] = ActorSystem(
    Behaviors.receiveMessage[(String, Promise[Int])] {
      case (message, promise) => promise.success(message.length)
        Behaviors.same
    },
    "PromiseResolver"
  )

  def doAsyncNonBlockingComputation(s: String): Future[Int] = {
    val aPromise = Promise[Int]()
    promiseResolver ! (s, aPromise)
    aPromise.future
  }

  val asyncNonBlockingResult: Future[Int] = doAsyncNonBlockingComputation("Some message")
  asyncNonBlockingResult.onComplete(value => s"I've got a non-blocking async answer: $value")

  def main(args: Array[String]): Unit = {
    ???
  }
}
