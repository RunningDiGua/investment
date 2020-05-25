package com.tilen.investment.stocks.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tilen.investment.common.ExcelUtil;
import com.tilen.investment.common.HttpClient;
import com.tilen.investment.common.JsonMapper;
import com.tilen.investment.common.OutPut;
import com.tilen.investment.common.excel.format.ZhuanZhaiFormat;
import com.tilen.investment.zhuanzhai.ZhuanZhaiHttpResp;
import com.tilen.investment.zhuanzhai.vo.ZhuanZhaiReqVo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockService {
  @Autowired ZhuanZhaiFormat zhuanZhaiFormat;
  private static final String DEFAULT_URL =
      "https://www.jisilu.cn/data/cbnew/cb_list/?___jsl=LST___t=";

  public void getValue(ZhuanZhaiReqVo reqVo, HttpServletResponse response) {}

  public void getDefault(HttpServletResponse response) {
    String url = DEFAULT_URL + System.currentTimeMillis();
    String data = this.getData(url);
    JsonNode json = JsonMapper.getNode(data);
    JsonNode jsonNode = json.get("data").get("data");
    JsonNode jsonNode1 = jsonNode.get(0);
    Iterator<String> stringIterator = jsonNode1.fieldNames();
    List<String> list = IteratorUtils.toList(stringIterator);
    this.outPut(list, data, response);
  }
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------

  private String getData(String url) {
    String stockResp = HttpClient.doGet(url, String.class);
    return stockResp;
  }

  private void outPut(List<String> heads, String json, HttpServletResponse response) {

    HSSFWorkbook workbook = ExcelUtil.getWorkBookFrJson(heads, json, "data/data");

    String name = "stock统计" + DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
    OutPut.output(workbook, name, response);
  }
}
