package team.belloo.hamoney.domain

import team.belloo.hamoney.core.pay.Pay

sealed class Spending(
    open val id: Long,
    open val accountBookId: Long,
    open val amount: Long,
    open val date: String,
    open val categoryId: Long,
    open val subCategoryId: Long,
    open val pay: Pay,
    open val used: String?,
    open val skipSum: Boolean
) {
    data class Normal(
        override val id: Long = 0,
        override val accountBookId: Long,
        override val amount: Long,
        override val date: String,
        override val categoryId: Long,
        override val subCategoryId: Long,
        override val pay: Pay,
        override val used: String?,
        override val skipSum: Boolean
    ) : Spending(
        id, accountBookId, amount, date, categoryId, subCategoryId, pay, used, skipSum
    )
}