package jc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class FTIGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FTIGatewayApplication.class, args);
	}
}
