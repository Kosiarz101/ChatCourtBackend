package com.chathall.springchatserver.api.mappers;

import com.chathall.springchatserver.api.models.request.CategoryRequestDTO;
import com.chathall.springchatserver.api.models.response.CategoryResponseDTO;
import com.chathall.springchatserver.app.models.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryAppMapper {
    CategoryResponseDTO toDTO(Category category);
    Category toApp(CategoryRequestDTO category);
}
