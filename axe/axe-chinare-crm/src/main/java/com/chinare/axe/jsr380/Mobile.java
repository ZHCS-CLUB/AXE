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

/**
 * @author 王贵源(kerbores@gmail.com)
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
@Pattern(regexp = "/^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/", message = "手机号码格式错误")
public @interface Mobile {

    boolean required() default true;

    @AliasFor(annotation = Pattern.class)
    String regexp() default "/^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/";

    @AliasFor(annotation = Pattern.class)
    String message() default "手机号码格式错误";

    @AliasFor(annotation = Pattern.class)
    Class<?>[] groups() default {};

    @AliasFor(annotation = Pattern.class)
    Class<? extends Payload>[] payload() default {};

}
