package com.example

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import java.util.Scanner


fun main() {
    val client = HttpClient(CIO) {
        install(WebSockets) {
            pingInterval = 20_000
        }
    }
    runBlocking {
        client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/echo") {
             while (true) {
                println("Websocket client started")
                Scanner(System.`in`).nextLine()?.let { userMessage ->
                    send(userMessage)
                }
                val otherMessage = (incoming.receive() as? Frame.Text)?.readText()
                otherMessage?.let { println(it) }
             }
        }
    }
   client.close()
}
