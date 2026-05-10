package com.walletProject.coreBankingService.business.concretes;

import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secretKey;
	
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // İŞİN KALBİ BURASI: 
    // parseClaimsJws metodu token'ı okurken İMZAYI ve SÜREYİ otomatik kontrol eder.
    // İmza sahteyse 'SignatureException', süre dolmuşsa 'ExpiredJwtException' fırlatır!
    private Claims extractAllClaims(String token) {
    	return Jwts.parser().verifyWith(  getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
