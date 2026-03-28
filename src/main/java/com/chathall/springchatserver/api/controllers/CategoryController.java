package com.chathall.springchatserver.api.controllers;

import com.chathall.springchatserver.api.mappers.CategoryAppMapper;
import com.chathall.springchatserver.api.models.request.CategoryRequestDTO;
import com.chathall.springchatserver.api.models.response.CategoryResponseDTO;
import com.chathall.springchatserver.app.models.Category;
import com.chathall.springchatserver.app.services.CategoryService;
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

    private final CategoryAppMapper categoryAppMapper;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> add(@RequestBody CategoryRequestDTO categoryDTO) {
        Category category = categoryAppMapper.toApp(categoryDTO);
        category = categoryService.create(category);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(categoryAppMapper.toDTO(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findAll() {
        List<Category> categories = categoryService.findAllSortByName();
        return ResponseEntity.ok(categories.stream().map(categoryAppMapper::toDTO).collect(Collectors.toList()));
    }
}
