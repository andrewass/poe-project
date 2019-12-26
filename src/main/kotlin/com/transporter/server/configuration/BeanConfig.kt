package com.transporter.server.configuration

import com.transporter.server.util.CustomPasswordEncoder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfig {

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun passwordEncoder() = CustomPasswordEncoder()

}