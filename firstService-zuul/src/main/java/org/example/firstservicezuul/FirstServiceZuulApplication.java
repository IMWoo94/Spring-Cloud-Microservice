package org.example.firstservicezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class FirstServiceZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstServiceZuulApplication.class, args);
	}

}
