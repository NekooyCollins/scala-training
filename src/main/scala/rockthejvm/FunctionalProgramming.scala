package rockthejvm

object FunctionalProgramming extends App {
  class Person(name: String) {
    def apply(age: Int) = println(s"I have aged $age years")
  }

  val bob = new Person("Bob")
  bob.apply(43)
  bob(43) // invoking bob as a function === bob.apply(43)

  // Invented FunctionX = Function1, Function2 ... Function22 (a function can take up to 22 arguments)
  val simpleIncrementer = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }
  simpleIncrementer.apply(43) // returns 44
  simpleIncrementer(43) // the same as calling the apply method

  // fucntion with 3 arguments and a String return type
  val stringConcatenator = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }
  stringConcatenator("I love", " Scala") // returns "I love Scala"

  // syntax sugars to replace all the boiler code above -- lambda expression
  val doubler: Function1[Int, Int] = (x: Int) => 2 * x
  doubler(4) // returns 8

  /*
  ****** Equivalent to the much longer: ******
  val doubler: Function1[Int, Int] = new Function1[Int, Int] {
   override def apply(x: Int) = 2 * x
   }

   ****** A shorter one ******
   val doubler1 = (x: Int) => 2 * x
  */

  // .map is a special method that allows passing a function in it.
  // Do not need to mention the type of x
  // mapped list returns a different list
  val aMappedList: List[Int] = List(1, 2, 3).map(x => x + 1)

  val aFlatMappedList = List(1, 2, 3).flatMap { x =>
    List(x, 2 * x)
  } //returns (1,2 2,4 3,6)}
  val testMap = List(1, 2, 3).map(x => List(x, 2 * x))

  val aFilteredList = List(1,2,3,4,5).filter(x => x <= 3)

  // all pairs between numbers 1,2,3 and the letters 'a', 'b', 'c'
  val allPairs = List(1,2,3).flatMap(number => List('a', 'b', 'c').map(letter => s"$number-$letter"))
  // returns (1-a, 1-b, 1-c, 2-a, 2-b, 2-c, 3-a, 3-b, 3-c)

  // for comprehensions == flatMap(...).map(...)
  val alternativePairs = for {
    number <- List(1,2,3)
    letter <- List('a', 'b', 'c')
  } yield s"$number-$letter"

  val aList = List(1,2,3,4,5)
  val firstElement = aList.head   // list has a head = the first element
  val rest = aList.tail           //a tail = the rest of the elements
  val aPrependedList = 0 :: aList // List(0,1,2,3,4,5)
  val anExtendedList = 0 +: aList :+ 6 // List(0,1,2,3,4,5,6)

  // sequences
  val aSequence: Seq[Int] = Seq(1,2,3) // Seq.apply(1,2,3)
  val accessedElement = aSequence.apply(1) // equivalent to aSequence(1); returns 2

  // vectors: fast Sequence implementation
  val aVector = Vector(1,2,3,4,5) // has fast access time then List

  // sets = collections with no duplicates
  val aSet = Set(1,2,3,4,1,2,3) // Set(1,2,3,4) // used to test whether an element is contained in a set
  val setHas5 = aSet.contains(5) // returns false
  val anAddedSet = aSet + 5 // returns Set(1,2,3,4,5) // order is not important in a Set collection
  val aRemovedSet = aSet - 3 // returns Set(1,2,4)

  // ranges
  val aRange = 1 to 1000 // a collections that contains all the numbers between 1 and 1000
  val twoByTwo = aRange.map(x => 2 * x).toList // List(2,4,6,8,..., 2000)

  // tuple -- access by aTuple._1 | aTuple._2 ....
  val aTuple = ("Bon Jovi", "Rock", 1982)
  println(s"first: ${aTuple._1}")

  // maps
  val aPhoneBook: Map[String, Int] = Map (
    ("Daniel", 123458),
    "Jane" -> 123409 // equivalent to ("Jane", 123409)
  )
}
