/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccet.backend.api.v1.jwtsecurity.security;

import com.ccet.backend.api.v1.jwtsecurity.model.JwtUser;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

/**
 * @author SAGAR MAHOBIA
 */
@Component
public class JwtUtil {

    private static final String signingKey = "MyKey_rAnDoM";

    JwtUser validate(String token) {
        JwtUser jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new JwtUser();
            jwtUser.setId(Integer.parseInt(body.getSubject()));
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException ignored) {
            //todo handle error.
        }

        return jwtUser;
    }

    public String generate(JwtUser jwtUser) {

        Claims claims = Jwts.claims().setSubject(String.valueOf(jwtUser.getId()));


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }
}
