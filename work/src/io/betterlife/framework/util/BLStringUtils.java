package io.betterlife.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/14/14
 */
public class BLStringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Logger logger = LogManager.getLogger(BLStringUtils.class.getName());

    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String COMMA = ",";

    private static BLStringUtils instance = new BLStringUtils();

    public static void init(){
        instance = new BLStringUtils();
    }

    public static BLStringUtils getInstance() {
        return instance;
    }

    public static void setInstance(BLStringUtils utils) {
        instance = utils;
    }

    private BLStringUtils() {
    }

    public String cryptWithMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = str.getBytes(BLStringUtils.ENCODING_UTF8);
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (byte aDigested : digested) {
                sb.append(Integer.toHexString(0xff & aDigested));
            }
            return BLStringUtils.upperCase(sb.toString());
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Failed to get MD5 crypt algorithm", ex);
        } catch (UnsupportedEncodingException e) {
            logger.error("Failed to get UTF-8 Byte array from string " + str);
        }
        return null;
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
