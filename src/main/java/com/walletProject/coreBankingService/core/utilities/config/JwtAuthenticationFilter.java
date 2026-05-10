package com.walletProject.coreBankingService.core.utilities.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.walletProject.coreBankingService.business.concretes.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        
        try {
            // Token bozuksa, sahteyse veya süresi geçmişse burası Exception fırlatır!
            username = jwtService.extractUsername(jwt);
            
            // Eğer username okunduysa ve context boşsa (DİKKAT: Veritabanına GİTMİYORUZ!)
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Sadece Token'dan gelen kullanıcı adıyla bir kimlik oluşturuyoruz
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        new ArrayList<>() // Eğer roller olsaydı token'dan okuyup buraya koyardık
                );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            // Token sahte/süresi dolmuş, hiçbir şey yapma, bırak SecurityConfig 401 dönsün
        }

        filterChain.doFilter(request, response);
		
	}

}
