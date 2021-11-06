package com.ads.configuration;

import com.ads.dto.ClassDto;
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
        ColumnPositionMappingStrategy<ClassDto> ms = new ColumnPositionMappingStrategy();
        ms.setType(ClassDto.class);

        Reader reader = Files.newBufferedReader(Paths.get(new ClassPathResource("static/ADS_Caracterizacao_das_salas.csv").getURI()));
        CsvToBean<ClassDto> cb = new CsvToBeanBuilder(reader)
                .withType(ClassDto.class)
                .withSkipLines(1)
                .withSeparator(';')
                .withMappingStrategy(ms)
                .build();
        List<ClassDto> parse = cb.parse();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(parse);
//        Workbook workbook = WorkbookFactory.create(new ClassPathResource("static/ADS_Caracterizacao_das_salas.xls").getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        List<ClassObj> cars = PoiPOJOUtils.sheetToPOJO(sheet, ClassObj.class);
    }

}
