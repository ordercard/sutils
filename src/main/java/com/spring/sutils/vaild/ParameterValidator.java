package com.spring.sutils.vaild;


import com.hbc.common.exception.CommonReturnCode;
import com.laiwang.common.bdata.rsp.ReturnResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ParameterValidator {
    private Logger logger = LoggerFactory.getLogger(ParameterValidator.class);

    @Pointcut("execution(* com.api.controller..*.*(..))")
    private void anyController() {
    }

    @Around(value = "anyController()")
    public Object basicParameterCheck(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {//通过它对错误的一个反馈
                ReturnResult rs = new ReturnResult(CommonReturnCode.PARAM_ERROR.getCode(),
                        CommonReturnCode.PARAM_ERROR.getMessage());
                BindingResult result = BindingResult.class.cast(arg);
                if (result.hasErrors()) {
                    Map<String, String> errMap = new HashMap<>(result.getErrorCount());
                    for (FieldError error : result.getFieldErrors()) {
                        errMap.put(error.getField(), error.getDefaultMessage());
                    }
                    rs.setData(errMap);

                    logger.error("### param error : " + rs.toString());

                    return rs;
                }
                break;
            }
        }

        return jp.proceed();
    }
}
