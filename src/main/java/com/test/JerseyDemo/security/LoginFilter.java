package com.test.JerseyDemo.security;

import java.io.IOException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

import com.test.JerseyDemo.beans.Utente;

import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;


@Provider
@LoginBind
public class LoginFilter implements ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext requestContext,
	          ContainerResponseContext responseContext) throws IOException {
			
		System.out.println("called "+requestContext.getUriInfo().getPath());
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "CSRF-Token, X-Requested-By, Authorization, Content-Type");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		
		String auth = requestContext.getHeaderString("Authorization");
		boolean logged = false;
		
		//logged = true;
		
		if(auth != null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);
			Utente u = null;
			try {
				JwtParserBuilder builder = Jwts.parserBuilder();
				JwtParser parser = builder.setSigningKey(JwtTools.jwtKey).build();
				Jws<Claims> claims = parser.parseClaimsJws(token);
				Claims claimBody = claims.getBody();
				String userString = claimBody.get("user").toString();
				//System.out.print(userString);
				u = JsonbBuilder.create().fromJson(userString, Utente.class);
				//requestContext.setProperty("user", u);
				if(requestContext.getUriInfo().getPath().equals("utenti/profilo")) {
					responseContext.setEntity(u);
				}
			}catch(SignatureException se){
				se.getMessage();
			}
			if(u!=null) {
				logged = true;
			}
		}

		if(!logged) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
		}
	}

}
