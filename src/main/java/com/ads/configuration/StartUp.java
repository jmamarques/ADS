package com.ads.configuration;

import com.ads.dto.ClassDTO;
import com.ads.dto.TimetableDTO;
import com.ads.models.ClassRoom;
import com.ads.utils.mapper.ClassRoomMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * JMA - 24/2/2021 17:20
 **/
@Component
@Log4j2
public class StartUp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = new ClassPathResource("static/ADS_Caracterizacao_das_salas.csv").getInputStream();
        ColumnPositionMappingStrategy<ClassDTO> ms = new ColumnPositionMappingStrategy();
        ms.setType(ClassDTO.class);

        Reader reader = Files.newBufferedReader(Paths.get(new ClassPathResource("static/ADS_Caracterizacao_das_salas.csv").getURI()));
        CsvToBean<ClassDTO> cb = new CsvToBeanBuilder(reader)
                .withType(ClassDTO.class)
                .withSkipLines(1)
                .withSeparator(';')
                .withMappingStrategy(ms)
                .build();
        List<ClassDTO> parse = cb.parse();
        List<ClassRoom> classRooms = ClassRoomMapper.toTimetable(parse);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(parse);

        inputStream = new ClassPathResource("static/ADS_horario_2.csv").getInputStream();
        ColumnPositionMappingStrategy<TimetableDTO> mss = new ColumnPositionMappingStrategy();
        mss.setType(TimetableDTO.class);

        reader = Files.newBufferedReader(Paths.get(new ClassPathResource("static/ADS_horario_2.csv").getURI()));
        CsvToBean<TimetableDTO> cbb = new CsvToBeanBuilder(reader)
                .withType(TimetableDTO.class)
                .withSkipLines(1)
                .withSeparator(';')
                .withMappingStrategy(ms)
                .build();
        List<TimetableDTO> parse1 = cbb.parse();
//        Workbook workbook = WorkbookFactory.create(new ClassPathResource("static/ADS_Caracterizacao_das_salas.xls").getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        List<ClassObj> cars = PoiPOJOUtils.sheetToPOJO(sheet, ClassObj.class);
    }

}
