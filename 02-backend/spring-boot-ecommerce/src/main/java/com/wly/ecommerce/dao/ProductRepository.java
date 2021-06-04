package com.wly.ecommerce.dao;

import com.wly.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

//@CrossOrigin("http://localhost:4200")
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

    //Query Method
    Page<Product> findByCategoryId(@RequestParam("id") Long id, Pageable pageable);

    // new query method
    Page<Product> findByNameContaining(@RequestParam("name") String name, Pageable pageable);
}
