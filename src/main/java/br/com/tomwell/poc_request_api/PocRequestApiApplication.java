package br.com.tomwell.poc_request_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PocRequestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocRequestApiApplication.class, args);
	}

}
