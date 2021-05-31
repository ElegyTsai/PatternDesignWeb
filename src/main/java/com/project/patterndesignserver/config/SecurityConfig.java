package com.project.patterndesignserver.config;

import com.project.patterndesignserver.config.authenticationhandler.user.MyAuthenticationFailureHandler;
import com.project.patterndesignserver.config.authenticationhandler.user.MyAuthenticationSuccessHandler;
import com.project.patterndesignserver.service.security.UserSecurityService;
import com.project.patterndesignserver.service.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    //第一个表示登陆
    @Configuration
    @Order(1)
    public class UserConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        MyAuthenticationFailureHandler myAuthenticationFailureHandler;
        @Autowired
        MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

        @Override
        public void configure(HttpSecurity http) throws Exception {
//            这里表示全部允许 无验证
//            http.authorizeRequests(authorizedRequests -> authorizedRequests.anyRequest().permitAll());

            http.antMatcher("/admin/**").
                    formLogin().usernameParameter("uname").passwordParameter("pwd").loginPage("/home/login")
                    .successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailureHandler)
                    .and()
                    .antMatcher("/home/login")
                    .userDetailsService(service())
                    .authorizeRequests()
                    .antMatchers("/admin/login").permitAll()
                    .antMatchers("/admin/**").access("hasRole('ADMIN')")
                    .anyRequest().permitAll();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.logout().logoutUrl("/home/logout").permitAll();


            http.cors().and().csrf().disable();
            //禁用csrf
        }

        @Bean
        UserDetailsService service() {
            return new UserSecurityService();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service()).passwordEncoder(passwordEncoder());
        }
    }
}
