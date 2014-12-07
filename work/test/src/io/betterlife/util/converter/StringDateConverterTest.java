package io.betterlife.util.converter;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StringDateConverterTest {

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(StringDateConverter.getInstance());
    }

    @Test
    public void testConvert() throws Exception {
        Date date = StringDateConverter.getInstance().convert("2014-12-11T16:00:00.000Z");
        assertNotNull(date);
    }
}