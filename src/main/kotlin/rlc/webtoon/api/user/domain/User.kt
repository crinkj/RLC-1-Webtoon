package rlc.webtoon.api.user.domain

import jakarta.persistence.*
import rlc.webtoon.api.common.domain.BaseEntity
import rlc.webtoon.api.webtoon.domain.Webtoon

@Table(name = "users")
@Entity
class User(
        @Column(unique = true)
        val accountId: String,
        val password: String,
        private var ownedLeaves: Int = 0
) : BaseEntity() {

    init {
        require(accountId.isNotBlank()) {
            "accountId 필수 값 입니다."
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val tokens: MutableList<UserToken> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val myWebtoons: MutableList<UserWebtoon> = mutableListOf()

    fun addToken(refreshToken: String) {
        this.tokens.forEach { it.delete() }

        val token = UserToken(
                user = this,
                refreshToken = refreshToken
        )
        this.tokens.add(token)
    }

    fun chargeLeaves(leaves: Int) {
        this.ownedLeaves = this.ownedLeaves.plus(leaves)
    }

    fun buyWebtoonWithLeaves(chargedLeaves: Int, webtoon: Webtoon): Int {
        if (this.ownedLeaves == null || this.ownedLeaves < chargedLeaves) {
            throw IllegalStateException("잔여 잎이 부족하여 웹툰을 구매할 수 없습니다.잎 충전 필요합니다.")
        }

        this.ownedLeaves -= chargedLeaves
        this.myWebtoons.add(
                UserWebtoon(
                        user = this,
                        webtoonId = webtoon.id,
                        title = webtoon.title,
                        paidLeaves = webtoon.requiredLeaves
                )
        )

        return this.ownedLeaves
    }
}