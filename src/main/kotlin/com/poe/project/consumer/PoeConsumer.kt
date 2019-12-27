package com.poe.project.consumer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.poe.project.consumer.objects.LeagueDTO
import org.codehaus.jettison.json.JSONObject
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

    @Value("\${poe.base.url}")
    private lateinit var baseUrl: String

    private val log = LoggerFactory.getLogger(PoEConsumer::class.java)

    fun getLeagues(): List<LeagueDTO> {
        val urlPath = "$baseUrl/api/trade/data/leagues"
        val response = restTemplate.exchange(urlPath,
                HttpMethod.GET,
                createHeaders(),
                String::class.java)

        return if (response.statusCode.is2xxSuccessful) {
            val responseBody = extractValues(response.body!!)
            return jacksonObjectMapper().readValue(responseBody)
        } else {
            log.error("Unable to fetch leagues : Statuscode ${response.statusCode}")
            emptyList()
        }
    }

    private fun extractValues(responseBody: String): String {
        val jsonObject = JSONObject(responseBody)
        return jsonObject.getString("result")
    }

    private fun createHeaders(): HttpEntity<String> {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
        return HttpEntity("parameters", headers)
    }
}