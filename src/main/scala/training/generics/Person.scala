package training.generics

import training.implicits.Max

case class Person(age: Int, name: String)
object Person {
  implicit val personMaxOnAge: Max[Person] = new Max[Person] {
    override def max(a: Person, b: Person): Person = if (a.age > b.age) a else b
  }

  implicit val personMaxOnName: Max[Person] = new Max[Person] {
    override def max(a: Person, b: Person): Person = if (a.name.length > b.name.length) a else b
  }
}
