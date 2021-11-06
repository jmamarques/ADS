package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.StringUtils;

/**
 * JMA - 27/10/2021 21:37
 * Convert string into boolean for CSV parser - Check if the field is filled with something
 **/
public class CsvBooleanField extends AbstractBeanField<String, Boolean> {
    @Override
    protected Boolean convert(String s) {
        return StringUtils.isNoneBlank(s);
    }
}
