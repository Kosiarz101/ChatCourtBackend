package com.chathall.springchatserver.services.db;

import com.chathall.springchatserver.models.mongodb.Category;

import java.util.List;

public interface CategoryService {

    Category add(Category category);

    List<Category> findAllSortByName();
}
