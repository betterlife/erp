package io.betterlife.util.converter;

import java.text.ParseException;

/**
 * Author: Lawrence Liu
 * Date: 12/6/14
 */
public interface Converter<F, T> {
    public T convert(F source) throws ParseException;
}
