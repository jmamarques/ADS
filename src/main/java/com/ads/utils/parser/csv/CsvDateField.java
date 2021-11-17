package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

import java.util.Date;

/**
 * JMA - 16/11/2021 23:00
 **/
public class CsvDateField extends AbstractBeanField<String, Date> {
    @Override
    protected Date convert(String s) {
        try {
            return new Date(s == null ? "" : s);
        } catch (Exception e) {
            return null;
        }
    }
}
