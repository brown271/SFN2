package ca.sheridancollege.fourothreeindustries.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.*;

import ca.sheridancollege.fourothreeindustries.domain.Account;
import lombok.AllArgsConstructor;

import org.hibernate.mapping.Collection;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;



public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authManager;
	
	public CustomAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		String username = "";
		String password ="";
		try {
			//String test =  request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			String test =  request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			username = test.substring(13);
			username = username.substring(0,username.indexOf('"'));
			password = test.substring(test.lastIndexOf(':')+2);
			password = password.substring(0,password.indexOf('"'));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println(authManager);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
		return authManager.authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		Account user = (Account)auth.getPrincipal();
		Algorithm algo = Algorithm.HMAC256("temp".getBytes());
		String accessToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algo);
		response.setHeader("token", accessToken);
	}
	

}
