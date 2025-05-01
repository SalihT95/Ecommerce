package org.turkoglu.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkoglu.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
