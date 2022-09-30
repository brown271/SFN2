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
		String username = ""; //blank username and password
		String password ="";
		try {
			//Join all the headers into one string
			String header =  request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			//pull username and password from the string
			username = header.substring(13);
			username = username.substring(0,username.indexOf('"'));
			password = header.substring(header.lastIndexOf(':')+2);
			password = password.substring(0,password.indexOf('"'));
		} catch (IOException e) {
			//catch any IOExceptions 
			e.printStackTrace();
		}
		//try to authenticate the token
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
		return authManager.authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		//get the User from the auth manager
		Account user = (Account)auth.getPrincipal();
		//temporary hashing algorithim
		Algorithm algo = Algorithm.HMAC256("temp".getBytes());
		//generate their JWT
		String accessToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algo);
		//return their JWT
		response.addHeader("Key", accessToken);
	}
	

}
