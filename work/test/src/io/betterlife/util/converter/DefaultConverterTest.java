package io.betterlife.util.converter;

import io.betterlife.framework.converter.Converter;
import io.betterlife.framework.converter.DefaultConverter;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DefaultConverterTest {

    @Test
    public void testConvert() throws Exception {
        Converter converter = new DefaultConverter();
        assertNull(converter.convert(null));
        assertEquals("abc", converter.convert("abc"));
        assertEquals(new Date(), converter.convert(new Date()));
    }
}