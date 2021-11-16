package com.ads.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * JMA - 16/11/2021 22:59
 **/
public class CsvListCommaField extends AbstractBeanField<String, List<String>> {
    @Override
    protected List<String> convert(String s) {
        return Arrays.asList(StringUtils.split(s, ","));
    }
}
