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

package config

import javax.inject.{Inject, Singleton}
import play.api.Configuration

import scala.util.Try

@Singleton
class AppConfig @Inject() (val config: Configuration) {

  lazy val cacheTtl: Int = config.get[Int]("mongodb.timeToLiveInDays")

  val mongoEncryptionKey: String                 = config.get[String]("mongodb.encryption.key")
  val previousMongoEncryptionKey: Option[String] = Try(config.get[String]("mongodb.encryption.previousKey")).toOption
}
