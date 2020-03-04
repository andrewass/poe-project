package com.poe.project.entities.tradeitem

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "T_MODIFICATION")
class Modification(
        @Id @GeneratedValue
        val id : Long? = null,

        val description : String = "",

        @ManyToOne
        @JsonIgnore
        val item: PoeItem? = null,

        val isImplicit : Boolean = false
)