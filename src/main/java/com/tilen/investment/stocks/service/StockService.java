package com.tilen.investment.stocks.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tilen.investment.common.ExcelUtil;
import com.tilen.investment.common.HttpClient;
import com.tilen.investment.common.JsonMapper;
import com.tilen.investment.common.OutPut;
import com.tilen.investment.common.excel.format.ZhuanZhaiFormat;
import com.tilen.investment.stocks.StockQueryBuilder;
import com.tilen.investment.stocks.StocksHttpReq;
import com.tilen.investment.zhuanzhai.vo.ZhuanZhaiReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockService {
  @Autowired ZhuanZhaiFormat zhuanZhaiFormat;
  private static final String DEFAULT_URL =
      "http://www.iwencai.com/unifiedwap/unified-wap/result/get-stock-pick";

  public void getValue(ZhuanZhaiReqVo reqVo, HttpServletResponse response) {}

  public void getDefault(HttpServletResponse response) {
    String url = DEFAULT_URL;
    LocalDateTime now = LocalDateTime.now();
    String query =
        StockQueryBuilder.getROESuggest(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    StocksHttpReq req = new StocksHttpReq();
    req.setQuestion(query);
    String data = this.getData(url, req);
    JsonNode json = JsonMapper.getNode(data);
    JsonNode jsonNode = json.get("data").get("data");
    JsonNode jsonNode1 = jsonNode.get(0);
    Iterator<String> stringIterator = jsonNode1.fieldNames();
    List<String> list = IteratorUtils.toList(stringIterator);
    list = this.filter(list);
    this.outPut(list, data, response);
  }
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------

  private String getData(String url, StocksHttpReq req) {
    String json = JsonMapper.toJson(req);
    Map<String, String> map = JsonMapper.getObj(json, Map.class);
    String stockResp = null;
    try {
      stockResp = HttpClient.doPost(url, map, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stockResp;
  }

  private void outPut(List<String> heads, String json, HttpServletResponse response) {

    HSSFWorkbook workbook = ExcelUtil.getWorkBookFrJson(heads, json, "data/data");

    String name = "stock统计" + DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
    OutPut.output(workbook, name, response);
  }

  private List<String> filter(List<String> list) {
    List<String> newList =
        list.stream()
            .filter(x -> !x.equalsIgnoreCase("最新价"))
            .filter(x -> !x.equalsIgnoreCase("最新涨跌幅"))
            .filter(x -> !x.startsWith("净资产收益率"))
            .filter(x -> !x.contains("hqCode"))
            .filter(x -> !x.contains("marketId"))
            .filter(x -> !x.contains("上市天数"))
            .collect(Collectors.toList());
    return newList;
  }
}
