package training.implicits

import java.time.Instant

trait Max[T] {
  def max(a: T, b: T): T
}
object Max {
  implicit val intMax: Max[Int] = new Max[Int] {
    def max(a: Int, b: Int): Int = if (a > b) a else b
  }

  implicit val instantMax: Max[Instant] = new Max[Instant] {
    override def max(a: Instant, b: Instant): Instant = if (a.isAfter(b)) a else b
  }
}
