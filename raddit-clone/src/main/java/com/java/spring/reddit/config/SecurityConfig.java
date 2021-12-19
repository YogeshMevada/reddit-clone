package com.java.spring.reddit.config;

import com.java.spring.reddit.interceptor.ApiRequestInterceptor;
import com.java.spring.reddit.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthenticationEntryPoint restAuthenticationEntryPoint;

    private final ApiRequestInterceptor apiRequestInterceptor;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
//                .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll();
//                .antMatchers("/api/v1/auth/**")
//                .permitAll()
//                .antMatchers(HttpMethod.GET, "/api/v1/subreddit", "/api/v1/post")
//                .permitAll()
//                .antMatchers("/v2/api-docs",
//                        "/configuration/ui",
//                        "/swagger-resources/**",
//                        "/configuration/security",
//                        "/swagger-ui.html",
//                        "/webjars/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated();

//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(apiRequestInterceptor)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/**/auth/**");
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
//
//    @Bean(BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void addCorsMappings(final CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:4200")
//                .allowedMethods("GET", "POST")
//                .maxAge(3600L)
//                .allowedHeaders("*")
//                .exposedHeaders("Authorization")
//                .allowCredentials(true);
//    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
