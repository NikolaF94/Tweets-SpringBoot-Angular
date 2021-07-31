package com.MasterTweetsAnalysis.config;

import com.MasterTweetsAnalysis.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ObjectMapper objectMapper;
	@Autowired
	private UserServiceImpl userDetailsService;

	public SecurityConfig(UserServiceImpl userDetailsService, ObjectMapper objectMapper) {
		this.userDetailsService = userDetailsService;
		this.objectMapper = objectMapper;
	}


	@Bean
	public RequestBodyReaderAuthenticationFilter authenticationFilter() throws Exception {
		RequestBodyReaderAuthenticationFilter authenticationFilter
				= new RequestBodyReaderAuthenticationFilter();
		authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
		authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
		authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/validateLogin", "POST"));
		authenticationFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().

				authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated()
				.and().httpBasic()
				.and()
				.addFilterBefore(
						authenticationFilter(),
						UsernamePasswordAuthenticationFilter.class)
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler(this::logoutSuccessHandler)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				.and()
				.cors();

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","DELETE"));
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(false);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}



	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() throws AuthenticationException{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authProvider;
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	private void loginSuccessHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException {

		response.setStatus(HttpStatus.OK.value());

		System.out.println("User successfully authenticated");

		objectMapper.writeValue(response.getWriter(), "User successfully authenticated");
	}

	private void loginFailureHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException e) throws IOException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		System.out.println("Invalid username /and password!");
		objectMapper.writeValue(response.getWriter(), "Invalid username /and password!");
	}
	private void logoutSuccessHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException {

		response.setStatus(HttpStatus.OK.value());
		objectMapper.writeValue(response.getWriter(), "You logged out");
	}

}









