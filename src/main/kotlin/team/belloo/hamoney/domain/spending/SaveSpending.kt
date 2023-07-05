package team.belloo.hamoney.domain.spending

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.spending.Spending
import team.belloo.hamoney.entity.SpendingEntity
import team.belloo.hamoney.persistence.SpendingRepository

@UseCase
class SaveSpending(
    private val spendingRepository: SpendingRepository
) {
    operator fun invoke(spending: Spending) {
        SpendingEntity().apply {
            this.accountBookId = spending.accountBookId
            this.amount = spending.amount.value
            this.date = spending.date.format()
            this.categoryId = spending.categoryId
            this.subCategoryId = spending.subCategoryId
            this.payId = spending.payId
            this.repeatPeriod = spending.repeatPeriod?.value
            this.createdBy = spending.createdBy
            this.skipSum = spending.skipSum
            this.memo = spending.memo
        }.also {
            spendingRepository.save(it)
        }
    }
}