package com.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import com.dto.ProductDto;
import com.model.ProductType;
import com.model.User;
import com.service.ProductService;
import com.service.UserService;

@Component
public class Runner implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;
    private final ConfigurableApplicationContext context;

    public Runner(UserService userService, ProductService productService,
                  ConfigurableApplicationContext context) {
        this.userService = userService;
        this.productService = productService;
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {

        User admin = userService.findByUsername("Admin")
                .orElseThrow(() -> new RuntimeException("Пользователь Admin не найден"));
        User testUser = userService.findByUsername("TestUser1")
                .orElseThrow(() -> new RuntimeException("Пользователь TestUser не найден"));

        userService.getAllUsers().forEach(System.out::println);

        productService.deleteAllProducts();

        ProductDto account1 = productService.createProductDto(
                "40817810000000000001",
                BigDecimal.valueOf(15000.50),
                ProductType.ACCOUNT,
                testUser.getId()
        );
        ProductDto card1 = productService.createProductDto(
                "4276000000000001",
                BigDecimal.valueOf(5000.00),
                ProductType.CARD,
                testUser.getId()
        );

        System.out.println("Продукты пользователя " + testUser.getUsername() + ":");
        productService.getProductsByUserId(testUser.getId())
                .forEach(System.out::println);

 //       int exitCode = SpringApplication.exit(context);
        //System.exit(exitCode);
    }
}