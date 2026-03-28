package com.chathall.springchatserver.app.services;

import com.chathall.springchatserver.app.models.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    List<Category> findAllSortByName();
}
