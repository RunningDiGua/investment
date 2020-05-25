package com.tilen.investment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.fasterxml.jackson.databind.JsonNode;
import com.tilen.investment.common.ExcelUtil;
import com.tilen.investment.common.JsonMapper;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class InvestmentApplication {

  public static void main(String[] args) throws IOException {
    // testStock();
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

    JsonNode json = JsonMapper.getNode(toString);
    JsonNode jsonNode = json.get("data").get("data");
    JsonNode jsonNode1 = jsonNode.get(0);
    Iterator<String> stringIterator = jsonNode1.fieldNames();
    List<String> list = IteratorUtils.toList(stringIterator);
    System.out.println(stringIterator);
    JSONArray read = (JSONArray) JSONPath.read(toString, "data/data");
    JSONObject o = (JSONObject) read.get(0);
    Collection<Object> values = o.values();
    Set<?> objects = JSONPath.keySet(read.getJSONObject(0), "/");
    System.out.println(objects);

    HSSFWorkbook workBookFrJson = ExcelUtil.getWorkBookFrJson(list, toString, "data/data");
    outPut(workBookFrJson, "ceshi.xls", "/Users/wangchangdong/Desktop");
    // System.out.println(workBookFrJson);
  }

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
