package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * JMA - 16/11/2021 22:59
 **/
public class CsvListCommaField extends AbstractBeanField<String, List<String>> {
    /**
     * @param s
     * @return list of strings split by the comma
     */
    @Override
    protected List<String> convert(String s) {
        return Arrays.stream(StringUtils.split(s, ",")).map(String::trim).toList();
    }
}
