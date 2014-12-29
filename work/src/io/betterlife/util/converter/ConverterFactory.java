package io.betterlife.util.converter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu
 * Date: 12/6/14
 */
public class ConverterFactory {
    private static ConverterFactory instance = new ConverterFactory();
    private Map<String, Converter> converters;

    private ConverterFactory() {
        converters = new HashMap<>();
        converters.put(getKey(String.class, Date.class), StringDateConverter.getInstance());
        converters.put(getKey(Double.class, BigDecimal.class), DoubleBigDecimalConverter.getInstance());
        converters.put(getKey(Integer.class, BigDecimal.class), IntegerBigDecimalConverter.getInstance());
        converters.put(getKey(Long.class, Date.class), LongDateConverter.getInstance());
    }

    private String getKey(final Class<?> fromClazz, final Class<?> toClazz) {
        return fromClazz.getName() + "_" + toClazz.getName();
    }

    public static ConverterFactory getInstance() {
        return instance;
    }

    public static void setInstance(ConverterFactory instance) {
        ConverterFactory.instance = instance;
    }

    public Converter getConverter(Class<?> fromClazz, Class<?> toClazz) {
        return converters.get(getKey(fromClazz, toClazz));
    }
}
