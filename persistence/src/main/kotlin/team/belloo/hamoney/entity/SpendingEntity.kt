package team.belloo.hamoney.entity

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

// SpendingDetail이 필요할까?
// TODO: 필요한 인덱스 걸자
// TODO: 통화를 남긴다
@Entity
@Table(name = "spending")
@EntityListeners(AuditingEntityListener::class)
data class SpendingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var accountBookId: Long = 0,

    @Column(nullable = false)
    var amount: Long? = null,

    @Column(nullable = false)
    var date: String = "",

    @Column(nullable = false)
    var categoryId: Long = 0,

    @Column(nullable = true)
    var subCategoryId: Long? = null,

    @Column(nullable = true)
    var payId: Long = 0,

    @Column(nullable = true)
    var repeatPeriod: String? = null,

    @Column(nullable = false)
    var skipSum: Boolean = false,

    @Column(nullable = false)
    var createdBy: Long = 0,

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Instant = Instant.now(),

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Instant = Instant.now()
)