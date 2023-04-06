package team.belloo.hamoney.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class UserEntity {
    @Id
    @Column(updatable = false, nullable = false)
    var id: Long = 0

    @Id
    @Column(updatable = false, nullable = false)
    var uuid: String = ""

    @Column(unique = true)
    var email: String = ""

    @Column
    var nickname: String = ""

    @Column
    var status: Int = 1

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Instant = Instant.now()

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Instant = Instant.now()
}