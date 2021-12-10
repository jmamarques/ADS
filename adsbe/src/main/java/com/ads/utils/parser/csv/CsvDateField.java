package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * JMA - 16/11/2021 23:00
 **/
public class CsvDateField extends AbstractBeanField<String, Date> {

    public static Date parseTo(String s) {
        try {
            String date = s;
            if (StringUtils.contains(s, "T")) {
                date = StringUtils.split(s, "T")[0];
            }
            return DateUtils.parseDate(date, "dd/MM/yyyy", "dd-MM-yyyy", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-ddTHH:mm:ss.SSS", "yyyy/MM/ddTHH:mm:ss.SSS");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * parses a string representing a date
     *
     * @param s
     * @return string date
     */
    @Override
    protected Date convert(String s) {
        return parseTo(s);
    }
}
