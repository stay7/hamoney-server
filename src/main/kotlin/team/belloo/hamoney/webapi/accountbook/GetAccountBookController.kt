package team.belloo.hamoney.webapi.accountbook

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.domain.category.FindCategory
import team.belloo.hamoney.domain.member.MemberRepository
import team.belloo.hamoney.domain.pay.PayRepository
import team.belloo.hamoney.domain.user.User
import team.belloo.hamoney.persistence.JpaAccountBookRepository
import team.belloo.hamoney.webapi.AccountBookView
import team.belloo.hamoney.webapi.JsonResult
import team.belloo.hamoney.webapi.toView

@RestController
@RequestMapping("/account_book")
@Authentication
class GetAccountBookController(
    private val accountBookRepository: JpaAccountBookRepository,
    private val payRepository: PayRepository,
    private val memberRepository: MemberRepository,
    private val findCategory: FindCategory,
) {

    @GetMapping
    fun accountBook(
        user: User,
        @RequestParam accountBookId: Long
    ): JsonResult {
        if (accountBookId !in memberRepository.findAllByUserId(user.id).map { it.accountBookId })
            return JsonResult.error("가계부에 권한이 없습니다.")

        val accountBook = accountBookRepository.findById(accountBookId).get()
        val sharedPays = payRepository.findAllSharedById(accountBookId)
        val categories = findCategory.allByAccountBookId(accountBookId = accountBook.id)

        return AccountBookView(
            id = accountBook.id,
            name = accountBook.name,
            categories = categories.map { it.toView() },
            payments = sharedPays.map { it.toView() },
            createdAt = accountBook.createdAt.toEpochMilli()
        )
    }
}