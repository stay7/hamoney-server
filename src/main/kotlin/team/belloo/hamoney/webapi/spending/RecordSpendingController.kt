package team.belloo.hamoney.webapi.spending

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.core.HamoneyAmount
import team.belloo.hamoney.core.HamoneyDate
import team.belloo.hamoney.core.spending.Spending
import team.belloo.hamoney.core.spending.SpendingRepeat
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.domain.spending.SaveSpending
import team.belloo.hamoney.webapi.JsonResult

@RestController
@RequestMapping("/spending/record")
@Authentication
class RecordSpendingController(
    private val saveSpending: SaveSpending
) {

    @PostMapping
    fun record(
        user: User,
        form: RecordSpendingForm
    ): JsonResult {

        val date = HamoneyDate.of(form.date)
        val amount = HamoneyAmount(form.amount)
        val spending = when (form.repeatPeriod) {
            null -> Spending.Normal(
                accountBookId = form.accountBookId,
                amount = amount,
                date = date,
                categoryId = form.categoryId,
                subCategoryId = form.subCategoryId,
                payId = form.paymentId,
                skipSum = form.skipSum,
                createdBy = user.id
            )

            else -> Spending.Repeat(
                accountBookId = form.accountBookId,
                amount = amount,
                date = date,
                categoryId = form.categoryId,
                subCategoryId = form.subCategoryId,
                payId = form.paymentId,
                repeatPeriod = SpendingRepeat.of(form.repeatPeriod),
                skipSum = form.skipSum,
                createdBy = user.id
            )
        }.also {
            saveSpending(it)
        }

        return JsonResult.success()
    }

    // 반복 지출 여부가 필요할 것 같음
    data class RecordSpendingForm(
        val accountBookId: Long,
        val categoryId: Long,
        val subCategoryId: Long,
        val date: String,
        val paymentId: Long,
        val amount: Long,
        val memo: String,
        val repeatPeriod: String?,
        val skipSum: Boolean
    )
}