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

package viewmodels.checkAnswers.adjustments

import controllers.rentalsandrentaroom.adjustments.routes.BusinessPremisesRenovationBalancingChargeController
import models._
import pages.adjustments.RenovationAllowanceBalancingChargePage
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.SummaryListRow
import viewmodels.checkAnswers.FormatUtils.{bigDecimalCurrency, keyCssClass, valueCssClass}
import viewmodels.govuk.summarylist._
import viewmodels.implicits._

object BusinessPremisesRenovationAllowanceBalancingChargeSummary {

  def row(taxYear: Int, answers: UserAnswers)(implicit messages: Messages): Option[SummaryListRow] =
    answers.get(RenovationAllowanceBalancingChargePage(RentalsRentARoom)).flatMap {
      case RenovationAllowanceBalancingCharge(true, amount) =>
        Some(
          SummaryListRowViewModel(
            key =
              KeyViewModel("businessPremisesRenovationBalancingCharge.checkYourAnswersLabel").withCssClass(keyCssClass),
            value = ValueViewModel(bigDecimalCurrency(amount.get)).withCssClass(valueCssClass),
            actions = Seq(
              ActionItemViewModel(
                "site.change",
                BusinessPremisesRenovationBalancingChargeController.onPageLoad(taxYear, CheckMode).url
              )
                .withVisuallyHiddenText(messages("businessPremisesRenovationBalancingCharge.change.hidden"))
            )
          )
        )
      case RenovationAllowanceBalancingCharge(false, _) =>
        Some(
          SummaryListRowViewModel(
            key =
              KeyViewModel("businessPremisesRenovationBalancingCharge.checkYourAnswersLabel").withCssClass(keyCssClass),
            value = ValueViewModel("site.no").withCssClass(valueCssClass),
            actions = Seq(
              ActionItemViewModel(
                "site.change",
                BusinessPremisesRenovationBalancingChargeController.onPageLoad(taxYear, CheckMode).url
              )
                .withVisuallyHiddenText(messages("businessPremisesRenovationBalancingCharge.change.hidden"))
            )
          )
        )
      case _ => Option.empty[SummaryListRow]
    }

}
