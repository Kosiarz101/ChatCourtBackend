package com.chathall.springchatserver.services.entity.mainimpl;

import com.chathall.springchatserver.models.app.Category;
import com.chathall.springchatserver.repositories.CategoryRepository;
import com.chathall.springchatserver.services.entity.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Resource with given name already exists");
        }
        category.setNewId();
        LocalDateTime now = LocalDateTime.now();
        category.setCreationDate(now);
        category.setLastModifiedDate(now);

        return categoryRepository.create(category);
    }

    public List<Category> findAllSortByName() {
        return categoryRepository.findAllSortByName(Sort.by("name"));
    }
}
