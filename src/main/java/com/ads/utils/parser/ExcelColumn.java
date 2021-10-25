package com.ads.utils.parser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * JMA - 25/10/2021 21:39
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String name();
    int index();
    String numberFormat() default "General";
}
