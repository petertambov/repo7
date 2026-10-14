package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/*import java.util.Optional;
import org.springframework.lang.Nullable;
import org.springframework.data.jpa.repository.Query;*/

import java.util.List;

import com.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /*@Query("SELECT p FROM Product p WHERE p.user.id = :userId")
    List<Product> findByUserId(@Param("userId") Long userId);*/

     
    List<Product> findByUser_Id(@Param("userId") Long userId);

    /*@Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findById(@Param("id") @Nullable Long id);*/
}