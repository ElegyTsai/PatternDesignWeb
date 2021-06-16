package com.project.patterndesignserver.config;

import com.project.patterndesignserver.config.authenticationhandler.user.MyAuthenticationFailureHandler;
import com.project.patterndesignserver.config.authenticationhandler.user.MyAuthenticationSuccessHandler;
import com.project.patterndesignserver.mapper.sys.UserLoginLogMapper;
import com.project.patterndesignserver.module.filter.JWTAuthorizationFilter;
import com.project.patterndesignserver.service.security.UserSecurityService;
import com.project.patterndesignserver.service.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
        @Autowired
        StringRedisTemplate stringRedisTemplate;
        @Autowired
        UserLoginLogMapper userLoginLogMapper;

        @Override
        public void configure(HttpSecurity http) throws Exception {
//            这里表示全部允许 无验证
//            http.authorizeRequests(authorizedRequests -> authorizedRequests.anyRequest().permitAll());

            http//.antMatcher("/admin/**")
                    .formLogin().usernameParameter("uname").passwordParameter("password").loginProcessingUrl("/api/login/process")
                    .successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailureHandler)
                    .and()
//                    .antMatcher("/home/login")
//                    .userDetailsService(service())
                    .authorizeRequests()
                    .antMatchers("/api/login/process").permitAll()
                    .antMatchers("/api/admin/login").permitAll()
                    .antMatchers("/api/home/logout").permitAll()
                    .antMatchers("/api/admin/user/**").access("hasRole('ADMIN')")
                    .anyRequest().permitAll()
                    .and()
                    .addFilter(new JWTAuthorizationFilter(authenticationManager(),stringRedisTemplate,userLoginLogMapper))
                    ;

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setHideUserNotFoundExceptions(false);
            provider.setUserDetailsService(service());
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }
        //因为有上面的重新注入的Bean 所以这个不需要了
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAu)
//            .userDetailsService(service()).passwordEncoder(passwordEncoder());
//        }
    }
}
