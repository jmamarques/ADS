package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

import java.time.LocalDate;

/**
 * JMA - 16/11/2021 23:00
 **/
public class CsvLocalDateField extends AbstractBeanField<String, LocalDate> {
    /**
     * obtains an instance of localdate from a text string such as 2007-12-03.
     * @param string
     * @return localdate
     */
    @Override
    protected LocalDate convert(String s) {
        try {
            return LocalDate.parse(s == null ? "" : s);
        } catch (Exception e) {
            return null;
        }
    }
}
