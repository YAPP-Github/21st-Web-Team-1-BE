plugins {
	java
	id("org.springframework.boot") version "2.7.6"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.yapp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")

	// DataBase 의존성
	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")

	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// p6spy 의존성 추가
	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.0")

	// Security 의존성 추가
	implementation("org.springframework.boot:spring-boot-starter-security")

	// OAuth2 의존성 추가
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	// Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
