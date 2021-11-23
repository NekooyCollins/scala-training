import org.scalacheck.Prop.forAll
import org.scalatest.wordspec.AnyWordSpec
import org.scalacheck._
import Arbitrary.arbitrary

class MainTest extends AnyWordSpec {

  val arbitraryInt: Gen[Int] = arbitrary[Int]
  val zeroOrMoreDigits = Gen.someOf(1 to 9)

  val fiveDice = Gen.pick(5, 1 to 6)

  "div function" should {
    "return expected values" in {
      forAll(arbitraryInt, arbitraryInt) {(n: Int, m: Int) =>
        println(n ,m)
        n == n
      }
    }
  }
}
