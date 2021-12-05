package com.ads.configuration;

import com.ads.models.ClassRoom;
import com.ads.models.Timetable;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * JMA - 24/2/2021 17:20
 **/
@Component
@Log4j2
public class StartUp implements CommandLineRunner {
    // ONLY FOR TESTING
    public static List<ClassRoom> classRooms = new ArrayList<>();
    public static List<Timetable> timetables = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
       /* InputStream inputStream = new ClassPathResource("static/ADS_Caracterizacao_das_salas.csv").getInputStream();
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
        classRooms = ClassRoomMapper.toClassRoom(parse);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(classRooms);

        inputStream = new ClassPathResource("static/ADS_horario_2.csv").getInputStream();
        ColumnPositionMappingStrategy<TimetableDTO> mss = new ColumnPositionMappingStrategy();
        mss.setType(TimetableDTO.class);

        reader = Files.newBufferedReader(Paths.get(new ClassPathResource("static/ADS_horario_2.csv").getURI()));
        CsvToBean<TimetableDTO> cbb = new CsvToBeanBuilder(reader)
                .withType(TimetableDTO.class)
                .withSkipLines(1)
                .withSeparator(';')
                .withMappingStrategy(mss)
                .build();
        List<TimetableDTO> timetableDTOList = cbb.parse();
        timetables = TimetableMapper.toTimetable(timetableDTOList)
                .stream()
                .peek(timetable -> timetable.setClassRoom(null))
                .collect(Collectors.toList());
        timetables.size();

        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule());
        json = obj.writer().withDefaultPrettyPrinter().writeValueAsString(timetables);

        FifoAlgorithm fifoAlgorithm = new FifoAlgorithm();
        List<Timetable> apply = fifoAlgorithm.apply(classRooms, timetables, new ArrayList<>());
        apply.stream().forEach(System.out::println);

        FeatureAlgorithm featureAlgorithm = new FeatureAlgorithm();
        List<Timetable> apply1 = featureAlgorithm.apply(classRooms, timetables, new ArrayList<>());
        apply1.stream().forEach(System.out::println);*/

        /*SWRLAPIService swrlapiService = new SWRLAPIService();
        swrlapiService.algorithm(3);*/
//        Workbook workbook = WorkbookFactory.create(new ClassPathResource("static/ADS_Caracterizacao_das_salas.xls").getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        List<ClassObj> cars = PoiPOJOUtils.sheetToPOJO(sheet, ClassObj.class);
    }
}
