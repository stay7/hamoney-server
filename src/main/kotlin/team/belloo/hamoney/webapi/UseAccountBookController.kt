package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.domain.accountBook.NewAccountBook
import team.belloo.hamoney.domain.category.Category
import team.belloo.hamoney.domain.category.FindCategory
import team.belloo.hamoney.domain.category.SubCategory
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.MemberRepository

@RestController
@RequestMapping("/use/account_book")
@Authentication
class UseAccountBookController(
    private val newAccountBook: NewAccountBook,
    private val memberRepository: MemberRepository,
    private val findCategory: FindCategory,
) {

    @GetMapping("/alone")
    fun useAlone(
        user: UserEntity
    ): JsonResult {
        if (memberRepository.findAllByUserId(user.id).isNotEmpty()) {
            return JsonResult.error("이미 가계부가 있습니다.")
        }

        val accountBook = newAccountBook(user)

        return findCategory.allByAccountBookId(accountBookId = accountBook.id).map {
            it.toView()
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
        val name: String,
        val subCategories: List<SubCategoryView>
    )

    data class SubCategoryView(
        val id: Long,
        val name: String,
        val iconId: Int
    )

    private fun Category.toView() = CategoryView(
        id = id,
        name = name,
        subCategories = subCategories.map { it.toView() }
    )

    private fun SubCategory.toView() = SubCategoryView(
        id = id,
        name = name,
        iconId = iconId
    )
}