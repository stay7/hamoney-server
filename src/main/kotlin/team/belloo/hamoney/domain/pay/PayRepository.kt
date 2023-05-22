package team.belloo.hamoney.domain.pay

interface PayRepository {
    fun findById(id: Long): Pay?

    fun findAllSharedById(accountBookId: Long): List<Pay>

    fun findAllPersonalById(userId: Long): List<Pay>

    fun save(pay: Pay): Pay
}