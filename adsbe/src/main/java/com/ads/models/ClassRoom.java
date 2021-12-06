package com.ads.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * generic class withe the caracterization of room
 * strings: building, roomName, error
 * list string: features
 * int: normal capacity, exam capacity, features number
 * boolean: if has an error
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
    private boolean hasError;
    private String error;

    /**
     * returns if is an error
     * @return boolean
     */
    public boolean isNotError() {
        return !hasError;
    }
}
