package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.domain.accountBook.NewAccountBook
import team.belloo.hamoney.domain.category.FindCategory
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.persistence.AccountBookPayRepository
import team.belloo.hamoney.persistence.MemberRepository

@RestController
@RequestMapping("/use/account_book")
@Authentication
class UseAccountBookController(
    private val newAccountBook: NewAccountBook,
    private val memberRepository: MemberRepository,
    private val accountBookPayRepository: AccountBookPayRepository,
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
        val paymentsView = accountBookPayRepository.findAllByAccountBookId(accountBook.id).map { it.toView() }
        val categoriesView = findCategory.allByAccountBookId(accountBookId = accountBook.id).map { it.toView() }

        return AccountBookView(
            id = accountBook.id,
            name = accountBook.name,
            categories = categoriesView,
            payments = paymentsView,
            createdAt = accountBook.createdAt.toEpochMilli()
        )
    }

    @GetMapping("/together")
    fun useTogether(): JsonResult {
        return JsonResult.success()
    }
}