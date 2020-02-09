package com.poe.project.consumer

import com.poe.project.entities.League
import com.poe.project.entities.StaticItem
import org.codehaus.jettison.json.JSONObject
import org.json.JSONArray
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


@Component
class PoEConsumer @Autowired constructor(
        private val restTemplate: RestTemplate
) {
    private val EMPTY_RESPONSE = ""

    @Value("\${poe.base.url}")
    private lateinit var baseUrl: String

    @Value("\${poe.image.url}")
    private lateinit var imageUrl: String

    @Value("\${poe.stash.url}")
    private lateinit var stashUrl: String

    private val log = LoggerFactory.getLogger(PoEConsumer::class.java)

    fun parseStashTabs(nextId: String): String {
        val urlPath = stashUrl + if (nextId.isEmpty()) "" else "?id=$nextId"
        val httpEntity = HttpEntity("body", createHeaders())

        val response = restTemplate.exchange(urlPath,
                HttpMethod.GET,
                httpEntity,
                String::class.java)

        return if(response.statusCode.is2xxSuccessful){
            response.body ?: EMPTY_RESPONSE
        } else {
            log.error("Unable to fetch stash tab for path $urlPath")
            EMPTY_RESPONSE
        }
    }

    fun getStaticItems(): List<StaticItem> {
        val urlPath = "$baseUrl/api/trade/data/static"
        val httpEntity = HttpEntity("body", createHeaders())

        val response = restTemplate.exchange(urlPath,
                HttpMethod.GET,
                httpEntity,
                String::class.java)

        return if (response.statusCode.is2xxSuccessful) {
            val responseBody = extractValuesFromResult(response.body!!)
            val res = mapStaticItems(JSONArray(responseBody), imageUrl)
            res
        } else {
            log.error("Unable to fetch static items : Statuscode ${response.statusCode}")
            emptyList()
        }
    }

    fun getLeagues(): List<League> {
        val urlPath = "$baseUrl/api/trade/data/leagues"
        val httpEntity = HttpEntity("body", createHeaders())

        val response = restTemplate.exchange(urlPath,
                HttpMethod.GET,
                httpEntity,
                String::class.java)

        return if (response.statusCode.is2xxSuccessful) {
            val responseBody = extractValuesFromResult(response.body!!)
            return mapLeagues(JSONArray(responseBody))
        } else {
            log.error("Unable to fetch leagues : Statuscode ${response.statusCode}")
            emptyList()
        }
    }

    private fun extractValuesFromResult(responseBody: String): String {
        val jsonObject = JSONObject(responseBody)
        return jsonObject.getString("result")
    }

    private fun createHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
        return headers
    }
}