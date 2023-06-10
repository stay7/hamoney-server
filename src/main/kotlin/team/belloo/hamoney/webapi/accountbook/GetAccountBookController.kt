package team.belloo.hamoney.webapi.accountbook

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.core.accountbook.AccountBookRepository
import team.belloo.hamoney.core.category.CategoryRepository
import team.belloo.hamoney.core.member.MemberRepository
import team.belloo.hamoney.core.pay.PayRepository
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.domain.accountBook.GetAccountBookUpdatedAt
import team.belloo.hamoney.webapi.AccountBookView
import team.belloo.hamoney.webapi.JsonResult
import team.belloo.hamoney.webapi.toView

@RestController
@RequestMapping("/account_book")
@Authentication
class GetAccountBookController(
    private val accountBookRepository: AccountBookRepository,
    private val payRepository: PayRepository,
    private val memberRepository: MemberRepository,
    private val categoryRepository: CategoryRepository,
    private val getAccountBookUpdatedAt: GetAccountBookUpdatedAt
) {

    @GetMapping
    fun accountBook(
        user: User,
        @RequestParam accountBookId: Long
    ): JsonResult {
        if (accountBookId !in memberRepository.findAllByUserId(user.id).map { it.accountBookId })
            return JsonResult.error("가계부에 권한이 없습니다.")

        val accountBook = accountBookRepository.findById(accountBookId)!!
        val sharedPays = payRepository.findAllSharedById(accountBookId)
        val categories = categoryRepository.findAllByAccountBookId(accountBookId = accountBook.id)

        return AccountBookResponse(
            accountBook = AccountBookView(
                id = accountBook.id,
                name = accountBook.name,
                categories = categories.map { it.toView() },
                payments = sharedPays.map { it.toView() },
                createdAt = accountBook.createdAt.toEpochMilli()
            ),
            revision = getAccountBookUpdatedAt(accountBook).toEpochMilli()
        )
    }

    private data class AccountBookResponse(
        val accountBook: AccountBookView,
        val revision: Long,
    ) : JsonResult(status = Status.SUCCESS)
}