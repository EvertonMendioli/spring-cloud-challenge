package br.com.caelum.eats.pagamento;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableCircuitBreaker
@EnableResourceServer
public class EatsPagamentoServiceApplication {

	public RequestInterceptor getInterceptorDeAutenticacao() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template) {
				org.springframework.security.core.Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
				 if(authentication==null) {
					 return;
				 }
				
				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
				template.header("Authorization", "Bearer" + details.getTokenValue());
						
			}
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EatsPagamentoServiceApplication.class, args);
	}

}
