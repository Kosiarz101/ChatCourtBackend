package com.chathall.springchatserver.api.validators;

import com.chathall.springchatserver.app.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidatorImpl implements CategoryValidator {

    @Override
    public void onCreate(Category category) {
        if (category.getName() == null) {
            throw new ResourceValidationException(Category.class.getSimpleName(), "Name is necessary");
        }
    }
}
