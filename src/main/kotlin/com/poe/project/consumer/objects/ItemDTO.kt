package com.poe.project.consumer.objects

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


class ItemDTO(
        val label : String?,
        val name : String?,
        val type : String,
        val text : String,
        val unique : Boolean?
)