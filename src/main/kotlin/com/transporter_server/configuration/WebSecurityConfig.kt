package com.transporter_server.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter()  {

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/","/**").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}