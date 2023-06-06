package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.accountbook.AccountBook
import team.belloo.hamoney.core.category.CategoryRepository
import java.time.Instant

@UseCase
class GetAccountBookUpdatedAt(
    private val categoryRepository: CategoryRepository,
) {

    operator fun invoke(accountBook: AccountBook): Instant {
        val categories = categoryRepository.findAllByAccountBookId(accountBook.id)

        return maxOf(accountBook.updatedAt, categories.maxOf { it.updatedAt })
    }
}