package io.betterlife.util.converter;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DoubleBigDecimalConverterTest {

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(DoubleBigDecimalConverter.getInstance());
        assertEquals(DoubleBigDecimalConverter.class, DoubleBigDecimalConverter.getInstance().getClass());
    }

    @Test
    public void testConvert() throws Exception {
        Double d = 12.00003D;
        assertEquals(new BigDecimal(12.0000).setScale(4, BigDecimal.ROUND_HALF_UP),
                     DoubleBigDecimalConverter.getInstance().convert(d));
    }
}