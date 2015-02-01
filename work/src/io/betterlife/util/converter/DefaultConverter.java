package io.betterlife.util.converter;

import java.text.ParseException;

/**
 * Author: Lawrence Liu
 * Date: 2/1/15
 */
public class DefaultConverter<F, T> implements Converter<F, T> {
    @Override
    public T convert(F source) throws ParseException {
        return (T) source;
    }
}
