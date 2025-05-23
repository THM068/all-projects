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
import models._

final case class OnshoreLiabilities(
  behaviour: Option[Set[WhyAreYouMakingThisOnshoreDisclosure]] = None,
  excuseForNotNotifying: Option[ReasonableExcuseOnshore] = None,
  reasonableCare: Option[ReasonableCareOnshore] = None,
  excuseForNotFiling: Option[ReasonableExcuseForNotFilingOnshore] = None,
  whatLiabilities: Option[Set[WhatOnshoreLiabilitiesDoYouNeedToDisclose]] = None,
  whichYears: Option[Set[OnshoreYears]] = None,
  youHaveNotIncludedTheTaxYear: Option[String] = None,
  youHaveNotSelectedCertainTaxYears: Option[String] = None,
  taxBeforeThreeYears: Option[String] = None,
  taxBeforeFiveYears: Option[String] = None,
  taxBeforeNineteenYears: Option[String] = None,
  disregardedCDF: Option[Boolean] = None,
  taxYearLiabilities: Option[Map[String, OnshoreTaxYearWithLiabilities]] = None,
  lettingDeductions: Option[Map[String, BigInt]] = None,
  lettingProperties: Option[Seq[LettingProperty]] = None,
  memberOfLandlordAssociations: Option[Boolean] = None,
  landlordAssociations: Option[String] = None,
  howManyProperties: Option[String] = None,
  corporationTaxLiabilities: Option[Set[CorporationTaxLiability]] = None,
  directorLoanAccountLiabilities: Option[Set[DirectorLoanAccountLiabilities]] = None
)

object OnshoreLiabilities {
  implicit val format: OFormat[OnshoreLiabilities] = Json.format[OnshoreLiabilities]
}
