package team.belloo.hamoney.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "event_spending")
class EventSpendingEntity {
    @Id
    @Column(nullable = false)
    var eventId: Long = 0

    @Id
    @Column(nullable = false)
    var spendingId: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventSpendingEntity

        if (eventId != other.eventId) return false
        return spendingId == other.spendingId
    }

    override fun hashCode(): Int {
        var result = eventId.hashCode()
        result = 31 * result + spendingId.hashCode()
        return result
    }
}
