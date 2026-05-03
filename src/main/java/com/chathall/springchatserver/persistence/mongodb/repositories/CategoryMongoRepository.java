package com.chathall.springchatserver.persistence.mongodb.repositories;

import com.chathall.springchatserver.app.models.Category;
import com.chathall.springchatserver.persistence.mongodb.mappers.CategoryDataMapper;
import com.chathall.springchatserver.persistence.mongodb.models.CategoryMongo;
import com.chathall.springchatserver.persistence.mongodb.repositories.spring.CategorySpringMongoRepository;
import com.chathall.springchatserver.persistence.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public boolean existsById(UUID id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }
}
