package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

/**
 * JMA - 27/10/2021 21:37
 * Convert string into integer for CSV parser - use Boolean.valueOf
 **/
public class CsvBooleanStringField extends AbstractBeanField<String, Boolean> {

    public static boolean parseToBoolean(String s) {
        return Boolean.parseBoolean(s);
    }

    /**
     * Convert string into integer for CSV parser
     *
     * @param s
     * @return boolean
     */
    @Override
    protected Boolean convert(String s) {
        return parseToBoolean(s);
    }

}
