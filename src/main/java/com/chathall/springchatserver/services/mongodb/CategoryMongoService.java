package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.mappers.data.CategoryDataMapper;
import com.chathall.springchatserver.models.app.Category;
import com.chathall.springchatserver.models.data.mongodb.CategoryMongo;
import com.chathall.springchatserver.repositories.CategoryRepository;
import com.chathall.springchatserver.services.db.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryMongoService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDataMapper categoryDataMapper;

    public Category add(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given name already exists");
        }
        category.setNewId();
        LocalDateTime now = LocalDateTime.now();
        category.setCreationDate(now);
        category.setLastModifiedDate(now);

        CategoryMongo categoryMongo = categoryDataMapper.toEntity(category);
        categoryMongo = categoryRepository.save(categoryMongo);
        return categoryDataMapper.toApp(categoryMongo);
    }

    public List<Category> findAllSortByName() {
        return categoryRepository.findAll(Sort.by("name")).stream()
                .map(categoryDataMapper::toApp)
                .toList();
    }
}
