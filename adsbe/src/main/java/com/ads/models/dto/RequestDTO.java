package com.ads.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * JMA - 08/12/2021 00:18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDTO implements Serializable {
    String mappingClass;
    String mappingTimetable;
    String qualities;
    Boolean fast;
    int maxGenerations = 250;
    int populationSize = 50;
}
