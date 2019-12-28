package com.poe.project.consumer.requests

import org.json.JSONObject


fun createTradeItemRequest(name: String): String {
    return JSONObject()
            .put("query", JSONObject()
                    .put("status", JSONObject()
                            .put("option", "online"))
                    .put("name", name)).toString()
}