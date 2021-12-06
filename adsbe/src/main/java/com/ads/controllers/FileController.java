package com.ads.controllers;

import com.ads.dto.ScheduleDTO;
import com.ads.services.FileService;
import com.ads.services.TimetablesService;
import com.ads.utils.constants.GeneralConst;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * JMA - 25/10/2021 21:17
 **/
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Api(tags = {"Timetable"})
@RequestMapping("/ads")
@Log4j2
public class FileController {

    private final TimetablesService timetablesService;
    private final FileService fileService;

    public FileController(TimetablesService timetablesService, FileService fileService) {
        this.timetablesService = timetablesService;
        this.fileService = fileService;
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
    public void processJsonTimetable(@RequestBody ScheduleDTO scheduleDTO) {
        log.info("Process Schedule JSON version");
        timetablesService.processJsonTimetable(scheduleDTO);
    }

    @PostMapping("/headers")
    @ApiOperation(value = "Upload csv file to get headers file",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> processFileToGetHeaders(
            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart(value = "file") MultipartFile file) {
        return fileService.getHeadersFromFile(file);
    }

    @GetMapping("/headers/classroom")
    @ApiOperation(value = "Get headers for Classroom", produces = MediaType.APPLICATION_JSON_VALUE)
    public String[] getClassroomHeaders() {
        return timetablesService.getClassRoomHeaders();
    }

    @GetMapping("/headers/timetable")
    @ApiOperation(value = "Get headers for Timetable", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK", responseContainer = MediaType.APPLICATION_JSON_VALUE, examples = @Example(value = @ExampleProperty(value = GeneralConst.TIMETABLE_FIELD_MAPPING_STR, mediaType = MediaType.APPLICATION_JSON_VALUE))),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found")})
    public String[] getTimetableHeaders() {
        return timetablesService.getTimetableHeaders();
    }

}
