package ca.sheridancollege.fourothreeindustries.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		  http
          .authorizeHttpRequests((authz) -> authz
              //.antMatchers("/h2-console","/h2-console/**").permitAll()
              //.antMatchers("/api/**").authenticated()
        		  .anyRequest().permitAll()
          )
          .formLogin();
          
          //.defaultSuccessUrl("/api/test/conn");
      return http.build();	
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
