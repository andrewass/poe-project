package com.poe.project.consumer.requests

import org.json.JSONObject


fun createTradeItemRequest(name: String): String {
    return JSONObject()
            .put("query", JSONObject()
                    .put("filters", JSONObject()
                            .put("trade_filters", JSONObject()
                                    .put("disabled", false)
                                    .put("filters", JSONObject()
                                            .put("price", JSONObject()
                                                    .put("min", 1)
                                                    .put("max", 1000)))))
                    .put("status", JSONObject()
                            .put("option", "online"))
                    .put("name", name))
            .put("sort", JSONObject()
                    .put("price", "asc"))
            .toString()
}