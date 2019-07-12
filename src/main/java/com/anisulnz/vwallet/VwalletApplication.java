package com.anisulnz.vwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VwalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(VwalletApplication.class, args);
	}

}
