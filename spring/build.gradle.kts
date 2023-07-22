plugins {
	java
	jacoco
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
	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.1")
	implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.0")

	// Security 의존성 추가
	implementation("org.springframework.boot:spring-boot-starter-security")

	// OAuth2 의존성 추가
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	// Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// Swagger
	implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
	// Spring Security를 사용하는 경우 추가. @AuthenticationPrincipal 무시해준다.
	implementation("org.springdoc:springdoc-openapi-security:1.6.11")

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// Json 변환 라이브러리
	implementation("org.json:json:20220924")

	// HikariCP
	implementation("com.zaxxer:HikariCP:4.0.3")

}

jacoco {
	// JaCoCo 버전
	toolVersion = "0.8.5"

//  테스트결과 리포트를 저장할 경로 변경
//  default는 "$/jacoco"
//  reportsDir = file("$buildDir/customJacocoReportDir")
}

tasks.test {
	useJUnitPlatform()
//	extensions.configure(JacocoTaskExtension::class) {
//		destinationFile = file("$buildDir/jacoco/jacoco.exec")
//	}
	finalizedBy("jacocoTestReport")
}

// 커버리지 기준을 만족하는지 확인해 주는 task
tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			element = "CLASS"

			limit {
				counter = "BRANCH"
				value = "COVEREDRATIO"
				minimum = "0.0".toBigDecimal()
			}

			excludes = listOf(
					//      "*.test.*",
					"*.Kotlin*"
			)

		}
	}
}

// 바이너리 커버리지 결과를 사람이 읽기 좋은 형태의 리포트로 저장합니다.
tasks.jacocoTestReport {
	reports {
		// 원하는 리포트를 켜고 끌 수 있습니다.
		html.required.set(true)
		xml.required.set(false)
		csv.required.set(false)

//  각 리포트 타입 마다 리포트 저장 경로를 설정할 수 있습니다.
//  html.destination = file("$buildDir/jacocoHtml")
//  xml.destination = file("$buildDir/jacoco.xml")
	}
	finalizedBy("jacocoTestCoverageVerification")
}
