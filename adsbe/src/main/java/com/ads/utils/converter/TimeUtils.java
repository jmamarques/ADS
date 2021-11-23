package com.ads.utils.converter;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * JMA - 21/11/2021 09:39
 * Helps on creation/manipulation in Time objects
 **/
public class TimeUtils {

    /**
     * Convert date to instant
     * @param dateToConvert
     * @return
     */
    public static LocalDate convertToLocalDateViaInstant(@NonNull Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * convert to local date and time
     * @param localDate
     * @param localTime
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(@NonNull LocalDate localDate, @NonNull LocalTime localTime) {
        return LocalDateTime.of(localDate, localTime);
    }

    /**
     * convert to local date
     * @param date
     * @param localTime
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(@NonNull Date date, @NonNull LocalTime localTime) {
        return LocalDateTime.of(TimeUtils.convertToLocalDateViaInstant(date), localTime);
    }
}
