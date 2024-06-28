package org.gamjae.makitfail.utils;

import java.util.List;

public class ProfileCheckUtil {
    public static boolean checkProfile(String[] profiles) {
        if (profiles.length == 0) {
            return true;
        }

        List<String> activeProfiles = List.of(System.getProperty("spring.profiles.active").split(", "));

        for (String profile : profiles) {
            if (matchesProfile(profile, activeProfiles)) {
                return true;
            }
        }

        return false;
    }

    private static boolean matchesProfile(String profile, List<String> activeProfiles) {
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
