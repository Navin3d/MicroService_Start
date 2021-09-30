package gmc.project.textnchat.api.gateway.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter> {

	private final Environment environment;
	
	private String signingKey = Base64.getEncoder().encodeToString("signingKey".getBytes(StandardCharsets.UTF_8));
	
	public AuthorizationHeaderFilter(Environment environment) {
		super(AuthorizationHeaderFilter.class);
		this.environment = environment;
	}
	
	public static class Config {}

	@Override
	public GatewayFilter apply(AuthorizationHeaderFilter config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
		
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeader.replace("Bearer", "");
			
			if(!isJwtValid(jwt))
				return onError(exchange, "Invalid Jwt", HttpStatus.UNAUTHORIZED);
			
			return chain.filter(exchange);
		
		};
	}
	
	private Mono<Void> onError(ServerWebExchange exchanage, String err, HttpStatus status) {
		ServerHttpResponse response = exchanage.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	
	private boolean isJwtValid(String jwt) {
		boolean returnValue = true;

		String subject = null;

		try {
			subject = Jwts.parser()
					.setSigningKey(signingKey)
					.parseClaimsJws(jwt)
					.getBody().getSubject();
		} catch (Exception e) {
			returnValue = false;
		}

		if(subject == null || subject.isEmpty())
			returnValue = false;

		return returnValue;
	}

}
