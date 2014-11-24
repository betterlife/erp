package io.betterlife.util;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/14/14
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static StringUtils ourInstance = new StringUtils();

    public static StringUtils getInstance() {
        return ourInstance;
    }

    private StringUtils() {
    }

    public boolean startWithAnyPattern(String[] patterns, String toMatch) {
        boolean match = false;
        if (StringUtils.isEmpty(toMatch)) {
            match = true;
        } else {
            for (String pattern : patterns) {
                pattern = pattern.replace("*","");
                if (StringUtils.startsWith(toMatch, pattern)) {
                    match = true;
                    break;
                }
            }
        }
        return match;
    }

}
