package com.moreira.dscommerce.repositories;

import com.moreira.dscommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategotyRepository extends JpaRepository<Category, Long> {
}
