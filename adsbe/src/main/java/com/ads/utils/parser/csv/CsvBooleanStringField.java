package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

/**
 * JMA - 27/10/2021 21:37
 * Convert string into integer for CSV parser - use Boolean.valueOf
 **/
public class CsvBooleanStringField extends AbstractBeanField<String, Boolean> {
    /**
     * Convert string into integer for CSV parser
     * @param string
     * @return boolean
     */
    @Override
    protected Boolean convert(String s) {
        return Boolean.valueOf(s);
    }
}
