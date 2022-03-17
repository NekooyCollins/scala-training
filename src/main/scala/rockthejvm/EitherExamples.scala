package rockthejvm

object EitherExamples extends App {

  val eLeft: Either[String, Int] = Left("foo")
  val eRight: Either[String, Int] = Right(1)

  def printEither(e: Either[String, Int]): Unit = e match {
    case Left(lvalue) => println(s"left: $lvalue")
    case Right(rvalue) => println(s"right: $rvalue")
  }

  println(eRight.map((i: Int) => i * 2)) // Right(2)
  println(eLeft.map((i: Int) => i * 2)) // Left(foo)
  println(eRight.flatMap { (x: Int) => // Right(2) = Right(1) + Right(1)
    eRight.map { (y: Int) => x + y }
  })

  println(s"$eLeft, ${eRight.toOption}") // Left(foo), Some(1)
  println(s"${eLeft.toOption}, $eRight") // None, Right(1)

  val some = Some(1)
  val none = None

  val r = some.toRight("foo")
  println(s"${some.toRight("foo")}") // Right(1)
  println(s"${some.toLeft("foo")}") // Left(1)

  // Option[Option[Int]]
  val ee: Either[String, Either[String, Int]] = Right(Right(1))
  println(s"Unflattened: $ee") // Unflattened: Right(Right(1))
  println(s"Flattened: ${ee.flatten}") // Flattened: Right(1)

  println(eLeft.left.map((s: String) => s.toUpperCase())) // Left(FOO)
  println(eRight.left.map((s: String) => s.toUpperCase())) // Right(1)

  println(eLeft.left.flatMap((x: String) => eLeft.left.map((y: String) => x + y))) // Left(foofoo)
}
