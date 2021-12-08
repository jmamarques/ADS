package com.ads.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
    List<String> qualities;
    Boolean fast;
}
