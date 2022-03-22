package rockthejvm.advanced.part1

object AdvancedPatternMatching extends App {

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head.")
    case _ =>
  }

  // how to make your class is comparable with pattern matching -- using unapply!
  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] = Some((person.name, person.age))

    def unapply(age: Int): Option[String] = Some(if (age < 21) "minor" else "major")
  }

  val Bob = new Person("Bob", 25)
  val gretting = Bob match {
    case Person(n, a) => s"hi, my name is $n and i am $a-year-old"
  }
  println(gretting)

  val legalStatus = Bob.age match {
    case Person(status) => s"My legal status is $status"
  }
  println(legalStatus)

  /*
  how to handle multiple conditions with pattern matching?
  usually use singleton objects with unapply and name it with lower case
  */
  object even {
    def unapply(n: Int): Boolean = n % 2 == 0
  }

  object singleDigit {
    def unapply(n: Int): Boolean = n > -10 && n < 10
  }

  val n: Int = 45
  val matchProperty = n match {
    case singleDigit() => "single digit"
    case even() => "an even number"
    case _ => "no property"
  }
  println(matchProperty)

  // infix patterns
  case class Or[A, B](a: A, b: B) // this type is Either

  val either = Or(2, "two")
  val humandescription = either match {
    case number Or string => s"$number is written as $string" // can be written as Or(number, string) => s"$number is written as $string"
  }
  println(humandescription)

  // decomposing sequences
  val vararg = numbers match {
    case List(1, _*) => "starting with 1"
  }

  abstract class MyList[+A] {
    def head: A = ???

    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]

  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _) // recursively
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposed = myList match {
    case MyList(1, 2, _*) => "starting with 1, 2"
    case _ => "something else"
  }
  println(decomposed)

  // custom return types for unapply
  // isEmpty: Boolean, get:something
  abstract class Wrapper[T] {
    def isEmpty: Boolean

    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      def isEmpty = false

      def get = person.name
    }
  }

  println(Bob match {
    case PersonWrapper(n) => s"This person's name is ${n}"
    case _ => "An alien"
  })
}
