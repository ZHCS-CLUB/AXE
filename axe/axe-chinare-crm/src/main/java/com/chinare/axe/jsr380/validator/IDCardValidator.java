package com.chinare.axe.jsr380.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.nutz.lang.Strings;

import com.chinare.axe.jsr380.IDCard;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class IDCardValidator implements ConstraintValidator<IDCard, String> {

    private boolean required = false;

    @Override
    public void initialize(IDCard anno) {
        required = anno.required();
    }

    @Override
    public boolean isValid(String idcard, ConstraintValidatorContext context) {
        if (required) {
            return Strings.isNotBlank(idcard) && ValidatorUtil.isIDCard(idcard);
        } else {
            // 允许为空
            if (Strings.isBlank(idcard)) {
                return true;
            } else {
                return ValidatorUtil.isIDCard(idcard);
            }
        }
    }

}
