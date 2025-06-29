package com.prm.productsale.repository;

import com.prm.productsale.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity,Integer> {
  Optional<CategoryEntity> findByCategoryName(String categoryName);
  @EntityGraph(attributePaths = "products")
  Optional<CategoryEntity> findById(int categoryID);
}
