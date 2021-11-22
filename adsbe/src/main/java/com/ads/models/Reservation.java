package com.ads.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JMA - 21/11/2021 10:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    private LocalDateTime begin;
    private LocalDateTime end;
}
