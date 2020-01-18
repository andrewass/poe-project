package com.poe.project.consumer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.poe.project.consumer.objects.ItemDTO
import com.poe.project.consumer.objects.LeagueDTO
import com.poe.project.consumer.objects.TradeItemDTO
import com.poe.project.consumer.requests.createTradeItemRequest
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
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.lang.Integer.min


@Component
class PoEConsumer @Autowired constructor(
        private val restTemplate: RestTemplate
) {

    @Value("\${poe.base.url}")
    private lateinit var baseUrl: String

    private val log = LoggerFactory.getLogger(PoEConsumer::class.java)

    fun getItems(): List<ItemDTO> {
        val urlPath = "$baseUrl/api/trade/data/items"
        val httpEntity = HttpEntity("body", createHeaders())

        val response = restTemplate.exchange(urlPath,
                HttpMethod.GET,
                httpEntity,
                String::class.java)

        return if (response.statusCode.is2xxSuccessful) {
            val responseBody = extractValuesFromResult(response.body!!)
            return mapItems(JSONArray(responseBody))
        } else {
            log.error("Unable to fetch leagues : Statuscode ${response.statusCode}")
            emptyList()
        }
    }

    fun getLeagues(): List<LeagueDTO> {
        val urlPath = "$baseUrl/api/trade/data/leagues"
        val httpEntity = HttpEntity("body", createHeaders())

        val response = restTemplate.exchange(urlPath,
                HttpMethod.GET,
                httpEntity,
                String::class.java)

        return if (response.statusCode.is2xxSuccessful) {
            val responseBody = extractValuesFromResult(response.body!!)
            return jacksonObjectMapper().readValue(responseBody)
        } else {
            log.error("Unable to fetch leagues : Statuscode ${response.statusCode}")
            emptyList()
        }
    }

    fun findItemsForTrade(itemName: String, league: String): List<TradeItemDTO> {
        val urlPath = "$baseUrl/api/trade/search/$league"
        val httpEntity = HttpEntity(createTradeItemRequest(itemName), createHeaders())

        return try {
            val response = restTemplate.exchange(urlPath,
                    HttpMethod.POST,
                    httpEntity,
                    String::class.java)

            val responseBody = fetchItems(buildResultString(response.body!!))
            mapTradeItems(itemName, extractValuesFromResult(responseBody))
        } catch (e: HttpClientErrorException) {
            log.error("${e.message} : Itemname : $itemName")
            emptyList()
        }
    }

    private fun fetchItems(items: String): String {
        val urlPath = "$baseUrl/api/trade/fetch/$items"
        val httpEntity = HttpEntity("body", createHeaders())

        val response = restTemplate.exchange(urlPath,
                HttpMethod.GET,
                httpEntity,
                String::class.java)

        return response.body!!
    }

    private fun buildResultString(response: String): String {
        val jsonBody = JSONObject(response)
        val items = jsonBody.getJSONArray("result")

        val result = StringBuilder("")
        val maxResults = min(5, items.length())
        for (i in 0 until maxResults) {
            result.append(items[i])
            if (i < items.length() - 2) {
                result.append(",")
            }
        }
        return result.toString()
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