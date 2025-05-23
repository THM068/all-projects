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

package crypto

import com.google.inject.{Inject, Singleton}
import models._
import models.store._

@Singleton
class SubmissionEncrypter @Inject() (
  notificationEncrypter: NotificationEncrypter,
  fullDisclosureEncrypter: FullDisclosureEncrypter
) {

  def encryptSubmission(submission: Submission, sessionId: String): EncryptedSubmission =
    submission match {
      case notification: Notification => notificationEncrypter.encryptNotification(notification, sessionId)
      case disclosure: FullDisclosure => fullDisclosureEncrypter.encryptFullDisclosure(disclosure, sessionId)
    }

  def decryptSubmission(submission: EncryptedSubmission, sessionId: String): Submission =
    submission match {
      case notification: EncryptedNotification => notificationEncrypter.decryptNotification(notification, sessionId)
      case disclosure: EncryptedFullDisclosure => fullDisclosureEncrypter.decryptFullDisclosure(disclosure, sessionId)
    }
}
