package com.chathall.springchatserver.dtos.frontend.mappers;

import com.chathall.springchatserver.dtos.frontend.request.CategoryRequestDTO;
import com.chathall.springchatserver.dtos.frontend.response.CategoryResponseDTO;
import com.chathall.springchatserver.models.mongodb.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryDTOMapper {
    CategoryResponseDTO toDTO(Category category);
    Category toEntity(CategoryRequestDTO category);
}
