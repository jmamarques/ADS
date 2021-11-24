package com.ads;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class to mapping from a xsf, csv and json file
 */
public class Mapping {

    /**
     * @param sheet
     * @return List<String>headers
     * Returns the headers from a xsf file
     */
    // method to return the headers in case of being a xlsx file
    public List<String> getHeaderData(Sheet sheet) {
        List<String> headers = new ArrayList<>();
        Row row;
        int headerId = 0;
        if (sheet.getRow(headerId) != null && sheet.getRow(headerId).cellIterator() != null) {
            row = sheet.getRow(headerId);

            for (int h = 0; h <= row.getLastCellNum(); h++) {
                if (row.getCell((short) h) != null && row.getCell((short) h).toString().length() != 0) {
                    headers.add(row.getCell((short) h).toString());
                }
            }

        }
        return headers;
    }

    /**
     * @param file
     * @return List<List<String>> headers
     * @throws IOException
     * returns the headers from a csv file
     */
    // method to return the headers in case of being a csv file
    public  List<List<String>> getHeaderDataCSV(String file) throws IOException {
        List<List<String>> headers = new ArrayList<>();
        String delimiter = ",";
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> values = Arrays.asList(br.readLine().split(delimiter));
        headers.add(values);
        return headers;
    }

    /**
     * Method that provides an array of jsonobject from a JSONFile
     * @param File
     * @return text
     * @throws IOException
     */
    // method to return the text in a json file
    public  List<String> getDataJSON(File f) throws IOException {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(f))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray text = (JSONArray) obj;
            //Iterate over headers array
            text.forEach( hd -> parseEmployeeObject( (JSONObject) hd ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return text;
    }

    /**
     * Method that returns the headers from jsonobject
     * @param text
     * @return headers
     */
    private List<String> parseHeadersObject(JSONObject text) {
        List<String> headers = new ArrayList<>();
        String inscritos = (String) text.get("inscritos");
        headers.add(inscritos)
        String lotacao = (String) text.get("lotacao");
        headers.add(lotacao)
        String sala = (String) text.get("sala aula");
        headers.add(sala)

    }


    //read from a db
    public List<String> getHeaderDatafromDB(Sheet sheet) {
        List<String> headers = new ArrayList<>();
        String sql="SELECT unidade,  turma, lotacao, sala FROM data"
        PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
/*
        while (iterator.hasNext()){

            preparedStatement.setInt(1, valuefrom_ArrayList);

            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            while (rs.next()) {
                String emp_name  = rs.getString("EMPNAME");
                String emp_age = rs.getString("EMPAGE");
            }
        }                                        ????*/
        return headers;
    }

    /*
    When call this 2 methods
     String ext1 = FilenameUtils.getExtension("pathhhh");
     verify if ends in xlsx or csv */
}
