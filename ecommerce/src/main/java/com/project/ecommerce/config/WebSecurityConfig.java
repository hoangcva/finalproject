package com.project.ecommerce.config;

import com.project.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IUserService userService;
    @Autowired
    private DataSource dataSource;
    // Token stored in Table - persistent_logins
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/**").hasAnyRole();

        http
            .authorizeRequests()
                // Chỉ cho phép user có quyền ADMIN truy cập đường dẫn /admin/**
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/vendor/**").access("hasRole('ROLE_VENDOR')")
                // Chỉ cho phép user có quyền ADMIN hoặc USER truy cập đường dẫn /user/**
                .antMatchers("/customer/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                .and()
            // Cấu hình cho Login Form.
            .formLogin()
                .loginProcessingUrl("/j_spring_security_login")
                .loginPage("/login")
                .defaultSuccessUrl("/showProducts")
                .failureUrl("/login?message=error")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
            .rememberMe()
                .key("rem-me-key")
                .rememberMeCookieName("remember-me-cookie")
                .rememberMeParameter("remember-me")  // remember-me field name in form.
                .tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(1*24*60*60)
                .and()
            // Cấu hình cho Logout Page.
            .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/j_spring_security_logout")
                .logoutSuccessUrl("/login?message=logout")
                // Khi người dùng đã login, với vai trò USER, Nhưng truy cập vào trang yêu cầu vai trò ADMIN, sẽ chuyển hướng tới trang /403
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
            .csrf().disable().cors();
    }
}