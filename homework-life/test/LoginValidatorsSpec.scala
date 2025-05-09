import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting
import controllers.LoginValidators._
class LoginValidatorsSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "LoginForm" should {
    "Be valid when valid username and password are provided" in  {
       val result = loginForm.bind(Map("Username" -> "thando", "Password" -> "password"))
       result.hasErrors must be(false)

    }

    "Have error when invalid data is provided" in {
      val result = loginForm.bind(Map("username" -> "thando"))
      result.hasErrors must be(true)
    }
  }

}
