package com.poe.project.entities

import javax.persistence.*

@Entity
@Table(name = "T_ITEM")
class PoeItem(
        @Id @GeneratedValue
        val id: Long? = null,

        val textID: String = "",

        @ManyToOne
        @JoinColumn(name = "LEAGUE")
        val league: League? = null,

        val imageUrl: String = "",

        val itemName: String = "",

        val typeLine: String = "",

        val stashId: String = "",

        val identified: Boolean = false

) {

    @ManyToOne
    @JoinColumn(name = "CURRENCY")
    private var currency: StaticItem? = null

    private var currencyCount: Int = 0

    fun setPrice(stashPrice: Pair<Int, StaticItem?>, itemPrice: Pair<Int, StaticItem?>) {
        if (itemPrice.first > 0) {
            currencyCount = itemPrice.first
            currency = itemPrice.second
        } else if(stashPrice.first > 0){
            currencyCount = stashPrice.first
            currency = stashPrice.second
        }
    }
    fun getPrice() = Pair(currencyCount, currency)
}
