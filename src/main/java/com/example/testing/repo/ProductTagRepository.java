package com.example.testing.repo;

import com.example.testing.models.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Integer> {
}
