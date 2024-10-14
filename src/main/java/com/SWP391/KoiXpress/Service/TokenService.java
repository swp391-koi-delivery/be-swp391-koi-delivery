package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.AuthException;
import com.SWP391.KoiXpress.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;



@Service
public class TokenService {

    @Autowired
    UserRepository userRepository;

    public final String KEY_SECRET="c4a7e5bc8d9a53b249f9e7a3eb8d44f4c281cfe6b327f0b2f86f5c9a7e3408d1";
    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(KEY_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //tạo token
    public String generateToken(User user){
        String token = Jwts.builder()
                .subject(user.getId() + "")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000*60*60*24))
                .signWith(getSignKey())
                .compact();
        return token;
    }

    //xác thực token
    public User getUserByToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String idString = claims.getSubject().trim();
        long id = Long.parseLong(idString);
        return userRepository.findUserById(id);
    }

    //tra ve email nguoi dung
    public String getEmailByToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String idString = claims.getSubject().trim();
            long id = Long.parseLong(idString);
            return userRepository.findEmailById(id);
        }catch(ExpiredJwtException e){
            throw new AuthException("Token expired");
        }catch (SignatureException | MalformedJwtException e){
            throw new AuthException("Token not valid");
        }
    }

}
