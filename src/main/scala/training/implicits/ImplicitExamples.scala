package training.implicits

import training.generics.Person

import java.time.Instant

object ImplicitExamples extends App {

  def findMax[T](ls: List[T])(implicit maxOfT: Max[T]): T =
    if (ls.isEmpty) throw new Exception("max of empty list")
    else ls.reduce((a, b) => maxOfT.max(a, b))

  println(findMax(List(1, 2, 3)))

  val now = Instant.now()
  println {
    findMax(List(now, now.plusSeconds(1)))
  }

  val people = List(Person(10, "oliver"), Person(42, "piotr"), Person(1, "olga"))

  def findMaxBy[T, Key](ls: List[T])(extractKey: T => Key)(implicit maxOfKey: Max[Key]): T = {
    if (ls.isEmpty) throw new Exception("max of empty list")
    else
      ls.reduce { (a, b) =>
        val (aKey, bKey) = (extractKey(a), extractKey(b))
        if (maxOfKey.max(aKey, bKey) == aKey) a else b
      }
  }

  println(findMaxBy(people)(_.age))
  println(findMaxBy(people)(_.name.length))

  val findOldestPerson = findMaxBy(_: List[Person])(_.age)
  val findPersonWithLongestName = findMaxBy(_: List[Person])(_.name.length)

  println(findOldestPerson(people))
  println(findPersonWithLongestName(people))
}
