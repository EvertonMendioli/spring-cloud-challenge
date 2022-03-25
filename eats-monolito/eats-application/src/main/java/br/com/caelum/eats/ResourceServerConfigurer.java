package br.com.caelum.eats;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

	public void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/admin/*")
			.hasRole("ADMINISTRATIVO");
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.PATCH, "/admin/*")
			.hasRole("ADMINISTRATIVO");
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/parceiros/restaurantes")
			.hasAnyRole("RESTAURANTE","ADMINISTRATIVO");

		http.authorizeRequests()
			.antMatchers(HttpMethod.PUT, "/parceiros/restaurantes/{id}")
			.hasAnyRole("RESTAURANTE","ADMINISTRATIVO");
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/pedidos/*")
			.hasAnyRole("CLIENTE","ADMINISTRATIVO");
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/pedidos/*")
			.hasAnyRole("CLIENTE","ADMINISTRATIVO");
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.PUT, "/pedidos/*")
			.hasAnyRole("CLIENTE","ADMINISTRATIVO");

	}
}
