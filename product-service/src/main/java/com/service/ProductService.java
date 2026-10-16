package com.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.dto.ProductDto;
import com.model.Product;
import com.model.ProductType;
import com.model.User;
import com.repository.ProductRepository;
import com.exception.MyException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }
    
    private String getProductNotFoundError(Long id) {
        return String.format("Продукт с id = %d не существует",id);
    }

    @Transactional
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    private ProductDto productDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType().name(),
                product.getUser().getId()
        );
    }

    public List<ProductDto> getProductsByUserId(Long userId) {
        return productRepository.findByUser_Id(userId).stream()
                .map(this::productDto)
                .collect(Collectors.toList());
    }

        
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::productDto).orElseThrow(() -> 
                 new EntityNotFoundException(getProductNotFoundError(id))
                );
    }

    private Product checkData(Long productId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MyException(HttpStatus.BAD_REQUEST, "Дельта должна быть больше нуля");
         }    
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(getProductNotFoundError(productId)));
    }

    @Transactional
    public ProductDto decBalance(Long productId, BigDecimal amount) {
        Product product = checkData(productId, amount);
        if (product.getBalance().compareTo(amount) < 0) {
            throw new MyException(HttpStatus.BAD_REQUEST, "Баланс не должен становиться отрицательным");
        }
        product.setBalance(product.getBalance().subtract(amount));
        Product saved = productRepository.save(product);
        return productDto(saved);
    }

    @Transactional
    public ProductDto incBalance(Long productId, BigDecimal amount) {
        Product product = checkData(productId, amount);
        product.setBalance(product.getBalance().add(amount));
        Product saved = productRepository.save(product);
        return productDto(saved);
    }

 
public List<ProductDto> getAllProductsDto() {
        return productRepository.findAll().stream()
                .map(this::productDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto createProductDto(String accountNumber, BigDecimal balance, ProductType type, Long userId) {
        User user = userService.getUserById(userId);
        Product product = new Product(accountNumber, balance, type, user);
        Product saved = productRepository.save(product);
        return productDto(saved);
    }
 
}