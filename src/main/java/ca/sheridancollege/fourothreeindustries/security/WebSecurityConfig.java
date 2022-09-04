package ca.sheridancollege.fourothreeindustries.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ca.sheridancollege.fourothreeindustries.filters.CustomAuthFilter;
import ca.sheridancollege.fourothreeindustries.filters.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;



@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	private final UserDetailsService usd;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usd).passwordEncoder(NoOpPasswordEncoder.getInstance());
		
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//no security
		//http.authorizeRequests().anyRequest().permitAll();
		// http.csrf().disable();
		 //   http.headers().frameOptions().disable();
		   
		//security
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/api/test/login").permitAll();
		http.authorizeRequests().antMatchers("/api/test/conn").hasAuthority("ADMIN");
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManagerBean());
		filter.setFilterProcessesUrl("/api/test/login");
		http.addFilter(filter);
		http.addFilterBefore(new CustomAuthFilter(), UsernamePasswordAuthenticationFilter.class);
		
	

	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManager();
	}
	
	
	
}
