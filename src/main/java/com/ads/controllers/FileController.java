package com.ads.controllers;

import com.ads.dto.ClassDTO;
import com.ads.services.TimetablesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * JMA - 25/10/2021 21:17
 **/
@RestController
@Api(tags = {"Timetable"})
@RequestMapping("/ads")
@Log4j2
public class FileController {

    private final TimetablesService timetablesService;

    public FileController(TimetablesService timetablesService) {
        this.timetablesService = timetablesService;
    }

    @GetMapping("/file/timetable")
    @ApiOperation(value = "Upload timetables to process (File)",
            produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void processTimetable(
            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart(value = "file") MultipartFile file) {

    }

    @GetMapping("/json/timetable")
    @ApiOperation(value = "Upload timetables to process (Json)",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void processJsonTimetable(@RequestBody List<ClassDTO> rooms) {
        log.info("Process TimeTable JSON version");
        timetablesService.processJsonTimetable(rooms);
    }

}
