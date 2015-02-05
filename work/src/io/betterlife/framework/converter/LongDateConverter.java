package io.betterlife.framework.converter;

import java.text.ParseException;
import java.util.Date;

/**
 * Author: Lawrence Liu
 * Date: 12/29/14
 */
public class LongDateConverter<F, T> implements  Converter<F, T> {
    private static Converter<Long, Date> instance = new LongDateConverter<>();

    public static Converter<Long, Date> getInstance() {
        return instance;
    }

    public static void setInstance(Converter<Long, Date> instance) {
        LongDateConverter.instance = instance;
    }

    @Override
    public T convert(F source) throws ParseException {
        Long l = (Long) source;
        Date d = new Date();
        d.setTime(l);
        return (T) d;
    }
}
