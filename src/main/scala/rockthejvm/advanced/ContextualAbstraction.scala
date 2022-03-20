package rockthejvm.advanced

object ContextualAbstraction {

  /** 1- context parameters/arguments * */

  // Ordering
  implicit val descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _) // (a, b) => a > b

  val aList = List(2, 1, 3, 4)
  val orderedList = aList.sorted

  trait Combinator[A] {
    def combine(x: A, y: A): A
  }

  def combineAll[A](list: List[A])(implicit combinator: Combinator[A]): A = {
    list.reduce((a, b) => combinator.combine(a, b))
  }

  implicit val intCombinator: Combinator[Int] = new Combinator[Int] {
    override def combine(x: Int, y: Int): Int = x + y
  }
  val theSum = combineAll(aList)

  def combineAll_v2[A: Combinator](list: List[A]): A = ???

  // combineAll(List(1,2,3,4))
  def main(args: Array[String]): Unit = {
    println(orderedList)
    println(theSum)
  }

}
