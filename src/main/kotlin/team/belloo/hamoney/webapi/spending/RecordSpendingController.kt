package team.belloo.hamoney.webapi.spending

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.persistence.AccountBookRepository
import team.belloo.hamoney.webapi.JsonResult

@RestController
@RequestMapping("/spending/record")
@Authentication
class RecordSpendingController(
    private val accountBookRepository: AccountBookRepository
) {

    @PostMapping
    fun record(
        user: UserEntity,
        form: RecordSpendingForm
    ): JsonResult {

        return JsonResult.success()
    }

    // 반복 지출 여부가 필요할 것 같음
    data class RecordSpendingForm(
        val accountBookId: Long,
        val categoryId: Long,
        val subCategoryId: Long,
        val date: String,
        val paymentId: Long,
        val amount: Long,
        val used: String? = null
    )
}