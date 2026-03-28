package com.chathall.springchatserver.repositories.mongodb;

import com.chathall.springchatserver.mappers.data.CategoryDataMapper;
import com.chathall.springchatserver.models.app.Category;
import com.chathall.springchatserver.models.data.mongodb.CategoryMongo;
import com.chathall.springchatserver.repositories.CategoryRepository;
import com.chathall.springchatserver.repositories.mongodb.spring.CategorySpringMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryMongoRepository implements CategoryRepository {

    private final CategorySpringMongoRepository categoryRepository;
    private final CategoryDataMapper categoryDataMapper;

    @Override
    public Category create(Category category) {
        CategoryMongo categoryMongo = categoryDataMapper.toEntity(category);
        categoryMongo = categoryRepository.save(categoryMongo);
        return categoryDataMapper.toApp(categoryMongo);
    }

    @Override
    public List<Category> findAllSortByName(Sort sort) {
        return categoryRepository.findAll(Sort.by("name")).stream()
                .map(categoryDataMapper::toApp)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
