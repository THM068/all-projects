/*
 * Copyright 2023 HM Revenue & Customs
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

package audit

import models.{AccountingMethod, AuditPropertyType, JourneyName, SectionName}
import play.api.libs.json.{Format, Json, OFormat}

final case class AuditModel[T](
  userType: String,
  nino: String,
  mtdItId: String,
  taxYear: Int,
  propertyType: AuditPropertyType,
  countryCode: String,
  journeyName: JourneyName,
  sectionName: SectionName,
  accountingMethod: AccountingMethod,
  isUpdate: Boolean,
  isFailed: Boolean,
  agentReferenceNumber: Option[String],
  userEnteredDetails: T
)

object AuditModel {
  implicit def format[T](implicit auditModelFormat: Format[T]): OFormat[AuditModel[T]] =
    Json.format[AuditModel[T]]
}
