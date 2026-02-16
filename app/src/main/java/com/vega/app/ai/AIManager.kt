package com.vega.app.ai

import okhttp3.*
import java.io.IOException

class AIManager {

    private val client = OkHttpClient()

    private val apiKey = "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"

    fun ask(prompt: String, callback: (String) -> Unit) {

        val json = """
        {
          "model": "llama3-8b-8192",
          "messages": [{"role":"user","content":"$prompt"}]
        }
        """.trimIndent()

        val request = Request.Builder()
            .url("https://api.groq.com/openai/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(
                "application/json".toMediaTypeOrNull(), json))
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback("Network Error")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string() ?: ""
                callback(body)
            }
        })
    }
}
