package com.poe.project.entities.tradeitem

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "T_PROPERTY")
class Property(
        @Id @GeneratedValue
        val id : Long? = null,

        @ManyToOne
        @JsonIgnore
        val item: PoeItem? = null,

        val description : String = ""

) {
}