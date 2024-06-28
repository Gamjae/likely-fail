package org.gamjae.makitfail.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.gamjae.makitfail.annotations.FailParam;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import static org.gamjae.makitfail.utils.ExceptionThrowUtil.throwRandomException;
import static org.gamjae.makitfail.utils.ProfileCheckUtil.checkProfile;

@Aspect
@Component
public class FailParamAspect {

    @Around("@annotation(failParam)")
    public Object checkParam(ProceedingJoinPoint joinPoint, FailParam failParam) throws Throwable {
        if (checkProfile(failParam.profiles()) && checkCondition(failParam)) {
            throwRandomException(failParam.exceptions());
        }

        return joinPoint.proceed();
    }

    private boolean checkCondition(FailParam failParam) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(failParam.condition());

        return Boolean.TRUE.equals(expression.getValue(Boolean.class));
    }
}
