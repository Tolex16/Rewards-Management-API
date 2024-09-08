package com.balancee.Balancee.Service.ServiceImpl;

import com.balancee.Balancee.Domain.Entity.Customer;
import com.balancee.Balancee.Service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${balancee.app.jwtsecret}")
    private String SECRET;

    public String genToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getLoginKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimResolvers){
        final Claims claims = extraAllClaims(token);
        return claimResolvers.apply(claims);
    }

    public Key getLoginKey(){
        byte[] key = Decoders.BASE64URL.decode(SECRET);
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extraAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getLoginKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public Long getCustomerId() {
    // Fetch the authenticated principal
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof Customer) {
        Customer customer = (Customer) principal;
		return customer.getCustomerId();
    } else {
        // If the principal is just a username (String), handle this appropriately
        throw new IllegalStateException("User is not authenticated properly");
    }
    }
	    
}
