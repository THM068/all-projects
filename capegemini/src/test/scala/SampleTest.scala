import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

import java.util
import java.util.EmptyStackException

class SampleTest extends AnyFlatSpec with MockFactory with Matchers with TableDrivenPropertyChecks {

 "Sample test" should "no such element exception" in {
    val stack = new util.Stack[Int]

    a[EmptyStackException] should be thrownBy {
      stack.pop()
    }
  }

  "match" should "contain all the words in table" in {
    val words = Table(
      ("name", "age"),
      ("Thando", "41"),
      ("Kerrie", "35"),
      ("Thomas", "4")
    )

    forAll(words) { (name, age) => {
      println(s"name $name age $age")
    }

    }
  }
}
