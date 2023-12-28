package com.chathall.springchatserver.dtos.chatcourtfrontend.mappers;

import com.chathall.springchatserver.dtos.chatcourtfrontend.CategoryDTO;
import com.chathall.springchatserver.models.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryDTOMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO category);
}
