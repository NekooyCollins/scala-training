package rockthejvm.advanced

object TypeLevelProgramming {

  // scala 2.13
  // reflecting package shows the type of value
  // show(List(1, 2, 3)) --> TypeTag[List[Int]]

  import scala.reflect.runtime.universe._

  def show[T](value: T)(implicit tag: TypeTag[T]) =
    tag.toString().replace("rockthejvm.TypeLevelProgramming", "")

  // type-level programming
  trait Nat

  class _0 extends Nat

  class Succ[N <: Nat] extends Nat // type N derives from Nat, <: is type constraint

  type _1 = Succ[_0]
  type _2 = Succ[_1] // Succ[Succ[_0]]
  type _3 = Succ[_2] // Succ[Succ[Succ[_0]]]
  type _4 = Succ[_3]
  type _5 = Succ[_4]

  trait <[A <: Nat, B <: Nat]

  object < {
    implicit def ltBasic[B <: Nat]: <[_0, Succ[B]] = new <[_0, Succ[B]] {}

    implicit def inductive[A <: Nat, B <: Nat](implicit lt: <[A, B]): Succ[A] < Succ[B] = new <[Succ[A], Succ[B]] {}

    def apply[A <: Nat, B <: Nat](implicit lt: <[A, B]): A < B = lt // return lt -- type is less than between A and B
  }

  val comparison: _1 < _3 = <[_1, _3]
  /*
  * <.apply[_1, _3] -> requires implicit <[_1, _3]
  * inductive[_1, _3] -> requires implicit <[_0, _2]
  * ltBasic[_1] -> produces implicit <[_0, Succ[_1]] == <[_0,_2]
  * */

  trait <=[A <: Nat, B <: Nat]

  object <= {
    implicit def ltBasic[B <: Nat]: <=[_0, B] = new <=[_0, B] {}

    implicit def inductive[A <: Nat, B <: Nat](implicit lte: <=[A, B]): Succ[A] <= Succ[B] = new <=[Succ[A], Succ[B]] {}

    def apply[A <: Nat, B <: Nat](implicit lte: <=[A, B]): A <= B = lte
  }

  val lteTest: _1 <= _1 = <=[_1, _1]

  def main(args: Array[String]): Unit = {
    println(show(lteTest))
  }
}
