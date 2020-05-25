package com.tilen.investment.common.excel;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.util.StringUtils;

@Slf4j
public class ExcelColumnTypeHandler {
  public static void handle(
      HSSFWorkbook workbook, HSSFCell cell, String value, ExcelColumnType type) {
    switch (type) {
      case num:
        cell.setCellType(CellType.NUMERIC);
        if ("-".equalsIgnoreCase(value)) {
          // cell.setCellValue();
          break;
        }
        if (value.contains("%")) {
          HSSFCellStyle cellStyle = workbook.createCellStyle();
          cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
          cell.setCellStyle(cellStyle);
          cell.setCellValue(transDoublePercent(value));
          break;
        }
        try {
          cell.setCellValue(Double.valueOf(value));
        } catch (Exception e) {
          log.error("转化异常", e);
          cell.setCellType(CellType.STRING);
          cell.setCellValue(transDate(value));
        }
        break;
      case date:
        cell.setCellType(CellType.STRING);
        cell.setCellValue(transDate(value));
        break;
      case text:
        cell.setCellType(CellType.STRING);
        cell.setCellValue(value);
        break;
    }
  }

  public static void handle(HSSFWorkbook workbook, HSSFCell cell, Object obj) {
    if (obj == null) {
      return;
    }
    if (obj instanceof BigDecimal) {
      cell.setCellType(CellType.NUMERIC);
      cell.setCellValue(Double.valueOf(obj.toString()));
      return;
    }
    if (obj instanceof String) {
      cell.setCellType(CellType.STRING);
      cell.setCellValue(obj.toString());
    }
  }

  public static String transDate(String origin) {
    if (StringUtils.isEmpty(origin)) {
      return "-";
    }
    // 25-12-18
    if (origin.length() == 8) {
      return "20" + origin;
    }
    return origin;
  }

  private static double transDoublePercent(String value) {
    String replace = value.replace("%", "");
    return Double.valueOf(replace) / 100;
  }
}
