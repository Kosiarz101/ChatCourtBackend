package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.Category;
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

    public Category add(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given name already exists");
        }
        category.setNewId();
        LocalDateTime now = LocalDateTime.now();
        category.setCreationDate(now);
        category.setLastModifiedDate(now);
        return categoryRepository.save(category);
    }

    public List<Category> findAllSortByName() {
        return categoryRepository.findAll(Sort.by("name"));
    }
}
