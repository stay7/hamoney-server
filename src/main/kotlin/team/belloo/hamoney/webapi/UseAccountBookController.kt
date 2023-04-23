package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.domain.accountBook.NewAccountBook
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.CategoryRepository

@RestController
@RequestMapping("/use/account_book")
@Authentication
class UseAccountBookController(
    private val newAccountBook: NewAccountBook,
    private val categoryRepository: CategoryRepository
) {

    @GetMapping("/alone")
    fun useAlone(
        user: UserEntity
    ): JsonResult {
        val accountBook = newAccountBook(user)

        return categoryRepository.findAllByAccountBookId(accountBookId = accountBook.id).map {
            CategoryView(
                id = it.id,
                name = it.name
            )
        }.let {
            AccountBookView(
                id = accountBook.id,
                name = accountBook.name,
                categories = it,
                createdAt = accountBook.createdAt.toEpochMilli()
            )
        }
    }

    @GetMapping("/together")
    fun useTogether(): JsonResult {
        return JsonResult.success()
    }

    data class AccountBookView(
        val id: Long,
        val name: String,
        val categories: List<CategoryView>,
        val createdAt: Long
    ) : JsonResult(status = Status.SUCCESS)

    data class CategoryView(
        val id: Long,
        val name: String
    )
}