package com.sparkx.damsy.service;

import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import com.sparkx.damsy.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class AuthService {
    
    public static String createToken(User user) {
        
        Date today = new Date();        //get the current time
        
        Calendar instance = Calendar.getInstance();     // add token expiration time /days- hours- minuts or seconds
        
        instance.setTime(today);    //set current date
        
        instance.add(Calendar.MINUTE, 90);      //add 90 minits to the current time
        
        Date exTime = instance.getTime();       //token expire time

        //generate secret key for token
        SecretKey secretKey = Keys.hmacShaKeyFor("Spark@ABCD123efghijklmnopqrstuvwxyz".getBytes());

        //create a token
        String compact = Jwts.builder()
                .setIssuer("Damsy@NCMS")
                .setSubject(user.getUserName())
                .setIssuedAt(today)
                .setExpiration(exTime)
                .claim("role", user.getRole())
                // .claim("name", user.getUserName())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        return compact;
    }


    public static Jws<Claims> getClaims(String jws) {
        SecretKey secretKey = Keys.hmacShaKeyFor("Spark@ABCD123efghijklmnopqrstuvwxyz".getBytes());
        try {
            String token = jws.split(" ")[1];
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        } catch (Exception ex) {
            return null;
        }
    }

    public static boolean checkValidity(String jws) {
        Jws<Claims> claims = getClaims(jws);
        if (claims != null) {
            Date expiration = claims.getBody().getExpiration();
            Date today = new Date();
            return today.before(expiration);
        } else {
            return false;
        }
    }

    public String getUserName(String jwt) {
        SecretKey secretKey = Keys.hmacShaKeyFor("Spark@ABCD123efghijklmnopqrstuvwxyz".getBytes());
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().getSubject();
    }
}
