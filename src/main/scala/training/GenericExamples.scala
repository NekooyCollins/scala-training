package training

import spray.json.{JsNull, JsNumber, JsString, JsValue}
import training.implicits.Max

import java.time.ZonedDateTime


case class Person(age: Int, name: String)
object Person {
  implicit val personMaxOnAge: Max[Person] = new Max[Person] {
    override def max(a: Person, b: Person): Person = if (a.age > b.age) a else b
  }

  implicit val personMaxOnName: Max[Person] = new Max[Person] {
    override def max(a: Person, b: Person): Person = if (a.name.length > b.name.length) a else b
  }
}

object GenericExamples {

  def findMaxInt(ls: List[Int]): Int =
    if(ls.isEmpty) throw new Exception("max of empty list")
    else ls.reduce((int1, int2) => if(int1 > int2) int1 else int2)

  def findMaxString(ls: List[String]): String =
    if(ls.isEmpty) throw new Exception("max of empty list")
    else ls.reduce((string1, string2) => if(string1 > string2) string1 else string2)

  def findMaxTimestamp(ls: List[ZonedDateTime]): ZonedDateTime =
    if(ls.isEmpty) throw new Exception("max of empty list")
    else ls.reduce((time1, time2) => if(time1.isAfter(time2)) time1 else time2)

  def findOldestPerson(ls: List[Person]): Person =
    if(ls.isEmpty) throw new Exception("max of empty list")
    else ls.reduce((person1, person2) => if(person1.age > person2.age) person1 else person2)

  def findMax[T](ls: List[T], max: (T, T) => T): T =
    if(ls.isEmpty) throw new Exception("max of empty list")
    else ls.reduce(max)


  // parsers
  // JsValue is a representation of json values
  // subclasses of JsValue are for example
  // JsNull = null
  // JsNumber(1) = 1, JsString("asd") = "asd"
  // JsArray(Vector(JsNumber(1), JsNumber(2))) = [1, 2]

  def numberOptToJson(on: Option[BigDecimal]): JsValue = on match {
    case None => JsNull
    case Some(number) => JsNumber(number)
  }

  def stringOptToJson(os: Option[String]): JsValue = os match {
    case None => JsNull
    case Some(string) => JsString(string)
  }

  def optionToJson[T](ot: Option[T], toJson: T => JsValue): JsValue = ot match {
    case None => JsNull
    case Some(value) => toJson(value)
  }

  def jsonToNumber(js: JsValue): Option[BigDecimal] = js match {
    case JsNull => None
    case JsNumber(bigDecimal) => Some(bigDecimal)
    case x => throw new Exception(s"expected json as number, got: $x")
  }

  def jsonToString(js: JsValue): Option[String] = js match {
    case JsNull => None
    case JsString(string) => Some(string)
    case x => throw new Exception(s"expected json as string, got: $x")
  }

  def jsonToOption[T](js: JsValue, readJson: JsValue => T): Option[T] = js match {
    case JsNull => None
    case js => Some(readJson(js))
  }
}
