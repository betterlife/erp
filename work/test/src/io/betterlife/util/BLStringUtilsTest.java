package io.betterlife.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        return data;
    }

    @Test
    public void testMatchAny() {
        final boolean actual = BLStringUtils.getInstance().startWithAnyPattern(patterns, toMatch);
        assertEquals(String.format("Failed on match [%s] with [%s]", toMatch, BLStringUtils.join(patterns)), expected, actual);
    }
}
