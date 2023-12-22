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

    @Pointcut("execution(* com.template.repo.UserRepository.*(..))")
    void allUserRepositoryMethods() {

    }
    @After("allUserRepositoryMethods()")
    void logValues(JoinPoint joinPoint) {
        if (joinPoint.getArgs().length == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Values:\n");
        Arrays.stream(joinPoint.getArgs()).forEach(param -> stringBuilder.append(param).append("\n"));
        log.info(stringBuilder);
    }
}
