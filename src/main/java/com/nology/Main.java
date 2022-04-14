package com.nology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

  public static void main(String[] args) throws IOException {
    String csvFile =
        "C:\\Users\\lae32\\Desktop\\sandbox\\java-library-project" + "\\books_data.csv";

    try (InputStream in = new FileInputStream(csvFile); ) {
      CSV csv = new CSV(true, ',', in);
      List<String> fieldNames = null;
      if (csv.hasNext()) fieldNames = new ArrayList<>(csv.next());
      List<Map<String, String>> list = new ArrayList<>();
      while (csv.hasNext()) {
        List<String> x = csv.next();
        Map<String, String> obj = new LinkedHashMap<>();
        for (int i = 0; i < fieldNames.size(); i++) {
          obj.put(fieldNames.get(i), x.get(i));
        }
        list.add(obj);
      }
      ObjectMapper mapper = new ObjectMapper();
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      mapper.writeValue(System.out, list);
    }
  }
}
