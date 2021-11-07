package com.ads.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * JMA - 07/11/2021 18:38
 **/
@Data
@Builder
public class Timetable {

    private String building;
    private String roomName;
    private int normalCapacity;
    private int examCapacity;
    private int featuresNumber;
    private List<String> features;
}
