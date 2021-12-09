package com.ads.dto;

import com.ads.models.dto.TimetableDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * JMA - 16/11/2021 23:09
 **/
@Log4j2
class TimetableDTOTest {

    List<TimetableDTO> timetableDTOS;

    /**
     * @throws IOException
     */
    @BeforeEach
    void setUp() throws IOException {
        InputStream inputStream = new ClassPathResource("static/ADS_horario_2.csv").getInputStream();
        ColumnPositionMappingStrategy<TimetableDTO> ms = new ColumnPositionMappingStrategy();
        ms.setType(TimetableDTO.class);

        Reader reader = Files.newBufferedReader(Paths.get(new ClassPathResource("static/ADS_horario_2.csv").getURI()));
        CsvToBean<TimetableDTO> cb = new CsvToBeanBuilder(reader)
                .withType(TimetableDTO.class)
                .withSkipLines(1)
                .withSeparator(';')
                .withMappingStrategy(ms)
                .build();
        timetableDTOS = cb.parse();
    }

    /**
     * @throws JsonProcessingException
     */
    @Test
    void testPrintList() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(timetableDTOS);
        log.debug(json);
    }
}
