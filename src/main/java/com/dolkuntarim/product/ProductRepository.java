package com.dolkuntarim.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.user_uuid = ?1")
    List<Product> findAllByUser(String user_uuid);

}
