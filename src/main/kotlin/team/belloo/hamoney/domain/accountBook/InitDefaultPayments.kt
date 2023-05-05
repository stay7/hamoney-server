package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.accountbook.AccountBookEntity
import team.belloo.hamoney.entity.accountbook.AccountBookPayEntity
import team.belloo.hamoney.entity.user.PersonalPayEntity
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.persistence.AccountBookPayRepository
import team.belloo.hamoney.persistence.PersonalPayRepository

@UseCase
class InitDefaultPayments(
    private val personalPayRepository: PersonalPayRepository,
    private val accountBookPayRepository: AccountBookPayRepository
) {
    operator fun invoke(command: Command) {
        PersonalPayEntity().apply {
            name = "개인카드"
            userId = command.user.id
            iconId = 0
        }.also {
            personalPayRepository.save(it)
        }

        AccountBookPayEntity().apply {
            accountBookId = command.accountBook.id
            name = "데이트 통장"
            iconId = 0
        }.also {
            accountBookPayRepository.save(it)
        }
    }

    data class Command(
        val user: UserEntity,
        val accountBook: AccountBookEntity
    )
}