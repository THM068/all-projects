/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers.ukandforeignproperty

import base.SpecBase
import forms.ukandforeignproperty.UkAndForeignPropertyForeignPremiumsGrantLeaseFormProvider
import models.ukAndForeign.UkAndForeignPropertyForeignPremiumsGrantLease
import models.{NormalMode, UserAnswers}
import org.scalatest.prop.TableFor1
import org.scalatest.prop.Tables.Table
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks.forAll
import pages.ukandforeignproperty._
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.api.test.Helpers._
import views.html.ukandforeignproperty.UkAndForeignPropertyForeignPremiumsGrantLeaseView

import java.time.LocalDate

class UkAndForeignPropertyForeignPremiumsGrantLeaseControllerSpec extends SpecBase with MockitoSugar {

  def onwardRoute: Call = Call("GET", "/foo")
  val countryCode: String = "ps"
  val periods: Int = 3
  val premiumAmount: BigDecimal = 2345

  override def emptyUserAnswers: UserAnswers = (for {
    ua1 <- UserAnswers(userAnswersId).set(ForeignLeaseGrantAmountReceivedPage, premiumAmount)
    ua2 <- ua1.set(ForeignYearLeaseAmountPage, periods)
  } yield ua2).success.value

  val scenarios: TableFor1[String] = Table[String]( "individual", "agent")
  val taxYear: Int = LocalDate.now().getYear
  val formProvider = new UkAndForeignPropertyForeignPremiumsGrantLeaseFormProvider()
  val foreignPremiumsGrantLeaseAnswers: UkAndForeignPropertyForeignPremiumsGrantLease =
    UkAndForeignPropertyForeignPremiumsGrantLease(premiumsOfLeaseGrantAgreed = true, Some(123.45))

  lazy val foreignPremiumsGrantLeaseRoute: String =
    routes.UkAndForeignPropertyForeignPremiumsGrantLeaseController.onPageLoad(taxYear, NormalMode).url

  forAll(scenarios) { (individualOrAgent: String) =>
    val form = formProvider(individualOrAgent)
    val isAgent: Boolean = individualOrAgent == "agent"

    s"ForeignPremiumsGrantLease Controller for an $individualOrAgent" - {

      "must return OK and the correct view for a GET" in {

        val application = applicationBuilder(userAnswers = Some(emptyUserAnswers), isAgent = isAgent).build()

        running(application) {
          val request = FakeRequest(GET, foreignPremiumsGrantLeaseRoute)

          val result = route(application, request).value

          val view = application.injector.instanceOf[UkAndForeignPropertyForeignPremiumsGrantLeaseView]

          status(result) mustEqual OK
          contentAsString(result) mustEqual view(
            form,
            taxYear,
            periods,
            premiumAmount,
            individualOrAgent,
            NormalMode
          )(request, messages(application)).toString
        }
      }

      "must populate the view correctly on a GET when the question has previously been answered" in {

        val userAnswers = emptyUserAnswers
          .set(
            UkAndForeignPropertyForeignPremiumsGrantLeasePage,
            foreignPremiumsGrantLeaseAnswers
          )
          .success
          .value

        val application = applicationBuilder(userAnswers = Some(userAnswers), isAgent = isAgent).build()

        running(application) {
          val request = FakeRequest(GET, foreignPremiumsGrantLeaseRoute)

          val view = application.injector.instanceOf[UkAndForeignPropertyForeignPremiumsGrantLeaseView]

          val result = route(application, request).value

          status(result) mustEqual OK
          contentAsString(result) mustEqual view(
            form.fill(foreignPremiumsGrantLeaseAnswers),
            taxYear,
            periods,
            premiumAmount,
            individualOrAgent,
            NormalMode
          )(request, messages(application)).toString
        }
      }

      //TODO when the navigation us implemented
//      "must redirect to the next page when valid data is submitted" in {
//
//        val mockSessionRepository = mock[SessionRepository]
//
//        when(mockSessionRepository.set(any())) thenReturn Future.successful(true)
//
//        val application =
//          applicationBuilder(userAnswers = Some(emptyUserAnswers), isAgent = isAgent)
//            .overrides(
//              bind[ForeignPropertyNavigator].toInstance(new FakeForeignPropertyNavigator(onwardRoute)),
//              bind[SessionRepository].toInstance(mockSessionRepository)
//            )
//            .build()
//
//        running(application) {
//          val request =
//            FakeRequest(POST, foreignPremiumsGrantLeaseRoute)
//              .withFormUrlEncodedBody(
//                ("premiumsOfLeaseGrantAgreed", "true"),
//                ("premiumsOfLeaseGrant", "123.45")
//              )
//
//          val result = route(application, request).value
//
//          status(result) mustEqual SEE_OTHER
//          redirectLocation(result).value mustEqual onwardRoute.url
//        }
//      }

      "must return a Bad Request and errors when invalid data is submitted" in {

        val application = applicationBuilder(userAnswers = Some(emptyUserAnswers), isAgent = isAgent).build()

        running(application) {
          val request =
            FakeRequest(POST, foreignPremiumsGrantLeaseRoute)
              .withFormUrlEncodedBody(("value", ""))

          val boundForm = form.bind(Map("value" -> ""))

          val view = application.injector.instanceOf[UkAndForeignPropertyForeignPremiumsGrantLeaseView]

          val result = route(application, request).value

          status(result) mustEqual BAD_REQUEST
          contentAsString(result) mustEqual view(
            boundForm,
            taxYear,
            periods,
            premiumAmount,
            individualOrAgent,
            NormalMode
          )(request, messages(application)).toString
        }
      }

      "must redirect to Journey Recovery for a GET if no existing data is found" in {

        val application = applicationBuilder(userAnswers = None, isAgent = isAgent).build()

        running(application) {
          val request = FakeRequest(GET, foreignPremiumsGrantLeaseRoute)

          val result = route(application, request).value

          status(result) mustEqual SEE_OTHER
          redirectLocation(result).value mustEqual controllers.routes.JourneyRecoveryController.onPageLoad().url
        }
      }

      "must redirect to Journey Recovery for a POST if no existing data is found" in {

        val application = applicationBuilder(userAnswers = None, isAgent = isAgent).build()

        running(application) {
          val request =
            FakeRequest(POST, foreignPremiumsGrantLeaseRoute)
              .withFormUrlEncodedBody(("value", "true"))

          val result = route(application, request).value

          status(result) mustEqual SEE_OTHER
          redirectLocation(result).value mustEqual controllers.routes.JourneyRecoveryController.onPageLoad().url
        }
      }
    }
  }

}
