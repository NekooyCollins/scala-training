package rockthejvm

class ObjectOrientation extends App {

  class Animal {
    val age: Int = 0
    def eat() = println("I am eating")
  }

  val anAnimal = new Animal

  // inheritance
  class Dog(val name: String) extends Animal //constructor definition
  val aDog = new Dog("Lassie")
  // constructor arguments are NOT fields: need to put a val before the argument
  aDog.name

  class Cat(val name: String) extends Animal
  val aCat = new Cat("Purrow")
  println(aCat.name) //works

  // subtype polymorphism
  val aDeclaredAnimal: Animal = new Dog("Hachi")
  aDeclaredAnimal.eat() // the most recent method will be called at runtime

  // abstract class
  abstract class WalkingAnimal {
    protected val hasLegs = true //by default public, cal restrict by using 'private' or 'protected'
    def walk(): Unit // if no function implementation is given here, has to be defined by extenders of the class
  }


  // "interface" = ultimate abstract type
  // traits are used to denote characteristics of objects
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  // method naming
  trait Philosopher {
    def ?!(thought: String): Unit //valid method name
  }

  // Scala has single-class inheritance, multi-trait 'mixing'
  class Crocodile extends Animal with Carnivore with Philosopher { //can mix in as many traits as we like
    override def eat(animal: Animal): Unit = println("I am eating you, animal!")
    override def ?!(thought: String): Unit = println(s"I was thinking: $thought")
  }

  val aCrocodile = new Crocodile
  aCrocodile.eat(aDog)
  aCrocodile eat aDog // infix notation = object method argument, only available for methods with ONE argument
  aCrocodile ?! "What if we could fly"

  // Operators in Scala are actually methods
  val basicMath = 1 + 2
  val anotherBasicMath = 1.+(2) //equivalent

  // anonymous classes - no class is chosen, just a trait. So the compiler creates an random class itself
  val dinosaur = new Carnivore {
    def eat (animal: Animal): Unit = println("I am a Dinosaur and I can eat anything")
  }

  // singleton object
  object MySingleton { // the only instance of MySingleton type
    val mySpecialValue = 2345
    def mySpecialMethod(): Int = 1820
    def apply(x: Int): Int = x + 1
  }

  // can apply it now:
  MySingleton.mySpecialMethod()
  MySingleton.apply(65)
  MySingleton(65) //equivalent to MySingleton.apply(65)

  object Animal { // companion object to class Animal (can also be applied to traits)
    // they can access each other's private fields/methods
    // singleton Animal and instances of Animal are different things
    val canLiveIndefinitely = false
  }
  val animalsCanLiveForever = Animal.canLiveIndefinitely

  case class Person(name: String, age: Int)

  // may be constructed without new
  val bob = Person("Bob", 54) // equivalent to Person.apply("Bob", 54)


  // Scala compiles on JVM!
  // exceptions - when JVM throws an error, it would crash the machine. But we can catch those errors/exceptions
  try {
    // some code that can throw
    val x: String = null
    x.length
  } catch {
    case e: Exception => "some faulty error message"
  } finally {
    // execute some code no matter what - like close some files or stop processes
  }

  // generics - feature of a statically typed language
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T]
  }

  // using generic with a concrete type
  val aList: List[Int] = List(1,2,3) // equivalent to List.apply(1,2,3)
  val first = aList.head
  val rest = aList.tail
  val aStringList = List("hello", "Scala")
  val firstString = aStringList.head //string

  // Point #1: Scala operates with IMMUTABLE values/objects. Changing an instance of a class should result in another instance
  // Any modification to an object must return ANOTHER object
  /*
  Benefits:
   - good for multithreaded / distributed env
   - helps making sense of the code ("reasoning about")
  */
  val reverseList = aList.reverse //returns a NEW list

  // Point #2: Scala is the closest to the OO ideal
  // -> All the code that we write is a part of the class or an object

}
