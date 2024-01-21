package com.chathall.springchatserver.services.mongodb;

import com.chathall.springchatserver.models.Category;
import com.chathall.springchatserver.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category add(Category category) {
        if (categoryRepository.existsById(category.getId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given id already exists");
        }
        if (categoryRepository.existsByName(category.getName())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given name already exists");
        }
        category.setNewId();
        category.setCreationDate(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    public List<Category> findAllSortByName() {
        return categoryRepository.findAll(Sort.by("name"));
    }
}
