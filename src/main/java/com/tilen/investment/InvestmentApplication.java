package com.tilen.investment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class InvestmentApplication {

  public static void main(String[] args) throws IOException {
    testStock();
    // testFilter();
    SpringApplication.run(InvestmentApplication.class, args);
    //
    System.out.println("<a>http://localhost:8080/zz/default</a>");
    System.out.println("<a>http://localhost:8080/ss/default</a>");
  }

  public static void testStock() {
    String toString = null;
    try {
      toString = IOUtils.resourceToString("/iwencai.txt", Charset.defaultCharset());
    } catch (IOException e) {
      e.printStackTrace();
    }
    JSONArray read = (JSONArray) JSONPath.read(toString, "data/config/columns");
    List<String> list = new ArrayList<>();
    for (int i = 0; i < read.size(); i++) {

      JSONObject o = (JSONObject) read.get(i);
      JSONArray tilte_children = (JSONArray) o.get("tilte_children");
      if (tilte_children.size() == 0) {
        list.add();
      }
      JSONArray data_index = (JSONArray) o.get("data_index");

      int mm = data_index.size();
      for (int j = 0; j < data_index.size(); j++) {
        list.add(data_index.get(j).toString());
      }
    }
    JSONObject o = (JSONObject) read.get(0);
    Collection<Object> values = o.values();
    Set<?> objects = JSONPath.keySet(read.getJSONObject(0), "/");
    System.out.println(objects);
  }

  public static void refresh(List<String> list, JSONObject jsonObject) {}

  public static void outPut(HSSFWorkbook workbook, String fileName, String path) {
    File file = new File(path + "/" + fileName);

    try {
      if (!file.exists()) {
        boolean newFile = file.createNewFile();
      }
      workbook.write(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void testFilter() {
    List<String> list = new ArrayList<>();
    list.add("12");
    list.add("112");
    list.add("123");
    list.add("124");
    List<String> collect =
        list.stream()
            .filter(x -> !x.equalsIgnoreCase("12"))
            .filter(x -> !x.equalsIgnoreCase("112"))
            .collect(Collectors.toList());
    System.out.println(collect);
  }
}
