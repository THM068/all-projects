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
import crypto.EncryptedValue

final case class CaseReference(
  doYouHaveACaseReference: Option[Boolean] = None,
  whatIsTheCaseReference: Option[String] = None
)

object CaseReference {
  implicit val format: OFormat[CaseReference] = Json.format[CaseReference]
}

final case class EncryptedCaseReference(
  doYouHaveACaseReference: Option[Boolean] = None,
  whatIsTheCaseReference: Option[EncryptedValue] = None
)

object EncryptedCaseReference {
  implicit val format: OFormat[EncryptedCaseReference] = Json.format[EncryptedCaseReference]
}
