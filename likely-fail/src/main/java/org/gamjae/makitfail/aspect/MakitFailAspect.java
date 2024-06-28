package org.gamjae.makitfail.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.gamjae.makitfail.annotations.MakitFail;
import org.springframework.stereotype.Component;

import static org.gamjae.makitfail.utils.ProfileCheckUtil.checkProfile;

@Aspect
@Component
public class MakitFailAspect {
    @Around("@annotation(makitFail)")
    public Object simulateFailure(ProceedingJoinPoint joinPoint, MakitFail makitFail) throws Throwable {
        if (checkProfile(makitFail.profiles()) && willFail(makitFail.failureRate())) {
            throwRandomException(makitFail);
        }

        return joinPoint.proceed();
    }

    private void throwRandomException(MakitFail makitFail) throws Throwable {
        Class<? extends Throwable>[] exceptions = makitFail.exceptions();
        int randomIndex = (int) (Math.random() * exceptions.length);

        throw makitFail.exceptions()[randomIndex].getConstructor().newInstance();
    }

    private boolean willFail(double failureRate) {
        return Math.random() < failureRate;
    }
}
