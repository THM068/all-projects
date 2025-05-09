import models.{Forms, Student}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import play.api.data.FormError

import scala.collection.immutable.ArraySeq

class studentFormSpec extends AnyFunSuite with Matchers {

  test("A valid student does not return any errors") {
    val input = Student("John", "Doe", 20)
    val form = Forms.studentForm.fillAndValidate(input)

    form.errors shouldBe empty
  }

  test("An invalid student returns errors") {
    val input = Student("", "", -1)
    val form = Forms.studentForm.fillAndValidate(input)


    form.errors should not be empty
    form.errors should contain theSameElementsAs Seq(
      FormError("name", "error.required", List()),
      FormError("surname", "error.required", List()),
      FormError("age", "error.min", ArraySeq(0))
    )
  }
}
