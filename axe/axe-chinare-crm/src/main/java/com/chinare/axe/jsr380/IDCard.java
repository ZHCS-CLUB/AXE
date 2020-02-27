package com.chinare.axe.jsr380;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import org.springframework.core.annotation.AliasFor;

import com.chinare.axe.jsr380.validator.IDCardValidator;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IDCardValidator.class})
public @interface IDCard {
    boolean required() default true;

    @AliasFor(annotation = Pattern.class)
    String message() default "身份证号码格式错误";

    @AliasFor(annotation = Pattern.class)
    Class<?>[] groups() default {};

    @AliasFor(annotation = Pattern.class)
    Class<? extends Payload>[] payload() default {};
}
