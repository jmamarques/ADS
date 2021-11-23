package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * JMA - 16/11/2021 23:00
 **/
public class CsvDateField extends AbstractBeanField<String, Date> {

    /**
     * parses a string representing a date
     * @param string
     * @return string date
     */
    @Override
    protected Date convert(String s) {
        try {
            return DateUtils.parseDate(s, "dd/MM/yyyy", "dd-MM-yyyy");
        } catch (Exception e) {
            return null;
        }
    }
}
