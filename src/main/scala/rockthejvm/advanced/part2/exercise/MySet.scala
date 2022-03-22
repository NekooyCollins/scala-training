package rockthejvm.advanced.part2.exercise

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean) {
  def apply(elem: A): Boolean = contains(elem)
  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(another: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  def -(elem: A): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A]  // difference
  def &(anotherSet: MySet[A]): MySet[A]   // intersection
}

class EmptySet[A] extends MySet[A] {
  def contains(elem: A): Boolean = false
  def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  def map[B](f: A => B): MySet[B] = new EmptySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(predicate: A => Boolean): MySet[A] = this
  def foreach(f: A => Unit): Unit = ()

  // part 2
  def -(elem: A): MySet[A] = this
  def --(anotherSet: MySet[A]): MySet[A] = this
  def &(anotherSet: MySet[A]): MySet[A] = this
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  def contains(elem: A): Boolean =
    elem == head || tail.contains(elem)

  def +(elem: A): MySet[A] =
    if (this.contains(elem)) this
    else new NonEmptySet[A](elem, this)

  // recursively calling the ++ method and then add the head
  /*
  * [1 2 3] ++ [4 5] =
  * [2 3] ++ [4 5] + 1 =
  * [3] ++ [4 5] + 1 + 2 =
  * [] ++ [4 5] + 1 + 2 + 3 =
  * [4 5] + 1 + 2 + 3 = [4 5 1 2 3], it's set so we do not care about the order
  * */
  def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  def map[B](f: A => B): MySet[B] = tail.map(f) + f(head)
  def flatMap[B](f: A => MySet[B]): MySet[B] = tail.flatMap(f) ++ f(head) // add another head set
  def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail.filter(predicate)
    if (predicate(head)) filteredTail + head
    else filteredTail
  }
  def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  // part 2
  def -(elem: A): MySet[A] =
    if (head == elem) tail
    else tail - elem + head
  def --(anotherSet: MySet[A]): MySet[A] = filter(x => !anotherSet.contains(x))
  def &(anotherSet: MySet[A]): MySet[A] = filter(x => anotherSet.contains(x)) // intersection = filtering
}

object MySet {
  /*
  val s = MySet(1, 2, 3) = buildSet(Seq(1,2,3), [])
        = buildSet(Seq(2, 3), [] + 1)
        = buildSet(Seq(3), [1] + 2)
        = buildSet(Seq(), [1, 2] + 3)
        = [1, 2, 3]
  * */
  // A* here is Seq[A]/List[A], it is another way to show a list
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] =
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)

    buildSet(values, new EmptySet[A])
  }
}

object MySetPlaygroud extends App {
  val s = MySet(1, 2, 3, 4)
  s.foreach(println)
}