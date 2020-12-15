package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class GiftCertificateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GiftCertificateApplication.class, args);
    }

}
