package com.test.JerseyDemo.security;

import java.security.Key;

import com.test.JerseyDemo.beans.Utente;
import jakarta.json.bind.JsonbBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTools {

	public static Key jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public static String generateToken(Utente user) {
		String userPayload = JsonbBuilder.create().toJson(user);
		String token = Jwts.builder().claim("user", userPayload).signWith(jwtKey).compact();
		return token;
	}
}
