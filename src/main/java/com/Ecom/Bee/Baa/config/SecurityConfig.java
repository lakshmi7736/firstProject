package com.Ecom.Bee.Baa.config;


import com.Ecom.Bee.Baa.Handler.LoginFailureHandler;
import com.Ecom.Bee.Baa.Handler.LoginSuccessHandler;
import com.Ecom.Bee.Baa.Security.JwtAuthenticationEntryPoint;
import com.Ecom.Bee.Baa.Security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


	private final LoginSuccessHandler loginSuccessHandler;

	private final LoginFailureHandler failureHandler;


	
	private final PasswordEncoder passwordEncoder;

	private final UserDetailsService userDetailsService;

	private final JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.csrf(csrf->csrf.disable())
				.cors(cors->cors.disable())
				.authorizeHttpRequests((requests) -> requests
		                .requestMatchers("/sec/**").hasRole("ADMIN")
						.requestMatchers("/**").permitAll()
						.requestMatchers("/frontEnd/**","/assets/**").permitAll()
								.anyRequest().authenticated()
				)
				.formLogin((form) -> form
						.loginPage("/authentication-login")
						.successHandler(loginSuccessHandler)
						.failureHandler(failureHandler)
						.loginProcessingUrl("/authentication-login")
						.permitAll()
				)
//				.logout(LogoutConfigurer::permitAll)
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		;
//		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
