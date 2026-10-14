package com.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

import com.dto.ProductDto;
import com.service.ProductService;

@RestController
@RequestMapping(value = "/v1/products")
public class ProductController {

    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;        
    }

    @GetMapping("/all")
    public List<ProductDto> getAllProductsDto() {
        return productService.getAllProductsDto();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductDto> getProductsByUserId(@RequestParam("userId") Long userId) {        
        return productService.getProductsByUserId(userId);
    }

    @PutMapping("/{id}/inc")
    public ProductDto incBalance(@PathVariable Long id, @RequestParam BigDecimal amount) {        
        return productService.incBalance(id, amount);
    }
    @PutMapping("/{id}/dec")
    public ProductDto decBalance(@PathVariable Long id, @RequestParam BigDecimal amount) {

        return productService.decBalance(id, amount);
    }
   
}