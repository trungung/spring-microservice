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
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Reactor webflux and reactor netty
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("io.projectreactor:reactor-core:3.4.1")
    implementation("junit:junit:4.12")
    testImplementation("io.projectreactor:reactor-test:3.4.1")
	implementation("io.projectreactor.ipc:reactor-netty:0.7.15.RELEASE")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("org.jsoup:jsoup:1.13.1")
	implementation("com.google.code.gson:gson:2.8.6")

	// Json parser with jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.okta.spring:okta-spring-boot-starter:1.2.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
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
