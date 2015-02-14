package io.betterlife.framework.converter;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Author: Lawrence Liu
 * Date: 12/10/14
 */
public class IntegerBigDecimalConverter<F, T> implements  Converter<F, T> {
    private static Converter<Integer, BigDecimal> instance = new IntegerBigDecimalConverter<>();

    public static Converter<Integer, BigDecimal> getInstance() {
        return instance;
    }

    public static void setInstance(Converter<Integer, BigDecimal> instance) {
        IntegerBigDecimalConverter.instance = instance;
    }

    @Override
    public T convert(F source) throws ParseException {
        Integer i = (Integer) source;
        return (T) new BigDecimal(i.intValue()).setScale(4, BigDecimal.ROUND_HALF_UP);
    }
}
