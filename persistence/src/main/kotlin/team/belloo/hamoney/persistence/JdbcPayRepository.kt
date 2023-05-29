package team.belloo.hamoney.persistence

import team.belloo.hamoney.Repository
import team.belloo.hamoney.core.pay.Pay
import team.belloo.hamoney.core.pay.PayRepository
import team.belloo.hamoney.entity.PayEntity

@Repository
class JdbcPayRepository(
    private val jpaPayRepository: JpaPayRepository,
) : PayRepository {
    override fun findById(id: Long): Pay? {
        return jpaPayRepository.findById(id).orElse(null)?.toDomain()
    }

    override fun findAllSharedById(accountBookId: Long): List<Pay> {
        return jpaPayRepository.findAllByRefIdAndType(accountBookId, PayEntity.Type.SHARED).map { it.toDomain() }
    }

    override fun findAllPersonalById(userId: Long): List<Pay> {
        return jpaPayRepository.findAllByRefIdAndType(userId, PayEntity.Type.PERSONAL).map { it.toDomain() }
    }

    override fun save(pay: Pay): Pay {
        return jpaPayRepository.save(pay.toEntity()).toDomain()
    }

    private fun Pay.toEntity() = when (this@toEntity) {
        is Pay.Personal -> PayEntity().apply {
            name = this@toEntity.name
            iconId = this@toEntity.iconId
            refId = this@toEntity.userId
            type = PayEntity.Type.PERSONAL
        }

        is Pay.Shared -> PayEntity().apply {
            name = this@toEntity.name
            iconId = this@toEntity.iconId
            refId = this@toEntity.accountBookId
            type = PayEntity.Type.SHARED
        }
    }

    private fun PayEntity.toDomain() = when (this.type) {
        PayEntity.Type.PERSONAL -> Pay.Personal(
            id = id,
            name = name,
            iconId = iconId,
            userId = refId,
        )

        PayEntity.Type.SHARED -> Pay.Shared(
            id = id,
            name = name,
            iconId = iconId,
            accountBookId = refId,
        )
    }
}
