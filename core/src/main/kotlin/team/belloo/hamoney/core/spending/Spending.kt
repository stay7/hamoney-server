package team.belloo.hamoney.core.spending

import team.belloo.hamoney.core.HamoneyAmount
import team.belloo.hamoney.core.HamoneyDate

sealed class Spending(
    open val id: Long,
    open val accountBookId: Long,
    open val amount: HamoneyAmount,
    open val date: HamoneyDate,
    open val categoryId: Long,
    open val subCategoryId: Long,
    open val payId: Long,
    open val repeatPeriod: SpendingRepeat?,
    open val skipSum: Boolean,
    open val createdBy: Long
) {
    data class Normal(
        override val id: Long = 0,
        override val accountBookId: Long,
        override val amount: HamoneyAmount,
        override val date: HamoneyDate,
        override val categoryId: Long,
        override val subCategoryId: Long,
        override val payId: Long,
        override val skipSum: Boolean,
        override val createdBy: Long
    ) : Spending(
        id = id,
        accountBookId = accountBookId,
        amount = amount,
        date = date,
        categoryId = categoryId,
        subCategoryId = subCategoryId,
        payId = payId, repeatPeriod = null,
        skipSum = skipSum,
        createdBy = createdBy
    )

    data class Repeat(
        override val id: Long = 0,
        override val accountBookId: Long,
        override val amount: HamoneyAmount,
        override val date: HamoneyDate,
        override val categoryId: Long,
        override val subCategoryId: Long,
        override val payId: Long,
        override val repeatPeriod: SpendingRepeat,
        override val skipSum: Boolean,
        override val createdBy: Long
    ) : Spending(
        id = id,
        accountBookId = accountBookId,
        amount = amount,
        date = date,
        categoryId = categoryId,
        subCategoryId = subCategoryId,
        payId = payId,
        repeatPeriod = repeatPeriod,
        skipSum = skipSum,
        createdBy = createdBy
    )
}