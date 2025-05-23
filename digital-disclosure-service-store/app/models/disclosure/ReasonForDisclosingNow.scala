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

package models.disclosure

import play.api.libs.json.{Json, OFormat}
import models.{AdviceGiven, WhichEmailAddressCanWeContactYouWith, WhichTelephoneNumberCanWeContactYouWith, WhyAreYouMakingADisclosure}
import crypto.EncryptedValue

final case class ReasonForDisclosingNow(
  reason: Option[Set[WhyAreYouMakingADisclosure]] = None,
  otherReason: Option[String] = None,
  whyNotBeforeNow: Option[String] = None,
  receivedAdvice: Option[Boolean] = None,
  personWhoGaveAdvice: Option[String] = None,
  adviceOnBehalfOfBusiness: Option[Boolean] = None,
  adviceBusinessName: Option[String] = None,
  personProfession: Option[String] = None,
  adviceGiven: Option[AdviceGiven] = None,
  whichEmail: Option[WhichEmailAddressCanWeContactYouWith] = None,
  whichPhone: Option[WhichTelephoneNumberCanWeContactYouWith] = None,
  email: Option[String] = None,
  telephone: Option[String] = None
)

object ReasonForDisclosingNow {
  implicit val format: OFormat[ReasonForDisclosingNow] = Json.format[ReasonForDisclosingNow]
}

final case class EncryptedReasonForDisclosingNow(
  reason: Option[Set[WhyAreYouMakingADisclosure]] = None,
  otherReason: Option[String] = None,
  whyNotBeforeNow: Option[String] = None,
  receivedAdvice: Option[Boolean] = None,
  personWhoGaveAdvice: Option[EncryptedValue] = None,
  adviceOnBehalfOfBusiness: Option[Boolean] = None,
  adviceBusinessName: Option[EncryptedValue] = None,
  personProfession: Option[String] = None,
  adviceGiven: Option[AdviceGiven] = None,
  whichEmail: Option[WhichEmailAddressCanWeContactYouWith] = None,
  whichPhone: Option[WhichTelephoneNumberCanWeContactYouWith] = None,
  email: Option[EncryptedValue] = None,
  telephone: Option[EncryptedValue] = None
)

object EncryptedReasonForDisclosingNow {
  implicit val format: OFormat[EncryptedReasonForDisclosingNow] = Json.format[EncryptedReasonForDisclosingNow]
}
