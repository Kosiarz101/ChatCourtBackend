package com.chathall.springchatserver.mappers.data;

import com.chathall.springchatserver.models.app.Category;
import com.chathall.springchatserver.models.data.mongodb.CategoryMongo;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryDataMapper {
    Category toApp(CategoryMongo categoryMongo);
    CategoryMongo toEntity(Category category);
}
