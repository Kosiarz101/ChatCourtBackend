package com.chathall.springchatserver.services.entity;

import com.chathall.springchatserver.models.app.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    List<Category> findAllSortByName();
}
