package com.ads.utils.parser.csv;

import com.ads.utils.Tuple;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * JMA - 09/12/2021 20:24
 **/
public class CsvPOJOUtils {
    /**
     * Convert CSV into DTO object
     *
     * @param inputStream - file
     * @param createClass - function to create DTO
     * @param headers     - headers (columns canfiguration)
     * @param splitter    - splitter on file
     * @param <T>         - Generic DTO
     * @return List of DTO's
     */
    public static <T> List<T> readDtoFromCSV(InputStream inputStream, Function<Tuple<String[], List<String>>, T> createClass, List<String> headers, String splitter) {
        List<T> list = new ArrayList<>();
        boolean isHeader = true;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            // skip header
            if (isHeader) {
                br.readLine();
                isHeader = false;
            }
            String line = br.readLine();
            while (line != null) {
                list.add(createClass.apply(new Tuple<>(StringUtils.splitPreserveAllTokens(line, splitter), headers)));
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return list;
    }
}
