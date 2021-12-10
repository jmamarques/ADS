package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;

/**
 * JMA - 27/10/2021 21:37
 * Convert string into integer for CSV parser
 **/
public class CsvIntegerField extends AbstractBeanField<String, Integer> {
    public static int parseToInt(String s) {
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Convert string into integer for CSV parser
     *
     * @param s
     * @return integer
     */
    @Override
    protected Integer convert(String s) {
        return parseToInt(s);
    }
}
