package com.project.patterndesignserver.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

public class JwtTokenUtil {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    //密钥
    private static final String SECRET = "jwtSECRET";
    //签发者
    private static final String ISS = "long";

    private static final String ROLE_CLAIMS = "rol";

    private static final long EXPIRATION = 3600L;

    private static final long EXPIRATION_REMEMBER = 604800L;

    public static String crateToken(String username,String role, boolean isRemember){
        long expiration =isRemember? EXPIRATION:EXPIRATION_REMEMBER;
        HashMap<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS,role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+(expiration*1000)))
                .compact();
    }
    public static String getUsername(String token) {return getTokenBody(token).getSubject();}

    public static String getUserRole(String token) {return (String) getTokenBody(token).get(ROLE_CLAIMS);}

    public static boolean isExpiration(String token) {return getTokenBody(token).getExpiration().before(new Date());}

    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
