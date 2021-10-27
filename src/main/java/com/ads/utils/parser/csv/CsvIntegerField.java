package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

/**
 * JMA - 27/10/2021 21:37
 **/
public class CsvIntegerField extends AbstractBeanField<String, Integer> {
    @Override
    protected Integer convert(String s) {
        return Integer.valueOf(s);
    }
}
