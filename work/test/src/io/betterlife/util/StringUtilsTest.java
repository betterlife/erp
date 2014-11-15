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
public class StringUtilsTest {

    private boolean expected;
    private String[] patterns;
    private String toMatch;

    public StringUtilsTest(String[] patterns, String toMatch, boolean expected) {
        this.expected = expected;
        this.patterns = patterns;
        this.toMatch = toMatch;
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
        return data;
    }

    @Test
    public void testMatchAny() {
        final boolean actual = StringUtils.getInstance().startWithAnyPattern(patterns, toMatch);
        assertEquals(String.format("Failed on match [%s] with [%s]", toMatch, StringUtils.join(patterns)), expected, actual);
    }
}
