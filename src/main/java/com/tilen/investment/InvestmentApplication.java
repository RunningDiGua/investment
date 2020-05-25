package com.tilen.investment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.fasterxml.jackson.databind.JsonNode;
import com.tilen.investment.common.ExcelUtil;
import com.tilen.investment.common.JsonMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.ArrayUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvestmentApplication {

  public static void main(String[] args) throws IOException {
    // testStock();
    SpringApplication.run(InvestmentApplication.class, args);

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
}
