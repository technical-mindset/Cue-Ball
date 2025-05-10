package com.cueball.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackages = {"com.cueballdb.repository"} )
@EntityScan(basePackages = "com.cueballdb.model")
@SpringBootApplication
@EnableScheduling
public class CueBallPortal extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CueBallPortal.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CueBallPortal.class);
    }

}
