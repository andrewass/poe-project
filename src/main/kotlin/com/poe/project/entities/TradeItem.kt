package com.poe.project.entities

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class TradeItem(

        @Id
        @GeneratedValue
        val id: Long? = null,

        val itemId: String = "",

        val name: String = "",

        val currency : String = "",

        val currencyAmount : Int = 0,

        @CreatedDate
        val date: LocalDate = LocalDate.now()

)