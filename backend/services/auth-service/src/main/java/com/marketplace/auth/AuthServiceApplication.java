package com.marketplace.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;
import java.time.ZoneId;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        System.out.println("ZoneId = " + java.time.ZoneId.systemDefault());
        System.out.println("TimeZone = " + java.util.TimeZone.getDefault().getID());
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
