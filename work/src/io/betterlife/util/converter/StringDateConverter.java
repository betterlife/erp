package io.betterlife.util.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Lawrence Liu
 * Date: 12/6/14
 */
public class StringDateConverter<F, T> implements Converter<F, T> {

    private static Converter<String, Date> instance = new StringDateConverter<>();

    public static Converter<String, Date> getInstance(){
        return instance;
    }
    @Override
    public T convert(F sourceStr) throws ParseException {
        String s = (String) sourceStr;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        try {
            return (T) dateFormat.parse(s);
        } catch (Exception e) {
            dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            return (T) dateFormat.parse(s);
        }
    }
}
