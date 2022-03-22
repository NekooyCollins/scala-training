package rockthejvm.advanced.part2

object PartialFunctions extends App {
  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int

  class FunctionNotApplicableException extends RuntimeException
  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  } // partial function value

  println(aPartialFunction(2))
//  println(aPartialFunction(65535)) // crash

  // PF utilities
  // a very useful method to test if the parameter will crash the program or not
  println(aPartialFunction.isDefinedAt(67))

  // lift
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2))  // return 56
  println(lifted(98)) // return None

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }
  println(pfChain(2))   // return 56, from original partial function
  println(pfChain(45))  // return 67, from second partial function

  // PF extend normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // High order functions accept partial functions as well
  val aMappedList = List(1, 2, 3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }
  println(aMappedList)

  /*
  * Note: Partial Function can only have ONE parameter type
  * */

    // create a partialFunction without syntax sugar
  val pfInstance = new PartialFunction[Int, Int] {
    override def apply(n: Int): Int = n match {
      case 1 => 42
      case 2 => 56
      case 56 => 999
    }
    override def isDefinedAt(n: Int): Boolean =
      n == 1 || n == 2 || n == 56
  }

  val chatbot: PartialFunction[String, String] = {
    case "hello" => "Hi, I am your chatbot Holo9000"
    case "goodbye" => "you can't return anymore, human!"
    case "nice to meet you" => "welcome to the foundation!"
    case _ => "I can't understand your language"
  }
  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)
}
