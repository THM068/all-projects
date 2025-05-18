package cats
import cats.instances.function._
import cats.syntax.functor._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FunctorSpec extends AnyFunSuite with Matchers {

  test("Functor map") {
    val func1: Int => Double = (x: Int) => x.toDouble
    val func2: Double => Double = (y: Double) => y * 2

    (func1 map func2)(5) shouldBe 10.0
  }

  test("functor test "){
    Functor[Option].map(Some(1))(_ + 1) shouldBe Some(2)
    Functor[List].map(List(1,2,3))(_ + 1) shouldBe List(2,3,4)
  }
}

trait  Functor[F[_]] {
  def map[A,B](container: F[A])(f: A => B): F[B]
}
