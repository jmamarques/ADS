package com.ads.utils.parser.csv;

import com.ads.utils.enums.DayOfWeek;
import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.StringUtils;

/**
 * JMA - 16/11/2021 22:59
 **/
public class CsvDayOfWeekField extends AbstractBeanField<String, DayOfWeek> {
    @Override
    protected DayOfWeek convert(String s) {
        return DayOfWeek.valueOf(StringUtils.upperCase(s));
    }
}
