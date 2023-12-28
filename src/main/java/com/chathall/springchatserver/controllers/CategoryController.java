package com.chathall.springchatserver.controllers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.CategoryDTO;
import com.chathall.springchatserver.dtos.chatcourtfrontend.mappers.CategoryDTOMapper;
import com.chathall.springchatserver.models.Category;
import com.chathall.springchatserver.services.mongodb.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryDTOMapper categoryDTOMapper;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> add(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryDTOMapper.toEntity(categoryDTO);
        category = categoryService.add(category);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(categoryDTOMapper.toDTO(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<Category> categories = categoryService.findAllSortByName();
        return ResponseEntity.ok(categories.stream().map(categoryDTOMapper::toDTO).collect(Collectors.toList()));
    }
}
