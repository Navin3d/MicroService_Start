package gmc.project.textnchat.users.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import gmc.project.textnchat.users.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private final BCryptPasswordEncoder bcycryptPasswordEncoder;
	private final UserService userService;
	private final Environment environment;
	
	public WebSecurityConfiguration(BCryptPasswordEncoder bcycryptPasswordEncoder, UserService userService, Environment environment) {
		super();
		this.bcycryptPasswordEncoder = bcycryptPasswordEncoder;
		this.userService = userService;
		this.environment = environment;
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/user/**").permitAll()
		.and()
			.headers().frameOptions().disable()
		.and()
			.csrf().disable();
		
		http.addFilter(getAuthenticationFilter());
		
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authFilter = new AuthenticationFilter(userService, environment, authenticationManager());
		authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authFilter;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bcycryptPasswordEncoder);
	}

}
