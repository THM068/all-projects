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

package models.notification

import play.api.libs.json.{Json, OFormat}
import crypto.EncryptedValue
import models.IncomeOrGainSource

final case class Background(
  haveYouReceivedALetter: Option[Boolean] = None,
  letterReferenceNumber: Option[String] = None,
  disclosureEntity: Option[DisclosureEntity] = None,
  areYouRepresetingAnOrganisation: Option[Boolean] = None,
  organisationName: Option[String] = None,
  offshoreLiabilities: Option[Boolean] = None,
  onshoreLiabilities: Option[Boolean] = None,
  incomeSource: Option[Set[IncomeOrGainSource]] = None,
  otherIncomeSource: Option[String] = None
)

object Background {
  implicit val format: OFormat[Background] = Json.format[Background]
}

final case class EncryptedBackground(
  haveYouReceivedALetter: Option[Boolean] = None,
  letterReferenceNumber: Option[String] = None,
  disclosureEntity: Option[DisclosureEntity] = None,
  areYouRepresetingAnOrganisation: Option[Boolean] = None,
  organisationName: Option[EncryptedValue] = None,
  offshoreLiabilities: Option[Boolean] = None,
  onshoreLiabilities: Option[Boolean] = None,
  incomeSource: Option[Set[IncomeOrGainSource]] = None,
  otherIncomeSource: Option[String] = None
)

object EncryptedBackground {
  implicit val format: OFormat[EncryptedBackground] = Json.format[EncryptedBackground]
}
