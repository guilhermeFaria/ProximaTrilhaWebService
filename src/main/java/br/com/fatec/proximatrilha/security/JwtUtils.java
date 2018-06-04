package br.com.fatec.proximatrilha.security;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fatec.proximatrilha.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    
    private static final String secretKey = "spring.jwt.sec";
    
    public static String generateToken(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);
        Date now = new Date();
        Long hour = 1000L * 60L * 60L;
        return Jwts.builder().claim("userDetails", userJson)
            .setIssuer("br.com.fatec.proximatrilha")
            .setSubject(user.getName())
            .setExpiration(new Date(now.getTime() + hour))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }
    
    public static User parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String credentialsJson = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userDetails", String.class);
        return mapper.readValue(credentialsJson, User.class);
    }

}