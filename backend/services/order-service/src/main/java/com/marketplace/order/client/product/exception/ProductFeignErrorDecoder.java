package com.marketplace.order.client.product.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class ProductFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {

            case 404 -> new ProductNotFoundException();

            default -> new RuntimeException(
                    "Product Service Error: " + response.status());

        };

    }

}
