package com.chathall.springchatserver.persistence.mongodb.mappers;

import com.chathall.springchatserver.app.models.Category;
import com.chathall.springchatserver.persistence.mongodb.models.CategoryMongo;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryDataMapper {
    Category toApp(CategoryMongo categoryMongo);
    CategoryMongo toEntity(Category category);
}
