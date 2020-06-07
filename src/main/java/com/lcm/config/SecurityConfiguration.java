package com.lcm.config;

import com.lcm.security.jwt.JWTConfigurer;
import com.lcm.security.jwt.TokenProvider;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService, TokenProvider tokenProvider, CorsFilter corsFilter) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable().addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // don't create session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/login/uacLogin").permitAll()
                .antMatchers("/login/loginOut").permitAll()
                .antMatchers("/menu/all").permitAll()
                .antMatchers("/line/sites").permitAll()
                .antMatchers("/line/fabs").permitAll()
                .antMatchers("/line/areas").permitAll()
                .antMatchers("/demo/**").permitAll()
                .antMatchers("/line/**").hasAuthority("LINE_SETTING")
                .antMatchers("/modModel/**").hasAuthority("MODEL_SETTING")
                .antMatchers("/eqpCapa/**").hasAuthority("CAPA_SETTING")
                .antMatchers("/modChange/**").hasAuthority("CHANGE_RULE")
                .antMatchers("/parameter/**").hasAuthority("SYSTEM_PARAMETER")
                .antMatchers("/plan/**").hasAuthority("PPCPLAN_MAINTAIN")
                .antMatchers("/special/**").hasAuthority("SPECIAL_MAINTAIN")
                .antMatchers("/pm/**").hasAuthority("PM_MAINTAIN")
                .antMatchers("/capa/**").hasAuthority("CAPA_CONSTRAINT")
                .antMatchers("/score/**").hasAuthority("EPLANNER_SCHEDULE")
                .antMatchers("/job/**").hasAuthority("EPLANNER_EDIT")
                .antMatchers("/adjustment/**").hasAuthority("PLAN_ADJUST_REPORT")
                .antMatchers("/report/**").hasAuthority("LEAKAGE_REPORT")
                .antMatchers("/match/**").hasAuthority("PLAN_MATCH_REPORT")
                .antMatchers("/**").authenticated();
//                .and()
//                .apply(securityConfigurationAdapter());
    }

    private JWTConfigurer securityConfigurationAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
