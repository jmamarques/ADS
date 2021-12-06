package com.ads.utils.mapper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * JMA - 07/11/2021 19:05
 * interface feature that associates with the name
 **/
@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {
    String name();
}
