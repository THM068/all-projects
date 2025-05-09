package cats
import cats.syntax.semigroup._
import cats.instances.string
import cats.instances.int._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MonoidSpec extends AnyFunSuite with Matchers {

  def addAll[A](list: List[A])(implicit monoid: Monoid[A]): A = {
    list.foldLeft(monoid.empty)(monoid.combine)
  }

  test("addAll with Monoid") {
    addAll(List(1, 2, 3)) shouldBe 6
    addAll(List("Hello", " ", "World")) shouldBe "Hello World"
    addAll(List(List(1, 2), List(3, 4))) shouldBe List(1, 2, 3, 4)
  }

  test("Simple example of Monoid") {
    Monoid[String].empty shouldBe ""
    Monoid[String].combine("Hello", "World") shouldBe "HelloWorld"

    Monoid[List[Int]].combine(List(1,2), List(3,4)) shouldBe List(1,2,3,4)

    Monoid[String].combine("12","34") shouldBe "1234"
  }

  test("option test") {
    val name = Option("John")
    val surname = Option(" Doe")


    Monoid[Option[String]].combine(name,surname) shouldBe Some("John Doe")
  }

  test("Order total") {
    import OrderM._
    val order1 = Product(100.0, 2.0)
    val order2 = Product(50.0, 1.0)

    val totalOrder = order1 |+| order2
    totalOrder shouldBe Product(150.0, 3.0)
  }
}
case class Product(totalCost: Double, quantity: Double)

object OrderM {
  implicit val orderMonoid: Monoid[Product] = new Monoid[Product] {
    override def empty: Product = Product(0.0, 0.0)

    override def combine(x: Product, y: Product): Product =
      Product(x.totalCost + y.totalCost, x.quantity + y.quantity)
  }
}
