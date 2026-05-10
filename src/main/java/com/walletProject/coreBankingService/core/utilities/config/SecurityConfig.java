package com.walletProject.coreBankingService.core.utilities.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Az önce oluşturduğumuz filtreyi buraya enjekte ediyoruz
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            
            // Veritabanı veya oturum yok, tamamen Stateless!
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(auth -> auth
                // Sadece ortak belgelendirme ve hata sayfalarına biletsiz izin var.
                // (Burada /api/v1/auth/login GİBİ YOLLAR YOK, çünkü bu servis giriş servisi değil!)
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/actuator/**",
                        "/error"
                ).permitAll()
                
                // Core Banking'in geri kalan BÜTÜN uç noktaları (Örn: /api/v1/accounts) token gerektirir!
                .anyRequest().authenticated()
            )
            
            // Sadece kendi JWT filtremizi ekliyoruz. Veritabanı filtresi (Provider) YOK!
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
