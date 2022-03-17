package rockthejvm

import scala.io.Source
import scala.util.{Failure, Success, Try}

object TryExample extends App {

  def divideTryCheck(a: Int, b: Int): Int = {
    try {
      a / b
    } catch {
      case _: Exception => 0
    } finally {
      println("it works!")
    }
  }

  def devideByEither(a: Int, b: Int): Either[Throwable, Int] = {
    if (b != 0) Right(a / b)
    else Left(new IllegalArgumentException())
  }

  def divideByTry(a: Int, b: Int): Try[Int] = Try(a / b)

  divideByTry(2, 0) match {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
  println(divideByTry(2, 0))
  println(divideByTry(2, 1))


  def printResourceSafe(resourceName: String): Try[Seq[String]] = {
    Try(Source.fromResource(resourceName)).map(_.getLines().toSeq)
  }

  def concatenateFilesSafe(f1: String, f2: String): Try[Seq[String]] = {
    Try(Source.fromResource(f1)).map(_.getLines().toSeq).flatMap { line1 =>
      Try(Source.fromResource(f2)).map(_.getLines().toSeq).map { line2 =>
        line1 ++ line2
      }
    }
  }

  def concatenateFilesSafeForComprehension(f1: String, f2: String): Try[Seq[String]] = {
    for {
      file1 <- Try(Source.fromResource(f1))
      file2 <- Try(Source.fromResource(f2))
    } yield (file1.getLines() ++ file2.getLines()).toSeq
  }
}
