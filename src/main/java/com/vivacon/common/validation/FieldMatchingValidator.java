package com.vivacon.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FieldMatchingValidator implements ConstraintValidator<FieldMatching, Object> {

    private String firstField;

    private String secondField;

    @Override
    public void initialize(FieldMatching fieldMatching) {
        this.firstField = fieldMatching.firstField();
        this.secondField = fieldMatching.secondField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Method firstFieldGetter = object.getClass().getMethod("get" + firstField);
            Method secondFieldGetter = object.getClass().getMethod("get" + secondField);
            Object firstFieldValue = firstFieldGetter.invoke(object);
            Object secondFieldValue = secondFieldGetter.invoke(object);
            return (firstFieldValue == null && secondFieldValue == null) || (firstFieldValue != null && firstFieldValue.equals(secondFieldValue));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
