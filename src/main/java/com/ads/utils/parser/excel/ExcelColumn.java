package com.ads.utils.parser.excel;

import com.ads.utils.enums.GenericType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * JMA - 25/10/2021 21:39
 * Configuration to parse Excel on java object
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    String name() default "";

    int index();

    GenericType type() default GenericType.GENERIC_TYPE;

    String numberFormat() default "General";
}
