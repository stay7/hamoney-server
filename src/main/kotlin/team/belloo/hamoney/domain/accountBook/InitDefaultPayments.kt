package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.pay.Pay
import team.belloo.hamoney.domain.pay.PayRepository

@UseCase
class InitDefaultPayments(
    private val payRepository: PayRepository,
) {
    operator fun invoke(command: Command) {
        Pay.Personal(
            id = 0,
            name = "개인카드",
            iconId = 0,
            userId = command.userId
        ).also {
            payRepository.save(it)
        }

        Pay.Shared(
            id = 0,
            name = "데이트 통장",
            iconId = 0,
            accountBookId = command.accountBookId
        ).also {
            payRepository.save(it)
        }
    }

    data class Command(
        val userId: Long,
        val accountBookId: Long
    )
}