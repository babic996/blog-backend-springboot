package com.project.blog.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.blog.exception.CustomAccessDeniedHandler;
import com.project.blog.exception.SimpleAuthenticationEntryPoint;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Autowired
	SimpleAuthenticationEntryPoint simpleAuthenticationEntryPoint;

	@Autowired
	CustomAccessDeniedHandler customAcessDeniedHandler;

	private static final String[] PERMIT_ALL_URLS = { "/user/login", "/user/register" };

	private static final String[] ADMIN_USER_URLS = { "/blog/save", "/blog/find-all-approved", "blog/find-by-id",
			"/blog/update" };

	private static final String[] ADMIN_URLS = { "/blog/find-all-no-approved", "/blog/approve" };

	// authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	// authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off

				http.cors()
					.and()
					.csrf()
					.disable()
	                .authorizeRequests()
	                .antMatchers(PERMIT_ALL_URLS).permitAll()
	                .antMatchers(ADMIN_URLS).hasRole("ADMIN")
	                .antMatchers(ADMIN_USER_URLS).hasAnyRole("ADMIN", "USER")
	                .anyRequest().authenticated()
	                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .exceptionHandling().authenticationEntryPoint(simpleAuthenticationEntryPoint)
					.accessDeniedHandler(customAcessDeniedHandler);
	            http
	                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	        // @formatter:on
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("X-Auth-Token", "Authorization", "Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
