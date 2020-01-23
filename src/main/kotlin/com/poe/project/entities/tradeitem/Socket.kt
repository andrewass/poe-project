package com.poe.project.entities.tradeitem

import javax.persistence.*

@Entity
@Table(name = "T_SOCKET")
class Socket(
        @Id
        @GeneratedValue
        val id: Long? = null,

        val groupNumber : Int = 0,

        val attr : String = "",

        @ManyToOne
        @JoinColumn(name = "TRADE_ITEM")
        val tradeItem: TradeItem? = null
)
