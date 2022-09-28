package com.soda.apiserver.user.util;

import com.soda.apiserver.user.model.entity.LoginLog;
import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.user.repository.LoginLogRepository;
import com.soda.apiserver.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token-validity-in-seconds}")
    private Integer expireSecond;
    private final LoginLogRepository loginLogRepository;
    private final UserRepository userRepository;

    @Autowired
    JwtUtil(LoginLogRepository loginLogRepository, UserRepository userRepository){
        this.loginLogRepository = loginLogRepository;
        this.userRepository = userRepository;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(String username) {

        String token = null;
        Map<String, Object> claims = new HashMap<>();
        token = createToken(claims, username);
        if(token != null){
            User user = userRepository.findByUserName(username);
            loginLogRepository.save(new LoginLog(user, new java.sql.Date(System.currentTimeMillis())));
        }
        return token;
    }
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * expireSecond))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }}
