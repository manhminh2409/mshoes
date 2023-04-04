package com.mshoes.mshoes.config;

import com.mshoes.mshoes.securities.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) -> authz.requestMatchers(HttpMethod.GET, "/products/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/users/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/categories/**").permitAll().anyRequest().authenticated())
				.httpBasic(withDefaults()).csrf().disable();
		return http.build();
	}

	protected void filterChain(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService((userDetailsService)).passwordEncoder((passwordEncoder()));
	}
//	@Bean
//	public UserDetailsManager userDetailsService() {
//		UserDetails user = User.builder().username("manhminh").password(passwordEncoder().encode("24092001"))
//				.roles("USER").build();
//		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//				.build();
//		InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
//		users.createUser(user);
//		users.createUser(admin);
//		return users;
//	}
}
