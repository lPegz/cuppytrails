package de.hybris.platform.cuppytrail.constraints;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by luizhp on 2/17/17.
 */
public class NotEmptyCuppyValidator implements ConstraintValidator<NotEmptyCuppy, String> {

    @Override
    public void initialize(NotEmptyCuppy notEmptyCuppy) {
        //empty
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && !StringUtils.isEmpty(s.trim()) && s.matches(".*[a-zA-Z].*");
    }
}
