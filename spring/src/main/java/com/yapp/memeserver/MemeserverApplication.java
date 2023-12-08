package com.yapp.memeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화. @WebMvcTest와 충돌난다.
@SpringBootApplication
public class MemeserverApplication {

	// aws sdk 에러가 나는 이유는, build.gradle에, spring-cloud-starter-aws 의존성 주입시 로컬환경은, aws환경이 아니라서.
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

	public static void main(String[] args) {
		SpringApplication.run(MemeserverApplication.class, args);
	}

}
