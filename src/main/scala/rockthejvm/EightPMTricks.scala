package rockthejvm

object EightPMTricks {
  val aNumber = 44
  val ordinal = aNumber match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "thrid"
    case _ => aNumber + "th"
  }

  case class Person(name: String, age:Int)
  val Bob = Person("Bob", 34)
  val bobGreeting = Bob match {
    case Person(n, a) => s"I am $n and I am $a years old"
  }

  // trick #1 -- list extractors
  val numberList = List(1, 2, 3, 42)
  val mustHaveThree = numberList match {
    case List(_, _, 3, somethingElse) => s"the list contains 3 and the 4th is $somethingElse"
  }

  // trick #2 -- Haskell-like prepending
  val startsWithOne = numberList match {
    case 1 :: tail => s"This lists starts with one, the rest is $tail"
  }

  def processList(aList: List[Int]): String = aList match {
    case Nil => ""
    case head :: tail => s"list starts with $head, tail is $tail"
  }

  // trick #3 -- List vararg pattern(arbitrary length)
  val dontCareAboutTheRest = numberList match {
    case List(_, 2, _*) => "I only care that this list has 2 as second element"
  }

  // trick #4 -- Other list infix patterns
  val mustEndWithMeaningOfLife = numberList match {
    case List(1,2,_) :+ 42 => "found the meaning of life!"
  }



  def main(args: Array[String]): Unit = {

  }
}
