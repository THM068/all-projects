package learn.test

import zio.Scope
import zio.test.Assertion.{assertion, containsString, equalTo}
import zio.test.{Assertion, Spec, TestEnvironment, ZIOSpecDefault, assert}

object PersonSpec extends ZIOSpecDefault{

  override def spec: Spec[TestEnvironment with Scope, Any] = zio.test.suite("Person Spec test")(
    test("Person should have a name") {
      val person = Person("John", 30)
      val assertMe: Assertion[String] = (equalTo("John") && containsString("g")).label("Name should contain 'g'")
      assert(person.name)(assertMe).label("Person name should be John")
      assert(person.age)(equalTo(30)).label("Person age should be 30")
    },
  )
}

case class Person(name: String, age: Int)
