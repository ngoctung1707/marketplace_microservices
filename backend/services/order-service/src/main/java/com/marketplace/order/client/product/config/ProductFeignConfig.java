package com.marketplace.order.client.product.config;

import org.springframework.context.annotation.Bean;

import com.marketplace.order.client.product.exception.ProductFeignErrorDecoder;

import feign.codec.ErrorDecoder;

public class ProductFeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ProductFeignErrorDecoder();
    }

}
