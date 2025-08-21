////package com.example.hijra_kyc.util;
////
////
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////import io.jsonwebtoken.security.Keys;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.stereotype.Component;
////
////import java.security.Key;
////import java.util.Date;
////
////@Component
////public class JwtUtil {
////
////    @Value("${jwt.secret}")
////    private String secret;
////
////    @Value("${jwt.expiration-ms}")
////    private long expirationMs;
////
////    private Key getSigningKey() {
////        // for demo use secret bytes
////        return Keys.hmacShaKeyFor(secret.getBytes());
////    }
////
////    public static String generateToken(String username, String emptire) {
////        Date now = new Date();
////        Date exp = new Date(now.getTime() + expirationMs);
////        return Jwts.builder()
////                .setSubject(username)
////                .claim("emptire", emptire)
////                .setIssuedAt(now)
////                .setExpiration(exp)
////                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
////                .compact();
////    }
////}
////
//package com.example.hijra_kyc.util;
//
//import com.example.hijra_kyc.security.CustomLdapUserDetails;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    @Value("${jwt.secret}")
//    private static String secret;
//
//    @Value("${jwt.expiration-ms}")
//    private static long expirationMs;
//
//    private static Key getSigningKey() {
//        return Keys.hmacShaKeyFor(secret.getBytes());
//    }
//
//    /**
//     * Generate JWT token from LDAP user details
//     * Includes: username, userId, branchId, firstName, lastName
//     */
//    public static String generateTokenFromLdap(CustomLdapUserDetails user) {
//        Date now = new Date();
//        Date exp = new Date(now.getTime() + expirationMs);
//
//        return Jwts.builder()
//                .setSubject(user.getUsername())          // username
//                .claim("userId", user.getUserId())       // LDAP userId
//                .claim("branchId", user.getBranchId())   // branch info
//                .claim("firstName", user.getFirstName()) // optional
//                .claim("lastName", user.getLastName())   // optional
//                .setIssuedAt(now)
//                .setExpiration(exp)
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//}
package com.example.hijra_kyc.util;

import com.example.hijra_kyc.security.CustomLdapUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate JWT token from LDAP user details
     * Includes: username, userId, branchId, firstName, lastName
     */
    public String generateTokenFromLdap(CustomLdapUserDetails user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(user.getUsername())          // username
                .claim("userId", user.getUserId())       // LDAP userId
                .claim("branchId", user.getBranchId())   // branch info
                .claim("firstName", user.getFirstName()) // optional
                .claim("lastName", user.getLastName())   // optional
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
