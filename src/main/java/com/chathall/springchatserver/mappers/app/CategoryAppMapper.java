package com.chathall.springchatserver.mappers.app;

import com.chathall.springchatserver.models.api.request.CategoryRequestDTO;
import com.chathall.springchatserver.models.api.response.CategoryResponseDTO;
import com.chathall.springchatserver.models.app.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryAppMapper {
    CategoryResponseDTO toDTO(Category category);
    Category toApp(CategoryRequestDTO category);
}
