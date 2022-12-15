package com.example.myproj.util;

import static com.example.myproj.util.ProfileConstants.DEV;
import static com.example.myproj.util.ProfileConstants.PROD;
import static com.example.myproj.util.ProfileConstants.TEST;

import lombok.experimental.UtilityClass;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * EnvironmentUtils.
 *
 * @author Evgeny_Ageev
 */
@UtilityClass
public final class EnvironmentUtils {

    /**
     * Check is service in a <strong>PROD</strong> mode.
     *
     * @param environment {@link ConfigurableEnvironment}
     *
     * @return <code>true</code> is service in a <strong>PROD</strong> mode
     */
    public static boolean prodMode(Environment environment) {
        return Arrays.asList(environment.getActiveProfiles()).contains(PROD);
    }

    /**
     * Check is service in a <strong>DEV</strong> mode.
     *
     * @param environment {@link ConfigurableEnvironment}
     *
     * @return <code>true</code> is service in a <strong>DEV</strong> mode
     */
    public static boolean devMode(Environment environment) {
        return Arrays.asList(environment.getActiveProfiles()).contains(DEV);
    }

    /**
     * Check is service in a <strong>TEST</strong> mode.
     *
     * @param environment {@link ConfigurableEnvironment}
     *
     * @return <code>true</code> is service in a <strong>TEST</strong> mode
     */
    public static boolean testMode(Environment environment) {
        return Arrays.asList(environment.getActiveProfiles()).contains(TEST);
    }
}
