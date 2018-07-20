package com.spring.sutils.vaild;

import java.lang.annotation.*;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午5:59 2018/5/22 2018
 * @Modify:
 */
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {

    String message() default "有字段";



}
