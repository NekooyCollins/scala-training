package rockthejvm.fundamental

object FunctionalProgramming extends App {
  /*
   Scala runs on the JVM
   FP: functions are first-class citizens:
   - compose functions
   - pass functions as arguments
   - return functions as a result
  //  COMPOSING FUNCTIONS
   Invented FunctionX = Function1, Function2 ... Function22 (a function can take max 22 arguments)
  */

  val simpleIncrementer = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg + 1
  }

  simpleIncrementer.apply(43) // returns 44
  simpleIncrementer(43) // the same as calling the apply method
  // we defined a function here - it acts like a function

  // ALL SCALA FUNCTIONS ARE INSTANCES OF THE FUNCTION_X TYPES

  // fucntion with 3 arguments and a String return type
  // (String, String) => String
  val stringConcatenator = new Function2[String, String, String] {
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  }

  stringConcatenator("I love", " Scala") // returns "I love Scala"

  // syntax sugars to replace all the boiler code above
  val doubler: Int => Int = (x: Int) => 2 * x
  doubler(4) // returns 8

  /*
  Equivalent to the much longer:
  val doubler: Function1[Int, Int] = new Function1[Int, Int] {
  override def apply(x: Int) = 2 * x
  }
  */

  val aMappedList: List[Int] = List(1, 2, 3).map(x => x + 1) //a HOF
  // .map is a special method that allows passing a function in it.
  // Do not need to mention the type of x
  // mapped list returns a different list

  val aFlatMappedList = List(1, 2, 3).flatMap { x => // alternative syntax with curly braces, same as .map(x => List(x, 2 * x))
    List(x, 2 * x)
  } //returns (1,2 2,4 3,6)

  val aFilteredList = List(1, 2, 3, 4, 5).filter(x => x <= 3)
  //returns the list where the condition x <=3 is true -> (1,2,3)
  // shorter syntax .filter(_ <= 3)

  // all pairs between numbers 1,2,3 and the letters 'a', 'b', 'c'
  val allPairs = List(1, 2, 3).flatMap(number => List('a', 'b', 'c').map(letter => s"$number-$letter"))
  // returns (1-a, 1-b, 1-c, 2-a, 2-b, 2-c, 3-a, 3-b, 3-c)

  // for comprehensions
  val alternativePairs = for {
    number <- List(1, 2, 3)
    letter <- List('a', 'b', 'c')
  } yield s"$number-$letter"
  // equivalent to the flatMap/map chain above


  val aList = List(1, 2, 3, 4, 5) // list has a head = the first element, and a tail = the rest of the elements
  val firstElement = aList.head
  val rest = aList.tail
  val aPrependedList = 0 :: aList // List(0,1,2,3,4,5) -- append all
  val anExtendedList = 0 +: aList :+ 6 // List(0,1,2,3,4,5,6) -- only append single element

  // sequences
  val aSequence: Seq[Int] = Seq(1, 2, 3) // Seq.apply(1,2,3) // allows to access an element at a given index a.k.a. array
  val accessedElement = aSequence.apply(1) // equivalent to aSequence(1); returns 2

  // vectors: fast Sequence implementation
  val aVector = Vector(1, 2, 3, 4, 5) // has fast access time

  // sets = collections with no duplicates
  val aSet = Set(1, 2, 3, 4, 1, 2, 3) // Set(1,2,3,4) // used to test whether an element is contained in a set
  val setHas5 = aSet.contains(5) // returns false
  val anAddedSet = aSet + 5 // returns Set(1,2,3,4,5) // order is not important in a Set collection
  val aRemovedSet = aSet - 3 // returns Set(1,2,4)

  // ranges
  val aRange = 1 to 1000 // a fictitious collections that contains all the numbers between 1 and 1000
  val twoByTwo = aRange.map(x => 2 * x).toList // List(2,4,6,8,..., 2000)

  // tuples = groups of values uncedr the same value
  val aTuple = ("Bon Jovi", "Rock", 1982)

  // maps
  val aPhoneBook: Map[String, Int] = Map(
    ("Daniel", 123458),
    "Jane" -> 123409 // equivalent to ("Jane", 123409)
  )
}
