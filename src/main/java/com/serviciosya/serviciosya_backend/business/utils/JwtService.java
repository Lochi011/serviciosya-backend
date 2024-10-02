package com.serviciosya.serviciosya_backend.business.utils;

import com.serviciosya.serviciosya_backend.business.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service

public class JwtService {

    private static final String SECRET_KEY="BOLSODECANOxZIBv6ak4Do5H1I7OvBZLksPbD8ECzZxvLNrnT8XjQw=";
    public String getToken(UserDetails usuario) {
        return getToken(new HashMap<>(),usuario);
    }

    private  String getToken(Map<String,Object> extraClaims, UserDetails usuario) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
