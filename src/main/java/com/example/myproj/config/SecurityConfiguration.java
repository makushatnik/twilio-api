package com.example.myproj.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.myproj.security.CustomExceptionTranslationFilter;
import com.example.myproj.security.jwt.JwtRequestFilter;
import com.example.myproj.security.point.UserAuthenticationEntryPoint;
import com.example.myproj.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.example.myproj.util.UrlConstants.API_PATH;
import static com.example.myproj.util.UrlConstants.BASE_PATH;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.ALL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${app.version}")
    private String version;

    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher(API_PATH + "**")
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        API_PATH + "login/**",
                        API_PATH + "logout",
                        API_PATH + "post/categories",
                        API_PATH + "post/unregistered/**",
                        BASE_PATH + "swagger_ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(API_PATH + "logout"))
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ALL)))
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                .maximumSessions(3)
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(sessionRegistry)
//                .and()
//                .sessionAuthenticationStrategy(sessionStrategy)
//                .sessionAuthenticationFailureHandler(errorHandler)
                .and()
                .addFilterBefore(new JwtRequestFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomExceptionTranslationFilter(userAuthenticationEntryPoint()), JwtRequestFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
//                "/actuator/**",
                "/csrf",
                "/error",
//                INTERNAL_BASE_PATH + "**",
//                "/v2/api-docs**",
//                "/configuration/ui",
//                "/swagger-resources",
//                "/configuration/security",
                "/swagger-ui.html"
//                "/webjars/**",
//                "/swagger-resources/configuration/ui",
//                "/swagger-resources/configuration/security"
        );
    }

    //для инвалидации сессий при логауте
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public AuthenticationEntryPoint userAuthenticationEntryPoint() {
        return new UserAuthenticationEntryPoint(objectMapper);
    }
}
