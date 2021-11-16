package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;

/**
 * JMA - 16/11/2021 23:00
 **/
public class CsvLocalDateField extends AbstractBeanField<String, LocalDate> {
    @Override
    protected LocalDate convert(String s) {
        return LocalDate.parse(s == null ? "" : s);
    }
}
