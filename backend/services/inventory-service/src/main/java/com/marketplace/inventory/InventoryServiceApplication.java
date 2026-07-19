package com.marketplace.inventory;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InventoryServiceApplication {

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        SpringApplication.run(
                InventoryServiceApplication.class,
                args);
    }

}
