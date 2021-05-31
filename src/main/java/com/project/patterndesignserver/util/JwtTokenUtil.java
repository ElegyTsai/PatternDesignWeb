package com.project.patterndesignserver.util;

import com.project.patterndesignserver.model.member.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    public static String crateToken(String username, List<String> roles, boolean isRemember){
        long expiration =isRemember? EXPIRATION:EXPIRATION_REMEMBER;
        HashMap<String,Object> map = new HashMap<>();
        String role=String.join(",",roles);
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

    public static List<SimpleGrantedAuthority> getUserRole(String token) {
        String roles = (String) getTokenBody(token).get(ROLE_CLAIMS);
        List<SimpleGrantedAuthority> role = new ArrayList<>();
        for(String roleName : roles.split(",")){
            role.add(new SimpleGrantedAuthority(roleName));
        }
        return role;

    }

    public static boolean isExpiration(String token) {return getTokenBody(token).getExpiration().before(new Date());}

    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
//    public static boolean getId(long id){return
}
