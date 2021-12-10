package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalTime;

/**
 * JMA - 16/11/2021 23:01
 **/
public class CsvLocalTimeField extends AbstractBeanField<String, LocalTime> {
    public static LocalTime parseTo(String s) {
        try {
            return LocalTime.parse(s == null ? "" : s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get an instance from localtime from a string such as ’10:15:45′ passed as parameter.
     *
     * @param s - value
     * @return localtime
     */
    @Override
    protected LocalTime convert(String s) {
        return parseTo(s);
    }
}
