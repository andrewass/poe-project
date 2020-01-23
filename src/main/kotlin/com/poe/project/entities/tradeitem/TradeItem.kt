package com.poe.project.entities.tradeitem

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "T_TRADE_ITEM")
class TradeItem(

        @Id
        @GeneratedValue
        val id: Long? = null,

        val itemId: String = "",

        val name: String = "",

        val currency: String = "",

        val currencyAmount: Int = 0,

        val isCorrupted: Boolean = false,

        @OneToMany(mappedBy = "tradeItem", cascade = [CascadeType.ALL])
        val sockets: List<Socket> = ArrayList(),

        @CreatedDate
        val date_created: LocalDate = LocalDate.now()

)