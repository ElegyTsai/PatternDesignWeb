package com.project.patterndesignserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig  {
    @Configuration
    @Order(1)
    public class MemberConfig extends WebSecurityConfigurerAdapter{

        @Override
        public void configure(HttpSecurity http) throws Exception{
            http.authorizeRequests(authorizedRequests -> authorizedRequests.anyRequest().permitAll());
            http.cors().and().csrf().disable();
        }
    }

}
