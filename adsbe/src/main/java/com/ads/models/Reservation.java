package com.ads.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * class that define a reservation of a room with the specified time interval
 * variables LocalDateTime: begin and end
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    private LocalDateTime begin;
    private LocalDateTime end;
}
