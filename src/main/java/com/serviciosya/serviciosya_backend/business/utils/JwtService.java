package com.serviciosya.serviciosya_backend.business.utils;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;


    public String getToken(Usuario usuario) {
        return getToken(new HashMap<>(),usuario);
    }

    private  String getToken(Map<String,Object> extraClaims, Usuario usuario) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .claim("id",usuario.getId())
                .claim("cedula",usuario.getCedula())
                .claim("role",usuario.getRole())
                .subject(usuario.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith( getKey())
                .compact();
    }

    public String generatePasswordResetToken(String email) {
        return Jwts.builder()
                .setSubject(email)  // Usamos el email como subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1600000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // Usamos la misma clave secreta
                .compact();
    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public String getUsernameFromToken(String token) {
        return getClaim(token,Claims::getSubject);

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){

        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){

        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){

        return getExpiration(token).before(new Date());
    }

    public Long getUserIdFromToken(String token) {
        return Long.parseLong(getClaim(token, Claims::getId));
    }
}
