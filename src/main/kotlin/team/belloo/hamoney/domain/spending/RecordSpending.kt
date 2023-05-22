package team.belloo.hamoney.domain.spending

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.pay.Pay
import team.belloo.hamoney.domain.Spending
import team.belloo.hamoney.entity.accountbook.AccountBookEntity
import team.belloo.hamoney.entity.accountbook.CategoryEntity
import team.belloo.hamoney.entity.accountbook.SubCategoryEntity
import team.belloo.hamoney.entity.user.UserEntity

@UseCase
class RecordSpending(
    private val saveSpending: SaveSpending
) {

    operator fun invoke(command: RecordSpendingCommand) {
        Spending.Normal(
            accountBookId = command.accountBook.id,
            amount = command.amount,
            date = command.date,
            categoryId = command.category.id,
            subCategoryId = command.subCategory.id,
            pay = command.pay,
            used = command.used,
            skipSum = command.skipSum,
        ).also {
            saveSpending(it)
        }
    }

    data class RecordSpendingCommand(
        val user: UserEntity,
        val accountBook: AccountBookEntity,
        val category: CategoryEntity,
        val subCategory: SubCategoryEntity,
        val pay: Pay,
        val used: String,
        val amount: Long,
        val date: String,
        val skipSum: Boolean
    )
}