package com.ads.controllers;

import com.ads.models.dto.RequestDTO;
import com.ads.models.internal.Timetable;
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
 * Control the timetable servixe and the service of file
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

    /**
     * process a csv file to get the headers
     *
     * @param file - generic file to give the headers
     * @return headers inside of file (excel or csv, json TBD)
     */
    @PostMapping("/headers")
    @ApiOperation(value = "Upload csv file to get headers file",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> processFileToGetHeaders(
            @ApiParam(name = "file", value = "Select the file to Upload", required = true)
            @RequestPart(value = "file") MultipartFile file) {
        return fileService.getHeadersFromFile(file);
    }

    /**
     * obtains the headers of the classroom
     *
     * @return headers for classroom file
     */
    @GetMapping("/headers/classroom")
    @ApiOperation(value = "Get headers for Classroom", produces = MediaType.APPLICATION_JSON_VALUE)
    public String[] getClassroomHeaders() {
        return timetablesService.getClassRoomHeaders();
    }

    /**
     * Obtains the timetable headers
     * @return headers for timetable file
     */
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


    /**
     * Submit form - receives a classroom file and timetable file  and returns a new timetable json
     *
     * @param classFile     - classroom file
     * @param timetableFile - timetable file
     * @param requestDTO    - additional parameters as mappings, criteria and execution speed
     * @return timetable json -> based on validations and criteria pass through initial request
     */
    @PostMapping("/execute")
    @ApiOperation(value = "Upload timetables to process (File)",
            produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<List<Timetable>> processTimetable(
            @ApiParam(name = "classFile", value = "Select the file to Upload", required = true)
            @RequestPart(value = "classFile")
                    MultipartFile classFile,
            @ApiParam(name = "timetableFile", value = "Select the file to Upload", required = true)
            @RequestPart(value = "timetableFile")
                    MultipartFile timetableFile,
            @ModelAttribute RequestDTO requestDTO) {
        return fileService.processForm(requestDTO, classFile, timetableFile);
    }

    /**
     * Submit form - receives a classroom file and timetable file  and returns a new timetable json
     *
     * @param classFile     - classroom file
     * @param timetableFile - timetable file
     * @param requestDTO    - additional parameters as mappings, criteria and execution speed
     * @return Time execution for Algorithm based on seconds
     */
    @PostMapping("/time-execution")
    @ApiOperation(value = "Get Time execution for Algorithm",
            produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public long getTimeExecution(
            @ApiParam(name = "classFile", value = "Select the file to Upload", required = true)
            @RequestPart(value = "classFile")
                    MultipartFile classFile,
            @ApiParam(name = "timetableFile", value = "Select the file to Upload", required = true)
            @RequestPart(value = "timetableFile")
                    MultipartFile timetableFile,
            @ModelAttribute RequestDTO requestDTO) {
        // seconds
        long startTime = System.currentTimeMillis() / 1000;
        // put default
        fileService.processForm(requestDTO, classFile, timetableFile);
        return System.currentTimeMillis() / 1000 - startTime;
    }

}
