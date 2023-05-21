package team.belloo.hamoney.webapi.accountbook

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.domain.category.FindCategory
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.persistence.AccountBookPayRepository
import team.belloo.hamoney.persistence.AccountBookRepository
import team.belloo.hamoney.persistence.MemberRepository
import team.belloo.hamoney.webapi.AccountBookView
import team.belloo.hamoney.webapi.JsonResult
import team.belloo.hamoney.webapi.toView

@RestController
@RequestMapping("/account_book")
@Authentication
class GetAccountBookController(
    private val accountBookRepository: AccountBookRepository,
    private val accountBookPayRepository: AccountBookPayRepository,
    private val memberRepository: MemberRepository,
    private val findCategory: FindCategory,
) {

    @GetMapping
    fun accountBook(
        user: UserEntity,
        @RequestParam accountBookId: Long
    ): JsonResult {
        if (accountBookId !in memberRepository.findAllByUserId(user.id).map { it.accountBookId })
            return JsonResult.error("가계부에 권한이 없습니다.")

        val accountBook = accountBookRepository.findById(accountBookId).get()
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
}