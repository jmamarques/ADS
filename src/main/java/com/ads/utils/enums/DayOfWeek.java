package com.ads.utils.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * JMA - 16/11/2021 22:43
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public enum DayOfWeek {
    SEG, TER, QUA, QUI, SEX, SAB, DOM, NONE
}
