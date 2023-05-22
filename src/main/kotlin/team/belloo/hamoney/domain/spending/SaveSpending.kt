package team.belloo.hamoney.domain.spending

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.Spending
import team.belloo.hamoney.entity.SpendingEntity
import team.belloo.hamoney.persistence.SpendingRepository

@UseCase
class SaveSpending(
    private val spendingRepository: SpendingRepository
) {
    operator fun invoke(spending: Spending) {
        SpendingEntity().apply {
            this.accountBookId = spending.accountBookId
            this.amount = spending.amount
            this.date = spending.date
            this.categoryId = spending.categoryId
            this.subCategoryId = spending.subCategoryId
            this.payId = spending.pay.id
            this.used = spending.used
            this.skipSum = spending.skipSum
        }.also {
            spendingRepository.save(it)
        }
    }
}