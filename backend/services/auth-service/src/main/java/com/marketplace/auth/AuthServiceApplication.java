package com.marketplace.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class AuthServiceApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        System.out.println("ZoneId = " + java.time.ZoneId.systemDefault());
        System.out.println("TimeZone = " + java.util.TimeZone.getDefault().getID());
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
