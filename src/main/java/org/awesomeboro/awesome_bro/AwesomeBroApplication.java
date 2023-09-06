package org.awesomeboro.awesome_bro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages ={"org.awesomeboro.awesome_bro"})
@EnableJpaAuditing
public class AwesomeBroApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwesomeBroApplication.class, args);
	}

}
