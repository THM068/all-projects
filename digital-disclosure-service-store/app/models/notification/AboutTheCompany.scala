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
import models.address.Address
import crypto.EncryptedValue
import models.address.EncryptedAddress

final case class AboutTheCompany(
  name: Option[String] = None,
  registrationNumber: Option[String] = None,
  address: Option[Address] = None
)

object AboutTheCompany {
  implicit val format: OFormat[AboutTheCompany] = Json.format[AboutTheCompany]
}

final case class EncryptedAboutTheCompany(
  name: Option[EncryptedValue] = None,
  registrationNumber: Option[EncryptedValue] = None,
  address: Option[EncryptedAddress] = None
)

object EncryptedAboutTheCompany {
  implicit val format: OFormat[EncryptedAboutTheCompany] = Json.format[EncryptedAboutTheCompany]
}
