package io.betterlife.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/14/14
 */
public class BLStringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String COMMA = ",";

    private static BLStringUtils ourInstance = new BLStringUtils();

    public static BLStringUtils getInstance() {
        return ourInstance;
    }

    private BLStringUtils() {
    }

    public boolean startWithAnyPattern(String[] patterns, String toMatch) {
        boolean match = false;
        if (BLStringUtils.isEmpty(toMatch)) {
            match = true;
        } else {
            for (String pattern : patterns) {
                pattern = pattern.replace("/*", StringUtils.EMPTY);
                if (BLStringUtils.startsWith(toMatch, pattern)) {
                    match = true;
                    break;
                }
            }
        }
        return match;
    }

}
