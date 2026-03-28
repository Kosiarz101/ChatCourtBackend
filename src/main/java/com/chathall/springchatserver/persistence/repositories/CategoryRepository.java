package com.chathall.springchatserver.persistence.repositories;

import com.chathall.springchatserver.app.models.Category;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryRepository {
    Category create(Category category);

    List<Category> findAllSortByName(Sort sort);

    boolean existsByName(String name);
}
