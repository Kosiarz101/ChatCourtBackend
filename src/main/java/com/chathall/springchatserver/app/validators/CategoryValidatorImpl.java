package com.chathall.springchatserver.app.validators;

import com.chathall.springchatserver.app.models.Category;
import com.chathall.springchatserver.exceptions.ResourceValidationException;
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
