package com.poe.project.consumer.objects

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
class LeagueDTO(

        @JsonProperty("text")
        val name : String = ""
)