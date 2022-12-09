package com.yapp.memeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화. @WebMvcTest와 충돌난다.
@SpringBootApplication
public class MemeserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeserverApplication.class, args);
	}

}
