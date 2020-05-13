package com.tilen.investment.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class JsonMapper {

  public static final String JSON_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String JSON_DATE_FORMAT = "yyyy-MM-dd";
  public static final String JSON_DATE_ZONE = "GMT+8";

  public static JsonNode getNode(String jsonStr) {
    if (jsonStr == null || jsonStr.isEmpty()) {
      // log.error("Failed to translate object [{}] from str [{}]", clazz.getSimpleName(),
      // jsonStr);
      return null;
    }
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readTree(jsonStr);
    } catch (IOException e) {
      log.error(
          "Failed to translate JsonNode from str [{}] with err [{}]", jsonStr, e.getMessage());
      return null;
    }
  }

  public static <T> T getObj(String jsonStr, Class<T> clazz) {
    if (jsonStr == null || jsonStr.isEmpty()) {
      // log.error("Failed to translate object [{}] from str [{}]", clazz.getSimpleName(),
      // jsonStr);
      return null;
    }
    try {
      ObjectMapper mapper = new ObjectMapper();
      T obj = mapper.readValue(jsonStr, clazz);
      return obj;
    } catch (IOException e) {
      log.error(
          "Failed to translate object [{}] from str [{}] with err [{}]",
          clazz.getSimpleName(),
          jsonStr,
          e.getMessage());
      return null;
    }
  }

  public static <T> T getObj(JsonNode jsonNode, Class<T> clazz) {
    if (jsonNode == null) {
      log.error("Failed to translate object [{}] from json node[null]", clazz.getSimpleName());
    }
    try {
      ObjectMapper mapper = new ObjectMapper();
      T obj = mapper.treeToValue(jsonNode, clazz);
      return obj;
    } catch (IOException e) {
      log.error(
          "Failed to translate object [{}] from node str [{}] with err [{}]",
          clazz.getSimpleName(),
          jsonNode.toString(),
          e.getMessage());
      return null;
    }
  }

  public static String toJson(Object o) {
    if (o == null) {
      return null;
    }
    try {
      ObjectMapper mapper = new ObjectMapper();
      StringWriter sw = new StringWriter();
      mapper.writeValue(sw, o);
      return sw.toString();
    } catch (IOException e) {
      return null;
    }
  }

  public static JsonNode toNode(Object o) {
    if (o == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.valueToTree(o);
  }
}
