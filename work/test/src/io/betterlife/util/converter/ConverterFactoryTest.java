package io.betterlife.util.converter;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConverterFactoryTest {

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(ConverterFactory.getInstance());
    }

    @Test
    public void testSetInstance() throws Exception {
        ConverterFactory factory = mock(ConverterFactory.class);
        ConverterFactory.setInstance(factory);
        assertSame(factory, ConverterFactory.getInstance());
    }

    @Test
    public void testGetConverter() throws Exception {
        assertNotNull(ConverterFactory.getInstance().getConverter(String.class, Date.class));
        assertNotNull(ConverterFactory.getInstance().getConverter(Double.class, BigDecimal.class));
        assertNotNull(ConverterFactory.getInstance().getConverter(Integer.class, BigDecimal.class));
        assertNotNull(ConverterFactory.getInstance().getConverter(Long.class, Date.class));
    }
}