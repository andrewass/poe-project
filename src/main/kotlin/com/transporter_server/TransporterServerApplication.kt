package com.transporter_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TransporterServerApplication

fun main(args: Array<String>) {
    runApplication<TransporterServerApplication>(*args) {
    }
}
