package com.spring.sutils.vaild;

import com.spring.sutils.vaild.exception.ValidationException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午6:06 2018/5/22 2018
 * @Modify:
 */
public class Check {

    private static final Logger logger = LoggerFactory.getLogger(Check.class);


    /**
     * 校验是否为空
     *
     * @param obj
     * @return 如果返回null表示：校验通过
     */
    public static void checkEmpty(Object obj) {
        parseAnnotation(obj);
    }

    private static void parseAnnotation(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field[] bothField = fields;
        Class<?> superClazz = clazz.getSuperclass();
        Field[] superFields = superClazz.getDeclaredFields();
        bothField = (Field[]) ArrayUtils.addAll(fields, superFields);
        for (Field field : bothField) {
            Annotation annotation = field.getAnnotation(NotNull.class);
            if (annotation == null) {
                continue;
            }
            field.setAccessible(true);
            try {
                if (annotation instanceof NotNull) {
                    Object oValue = field.get(obj);
                    if (oValue instanceof String) {
                        if (StringUtils.isEmpty((String) oValue)) {
                            throw new ValidationException(((NotNull) annotation).message() + "必填");
                        }
                    }
                    if (oValue == null) {
                        throw new ValidationException(((NotNull) annotation).message() + "必填");
                    } else {
                        parseAnnotation(oValue);
                    }
                }
            } catch (IllegalArgumentException e) {
                logger.error("解析注解出错：", e);
            } catch (IllegalAccessException e) {
                logger.error("解析注解出错：", e);
            }

        }
    }
}
