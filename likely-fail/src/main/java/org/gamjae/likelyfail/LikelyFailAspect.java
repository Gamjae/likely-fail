package org.gamjae.likelyfail;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class LikelyFailAspect {
    @Around("@annotation(likelyFail)")
    public Object simulateFailure(ProceedingJoinPoint joinPoint, LikelyFail likelyFail) throws Throwable {
        if (profileMatches(likelyFail) && willFail(likelyFail.failureRate())) {
            throwRandomException(likelyFail);
        }

        return joinPoint.proceed();
    }

    private void throwRandomException(LikelyFail likelyFail) throws Throwable {
        Class<? extends Throwable>[] exceptions = likelyFail.exceptions();
        int randomIndex = (int) (Math.random() * exceptions.length);

        throw likelyFail.exceptions()[randomIndex].getConstructor().newInstance();
    }

    private boolean profileMatches(LikelyFail likelyFail) {
        if (likelyFail.profiles().length == 0) {
            return true;
        }

        List<String> activeProfiles = List.of(System.getProperty("spring.profiles.active").split(", "));

        for (String profile : likelyFail.profiles()) {
            if (matchesProfile(profile, activeProfiles)) {
                return true;
            }
        }

        return false;
    }

    private boolean willFail(double failureRate) {
        return Math.random() < failureRate;
    }

    private boolean matchesProfile(String profile, List<String> activeProfiles) {
        profile = profile.trim();

        if (profile.contains("&")) {
            int index = profile.indexOf("&");
            String firstOperand = profile.substring(0, index);
            String secondOperand = profile.substring(index + 1);

            return matchesProfile(firstOperand, activeProfiles) && matchesProfile(secondOperand, activeProfiles);
        }

        if (profile.contains("|")) {
            int index = profile.indexOf("|");
            String firstOperand = profile.substring(0, index);
            String secondOperand = profile.substring(index + 1);

            return matchesProfile(firstOperand, activeProfiles) || matchesProfile(secondOperand, activeProfiles);
        }

        if (profile.startsWith("!")) {
            return !matchesProfile(profile.substring(1), activeProfiles);
        }

        return activeProfiles.contains(profile);
    }
}
