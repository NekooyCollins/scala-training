package training.implicits

import spray.json.{JsNull, JsNumber, JsString, JsValue}

object implicitExamples2 extends App{

  trait JsonWriter[T] {
    def writeJson(t: T): JsValue
  }
  object JsonWriter {
    implicit val integerWriter: JsonWriter[Int] = new JsonWriter[Int] {
      def writeJson(i: Int): JsValue = JsNumber(i)
    }

    implicit val stringWriter: JsonWriter[String] = new JsonWriter[String] {
      def writeJson(str: String): JsValue = JsString(str)
    }


    implicit val listIntWirter: JsonWriter[List[String]] = new JsonWriter[List[String]] {
      def writeJson(strList: List[String]): JsValue = ???
    }
    // Some(value) => JsValue
    // None => JsNull
    implicit def optionWriter[T](implicit writer: JsonWriter[T]): JsonWriter[Option[T]] =
      new JsonWriter[Option[T]] {
        def writeJson(optionT: Option[T]): JsValue = optionT match {
          case Some(t) => writer.writeJson(t)
          case None => JsNull
        }
      }
  }


  trait JsonWritable {
    def writeJson(t: Any): JsValue
  }

//  def toJson[T <: JsonWritable](t: T)
  def toJson[T](t: T)(implicit jsonWriter: JsonWriter[T]): JsValue = {
    jsonWriter.writeJson(t)
  }


  println(toJson(1))
  println(toJson("abc"))
  println(toJson(Option(1)))
  println(toJson(Option("abc")))

//  println(toJson(List(1, 2, 3)))
//  println(toJson(List("a", "b", "c")))
}
