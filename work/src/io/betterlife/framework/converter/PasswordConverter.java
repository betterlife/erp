package io.betterlife.framework.converter;

import io.betterlife.framework.util.BLStringUtils;
import io.betterlife.framework.util.LoginUtil;

import java.text.ParseException;

/**
 * Author: Lawrence Liu
 * Date: 2/1/15
 */
public class PasswordConverter<F, T> implements Converter<F, T> {
    @Override
    @SuppressWarnings("unchecked")
    public T convert(F source) throws ParseException {
        return (T) BLStringUtils.upperCase(LoginUtil.getInstance().cryptWithMD5((String) source));
    }
}
