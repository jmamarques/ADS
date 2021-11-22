package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalTime;

/**
 * JMA - 16/11/2021 23:01
 **/
public class CsvLocalTimeField extends AbstractBeanField<String, LocalTime> {
    @Override
    protected LocalTime convert(String s) {
        try {
            return LocalTime.parse(s == null ? "" : s);
        } catch (Exception e) {
            return null;
        }
    }
}
