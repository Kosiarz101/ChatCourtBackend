package com.chathall.springchatserver.persistence.repositories;

import com.chathall.springchatserver.app.models.Category;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository {
    Category create(Category category);

    void delete(UUID id);

    List<Category> findAllSortByName(Sort sort);

    boolean existsById(UUID id);

    boolean existsByName(String name);
}
