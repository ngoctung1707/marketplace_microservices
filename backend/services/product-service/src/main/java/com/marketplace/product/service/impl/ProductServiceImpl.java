package com.marketplace.product.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.product.client.category.CategoryDto;
import com.marketplace.product.client.category.CategoryServiceClient;
import com.marketplace.product.client.shop.ShopDto;
import com.marketplace.product.client.shop.ShopServiceClient;
import com.marketplace.product.dto.request.CreateProductRequest;
import com.marketplace.product.dto.request.UpdateProductRequest;
import com.marketplace.product.dto.response.ProductResponse;
import com.marketplace.product.entity.Product;
import com.marketplace.product.entity.ProductStatus;
import com.marketplace.product.exception.CategoryNotFoundException;
import com.marketplace.product.exception.ProductAlreadyExistsException;
import com.marketplace.product.exception.ProductNotFoundException;
import com.marketplace.product.exception.ShopNotFoundException;
import com.marketplace.product.mapper.ProductMapper;
import com.marketplace.product.repository.ProductRepository;
import com.marketplace.product.service.ProductService;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    private final ShopServiceClient shopServiceClient;

    private final CategoryServiceClient categoryServiceClient;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        // Sinh slug từ tên sản phẩm
        String slug = request.getName()
                .trim()
                .toLowerCase()
                .replace(" ", "-");

        if (repository.existsBySlug(slug)) {
            throw new ProductAlreadyExistsException(slug);
        }

        // Kiểm tra Shop tồn tại
        try {
            shopServiceClient.getShopById(request.getShopId());
        } catch (FeignException.NotFound ex) {
            throw new ShopNotFoundException();
        }

        // Kiểm tra Category tồn tại
        try {
            categoryServiceClient.getCategoryById(request.getCategoryId());
        } catch (FeignException.NotFound ex) {
            throw new CategoryNotFoundException();
        }

        Product product = mapper.toEntity(request);

        product.setSlug(slug);
        product.setStatus(ProductStatus.DRAFT);

        Product savedProduct = repository.saveAndFlush(product);

        System.out.println("===== AFTER SAVE =====");
        System.out.println(savedProduct.getCreatedAt());
        System.out.println(savedProduct.getUpdatedAt());

        savedProduct = repository.findById(savedProduct.getId())
                .orElseThrow(ProductNotFoundException::new);

        System.out.println("===== AFTER FIND =====");
        System.out.println(savedProduct.getCreatedAt());
        System.out.println(savedProduct.getUpdatedAt());

        return buildProductResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(UUID productId) {

        Product product = repository.findByIdAndActiveTrue(productId)
                .orElseThrow(ProductNotFoundException::new);

        return buildProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByShop(UUID shopId) {

        return repository.findByShopId(shopId)
                .stream()
                .map(this::buildProductResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {

        return repository.findByActiveTrue()
                .stream()
                .map(this::buildProductResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(
            UUID productId,
            UpdateProductRequest request) {

        Product product = repository.findByIdAndActiveTrue(productId)
                .orElseThrow(ProductNotFoundException::new);

        // Nếu đổi Category thì kiểm tra lại
        if (request.getCategoryId() != null) {
            try {
                categoryServiceClient.getCategoryById(request.getCategoryId());
            } catch (FeignException.NotFound ex) {
                throw new CategoryNotFoundException();
            }
        }

        mapper.updateEntityFromRequest(request, product);

        Product updatedProduct = repository.save(product);

        return buildProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {

        Product product = repository.findByIdAndActiveTrue(productId)
                .orElseThrow(ProductNotFoundException::new);

        product.setActive(false);

        repository.save(product);
    }

    /**
     * Bổ sung thông tin Shop và Category vào ProductResponse.
     */
    private ProductResponse buildProductResponse(Product product) {

        System.out.println("===== BUILD RESPONSE =====");
        System.out.println("Entity createdAt = " + product.getCreatedAt());
        System.out.println("Entity updatedAt = " + product.getUpdatedAt());

        ProductResponse response = mapper.toResponse(product);

        System.out.println("Response createdAt = " + response.getCreatedAt());
        System.out.println("Response updatedAt = " + response.getUpdatedAt());

        ShopDto shop = shopServiceClient.getShopById(product.getShopId());

        CategoryDto category = categoryServiceClient.getCategoryById(product.getCategoryId());

        response.setShopName(shop.getShopName());
        response.setCategoryName(category.getName());

        return response;
    }
}
