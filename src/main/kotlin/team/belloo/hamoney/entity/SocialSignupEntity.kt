package team.belloo.hamoney.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(name = "social_signup", indexes = [Index(name = "idx_user_id", columnList = "userId")])
class SocialSignupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long = 0

    @Column(nullable = false)
    var userId: Long = 0

    @Column(nullable = false, unique = true)
    var providerKey: String = "" // kakao_1234
}