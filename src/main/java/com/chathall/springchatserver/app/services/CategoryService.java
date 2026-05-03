package com.chathall.springchatserver.app.services;

import com.chathall.springchatserver.app.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Category create(Category category);

    void delete(UUID id);

    List<Category> findAllSortByName();
}
