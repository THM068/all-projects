package cats

import cats.implicits._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class CatsTutorialSpec extends AnyFunSuite with Matchers {

  test("map options") {
    val option1 = Option(1)
    val option2 = Option(2)

    val combined = (option1, option2).mapN(_ + _)
    combined shouldBe Some(3)

   val r=  for {
      a <- option1
      b <- option2
    } yield a + b

    r shouldBe Some(3)

    val numbers = List(1, 2, 3, 4, 5)
    val sum = numbers.combineAll
    sum shouldBe 15
  }
}
