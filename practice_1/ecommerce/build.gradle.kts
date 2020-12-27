import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.0"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.10"
	kotlin("plugin.spring") version "1.4.10"
}

group = "com.api"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {

	// Spring boot starter web
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("com.okta.spring:okta-spring-boot-starter:1.2.1")

	// Spring boot validation
	implementation("org.springframework.boot:spring-boot-starter-validation:2.4.1")

	// Data rest and configuration
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	// Spring security with JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation("io.jsonwebtoken:jjwt-orgjson:0.11.2")

	// Spring Test junit, jsoup
	testImplementation("junit:junit:4.12")
	testImplementation("org.jsoup:jsoup:1.13.1")
	implementation("com.google.code.gson:gson:2.8.6")
	testImplementation("org.junit.platform:junit-platform-commons:1.7.0")
	testRuntimeOnly("org.junit.platform:junit-platform-commons:1.7.0")
	// Json parser with jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Swagger UI
	implementation("io.springfox:springfox-boot-starter:3.0.0")

	// PostgresSql
	implementation("org.postgresql:postgresql")

	// H2database
	testImplementation("com.h2database:h2:1.3.148")
	implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
