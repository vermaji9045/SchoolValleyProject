package com.School.SchoolValleyProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories("com.School.SchoolValleyProject.repository")
@EntityScan("com.School.SchoolValleyProject.Model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
//@EnableFeignClients(basePackages = "com.School.SchoolValleyProject.proxy")
public class SchoolValleyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolValleyProjectApplication.class, args);
	}

}
