package io.betterlife.util.converter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Lawrence Liu
 * Date: 12/6/14
 */
public class DoubleBigDecimalConverter<F, T> implements Converter<F, T> {

    private static Converter<Double, BigDecimal> instance = new DoubleBigDecimalConverter<>();

    public static Converter<Double, BigDecimal> getInstance(){
        return instance;
    }

    @Override
    public T convert(F source) {
        Double d = (Double) source;
        return (T) new BigDecimal(d.doubleValue()).setScale(4, BigDecimal.ROUND_HALF_UP);
    }
}
