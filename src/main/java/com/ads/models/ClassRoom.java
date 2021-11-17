package com.ads.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * JMA - 07/11/2021 18:38
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoom {

    private String building;
    private String roomName;
    private int normalCapacity;
    private int examCapacity;
    private int featuresNumber;
    private List<String> features;
}
