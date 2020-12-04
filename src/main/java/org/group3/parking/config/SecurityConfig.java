package org.group3.parking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("666666");
        auth.inMemoryAuthentication().withUser("admin").password(password).roles("admin");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/admin/index")
                .loginProcessingUrl("/admin/login")
                .failureForwardUrl("/admin/login/error")
                .successForwardUrl("/admin/main").permitAll().and()
                .authorizeRequests().antMatchers("/admin/index", "/asserts/**","/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
//        http.authorizeRequests().antMatchers("/**").permitAll();
    }
}
