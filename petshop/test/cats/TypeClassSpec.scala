import cats.implicits.{catsSyntaxSemigroup, toFoldableOps}
import cats.{Eq, Monoid, Show}
import cats.instances.int._
import cats.instances.option._
import cats.syntax.show._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.util.Date

class TypeClassSpec extends AnyFunSuite with Matchers {
  case class Person(name: String, age: Int) {
    def getName() = name
  }
  val peopleList = List(
    Person("Charlie", 30),
    Person("Bob", 25),
    Person("Alice", 35)
  )

  implicit val orderByName:Ordering[Person] = Ordering.fromLessThan((p1, p2) => p1.name.compareTo(p2.name) < 0)
  peopleList.sorted shouldBe List(
    Person("Alice", 35),
    Person("Bob", 25),
    Person("Charlie", 30)
  )

  implicit def getName(name: String): Person = Person(name, 0)

  test("implicit get name") {
    val result = "Thomas".getName()
    result shouldBe Person("Thomas", 0).getName()
  }

  test("calculate area of rectangle") {
    val rectangle = Rectangle(5, 10)
    println(implicitly[Area[Rectangle]])
    val area = ShapeArea.areaOf(rectangle)
    area shouldBe 50.0

    val circle = Circle(5)
    val circleArea = ShapeArea.areaOf(circle)
    circleArea shouldBe Math.PI * Math.pow(5, 2)
  }

  test("test printable") {
    import PrintableInstances._
    val cat = Cat("Fluffy", 5, "white")
    val name = "Thando"
    val age = 43
    val formattedName = Printable.format(name)
    val formattedAge = Printable.format(age)
    val formattedCat = Printable.format(cat)

    formattedName shouldBe "Thando"
    formattedAge shouldBe "43"
    formattedCat shouldBe "Fluffy is 5 years old and is white cat"

    Printable.print(name)
    Printable.print(age)
    Printable.print(cat)
  }

  test("test cats show") {
    val showInt = Show.apply[Int]
    val showString: Show[String] = Show.apply[String]

    val intAsString = showInt.show(42)
    intAsString shouldBe "42"

    43.show shouldBe "43"
    "Hello".show shouldBe "Hello"

    implicit val dateShow: Show[Date] = new Show[Date] {
      override def show(date: Date): String =
        s"${date.getTime}ms since the epoch."
    }

    val date = new Date()
    println(date.show)
  }

  test("test cats eq") {
    val eqInt = Eq[Int]
    val eqOpt = Eq[Option[Int]]
    val intRes = eqInt.eqv(42, 42)
    intRes shouldBe true
    eqInt.eqv(42, 43) shouldBe false
    eqOpt.eqv(Some(42), Some(42)) shouldBe true

  }

  test("Test sales report") {
    val report1 = SalesReport(1000, 10, 5, 50)
    val report2 = SalesReport(2000, 20, 10, 100)

    val combinedReport = report1 |+| report2
    combinedReport shouldBe SalesReport(3000, 30, 15, 150)

    List(report1, report2).combineAll shouldBe SalesReport(3000, 30, 15, 150)
  }

}
final case class Cat(name: String, age: Int, color: String)
case class Circle(radius: Double)
case class Rectangle(length: Double, width: Double)

object Area {
  implicit val circleArea: Area[Circle] = new Area[Circle] {
    def area(circle: Circle): Double = Math.PI * Math.pow(circle.radius, 2)
  }

  implicit val rectangleArea: Area[Rectangle] = new Area[Rectangle] {
    def area(rectangle: Rectangle): Double = rectangle.length * rectangle.width
  }
}

object ShapeArea {
  def areaOf[A](a: A)(implicit shape: Area[A]): Double = shape.area(a)
}

trait Area[A] {
  def area(a: A): Double
}

trait Printable[A] {
  def format(a: A): String
}

object PrintableInstances {
  implicit val stringPrintable: Printable[String] = (a: String) => a

  implicit val intPrintable: Printable[Int] = (a: Int) => a.toString

  implicit val catPrintable: Printable[Cat] = (a: Cat) =>
    s"${a.name} is ${a.age} years old and is ${a.color} cat"
}

object Printable {
  def format[A](a: A)(implicit printable: Printable[A]): String = {
    printable.format(a)
  }

  def print[A](a: A)(implicit printable: Printable[A]):Unit =
    println(format(a))
}

case class SalesReport(
                        totalRevenue: Double,
                        unitsSold: Int,
                        newCustomers: Int,
                        taxCollected: Double
                      )
object SalesReport {

  implicit val salesReportMonoid: Monoid[SalesReport] = new Monoid[SalesReport] {

    override def empty: SalesReport = SalesReport(0, 0, 0, 0)

    override def combine(x: SalesReport, y: SalesReport): SalesReport = SalesReport(
      x.totalRevenue + y.totalRevenue,
      x.unitsSold + y.unitsSold,
      x.newCustomers + y.newCustomers,
      x.taxCollected + y.taxCollected
    )
  }
}

