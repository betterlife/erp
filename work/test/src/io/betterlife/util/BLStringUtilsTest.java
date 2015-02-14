package io.betterlife.util;

import io.betterlife.framework.util.BLStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/14/14
 */
@RunWith(Parameterized.class)
public class BLStringUtilsTest {

    private boolean expected;
    private String[] patterns;
    private String toMatch;

    public BLStringUtilsTest(String[] patterns, String toMatch, boolean expected) {
        this.patterns = patterns;
        this.toMatch = toMatch;
        this.expected = expected;
        BLStringUtils.init();
    }

    @Parameterized.Parameters
    public static Collection testData() {
        List<Object> data = new ArrayList<>();
        data.add(new Object[]{new String[]{"abc", "", "def", "///", "\\*\\"}, "abc", true});
        data.add(new Object[]{new String[]{"abc", "", "def", "///", "\\*\\"}, "\\*\\", true});
        data.add(new Object[]{new String[]{"abc", "", "def", "///", "\\*\\"}, "///", true});
        data.add(new Object[]{new String[]{"abc", "", "def", "///", "\\*\\"}, "abcd", true});
        data.add(new Object[]{new String[]{"abc", "def", "///", "\\*\\"}, "g", false});
        data.add(new Object[]{new String[]{"abc", "def", "///", "\\*\\"}, "", true});
        data.add(new Object[]{new String[] {"/user/*"}, "/user/list", true});
        data.add(new Object[]{new String[] {"/user/*"}, "/user", true});
        return data;
    }

    @Test
    public void testMatchAny() {
        final boolean actual = BLStringUtils.getInstance().startWithAnyPattern(patterns, toMatch);
        assertEquals(String.format("Failed on match [%s] with [%s]", toMatch, BLStringUtils.join(patterns)), expected, actual);
    }

    @Test
    public void testCryptWithMD5() throws Exception {
        assertEquals("13C257762EC5236764A0FF761894DEED", BLStringUtils.getInstance().cryptWithMD5("Test123_"));
        assertEquals("DC647EB65E6711E155375218212B3964", BLStringUtils.getInstance().cryptWithMD5("Password"));
    }

    @Test(expected = NullPointerException.class)
    public void testCryptWithMD5NullPointerException() {
        BLStringUtils.getInstance().cryptWithMD5(null);
    }

}
