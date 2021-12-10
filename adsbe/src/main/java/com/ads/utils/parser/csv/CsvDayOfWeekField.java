package com.ads.utils.parser.csv;

import com.ads.utils.enums.DayOfWeek;
import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.StringUtils;

/**
 * JMA - 16/11/2021 22:59
 **/
public class CsvDayOfWeekField extends AbstractBeanField<String, DayOfWeek> {
    public static DayOfWeek parseTo(String s) {
        if (StringUtils.equalsIgnoreCase("Sáb", s)) {
            return DayOfWeek.SAB;
        }
        try {
            return DayOfWeek.valueOf(StringUtils.upperCase(s));
        } catch (Exception e) {
            return DayOfWeek.NONE;
        }
    }

    /**
     * convert to day of the week
     *
     * @param s value
     * @return Say of week
     */
    @Override
    protected DayOfWeek convert(String s) {
        return parseTo(s);
    }
}
