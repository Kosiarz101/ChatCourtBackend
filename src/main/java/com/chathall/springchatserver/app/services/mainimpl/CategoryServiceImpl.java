package com.chathall.springchatserver.app.services.mainimpl;

import com.chathall.springchatserver.app.models.Category;
import com.chathall.springchatserver.app.services.CategoryService;
import com.chathall.springchatserver.app.validators.CategoryValidator;
import com.chathall.springchatserver.exceptions.ResourceAlreadyExistsException;
import com.chathall.springchatserver.exceptions.ResourceNotFoundException;
import com.chathall.springchatserver.persistence.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    @Override
    public Category create(Category category) {
        categoryValidator.onCreate(category);
        if (categoryRepository.existsByName(category.getName())) {
            throw new ResourceAlreadyExistsException("Category with given name already exists");
        }
        category.setNewId();
        LocalDateTime now = LocalDateTime.now();
        category.setCreationDate(now);
        category.setLastModifiedDate(now);

        return categoryRepository.create(category);
    }

    @Override
    public void delete(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id = " + id + " does not exist");
        }
//        if (categoryRepository.hasAnyChatroom()) {
//            // todo: delete categories from specific chatrooms
//        }

        categoryRepository.delete(id);
    }

    @Override
    public List<Category> findAllSortByName() {
        return categoryRepository.findAllSortByName(Sort.by("name"));
    }
}
