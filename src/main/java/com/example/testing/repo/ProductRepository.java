package com.example.testing.repo;

import com.example.testing.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p " +
            " JOIN p.items t " +
            " WHERE t.tag = :tag")
    List<Product> findByTag(@Param("tag") String tag);
}
