package ca.sheridancollege.fourothreeindustries.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class CustomAuthFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("FILTER");
		if(request.getServletPath().equals("/api/test/login")) {
			System.out.println("LOGIN");
			filterChain.doFilter(request, response);
		}else {
			String authHeader = request.getHeader("Authorization");
			
			System.out.println("other");
			if(authHeader != null && authHeader.startsWith("Bear")) {
				try {
					System.out.println(authHeader);
					String token = authHeader.substring(7);
					System.out.println(token);
					Algorithm algo = Algorithm.HMAC256("temp".getBytes());
					JWTVerifier verifier = JWT.require(algo).build();
					DecodedJWT decoded = verifier.verify(token);
					String username = decoded.getSubject();
					String[] roles = decoded.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
					for (String role: roles) {
						System.out.println(role);
						auths.add(new SimpleGrantedAuthority(role));
					}
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, auths);
					System.out.println(authToken);
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
					
				}catch(Exception e) {
					System.out.println("Error: " + e);
					response.setHeader("Error", e.getMessage());
					response.sendError(403);
				}

			}
			else {
				filterChain.doFilter(request, response);
			}
		}
	}

}
