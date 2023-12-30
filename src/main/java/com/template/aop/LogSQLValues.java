package com.template.aop;

import lombok.extern.apachecommons.CommonsLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@ConditionalOnProperty(name = "spring.jpa.show-values", havingValue = "true")
@CommonsLog
public class LogSQLValues {

    @Pointcut("execution(* org.springframework.data.repository.Repository+.*(*, ..))")
    void allUserRepositoryMethods() {

    }

    @After("allUserRepositoryMethods()")
    void logValues(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Values:\n");
        Arrays.stream(joinPoint.getArgs()).forEach(param -> {
            String value = "";
            if (param.getClass().isArray()) {
                if (param instanceof Object[] objects) {
                    value = Arrays.toString(objects);
                }
            } else {
                value = param.toString();
            }
            stringBuilder.append(value).append("\n");
        });
        log.info(stringBuilder);
    }
}
