package com.github.daqun.jira.core.validator;

import com.atlassian.jira.util.SimpleErrorCollection;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * @description 实体验证类
 * @date 2019/3/25 10:40
 * @author by chenq
 */
public class BeanValidator {

    private static javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 首次验证耗时较长，大概两三百毫秒。但是它是使用缓存的，将实体的反射信息缓存，后面调用验证很快，大概在几毫秒之内
     *
     * @param bean 验证的实体
     * @return jira的errorCollection
     */
    public static SimpleErrorCollection validate(Object bean) {
        SimpleErrorCollection simpleErrorCollection = new SimpleErrorCollection();
        Set<ConstraintViolation<Object>> validate = validator.validate(bean);
        if (validate.size() > 0) {
            validate.forEach(v -> {
                String errMsg = v.getMessage();
                String propertyName = v.getPropertyPath().toString();
                simpleErrorCollection.addError(propertyName, errMsg);
            });
        }

        return simpleErrorCollection;
    }
}
