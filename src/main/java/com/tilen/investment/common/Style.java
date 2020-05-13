package com.tilen.investment.common;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class Style {

  public static void setCell(HSSFRow row, Integer value, int columnIndex, HSSFCellStyle style) {
    HSSFCell cell = row.createCell(columnIndex, CellType.NUMERIC);
    cell.setCellValue(value);
    cell.setCellStyle(style);
  }

  public static void setCell(HSSFRow row, String value, int columnIndex, HSSFCellStyle style) {
    HSSFCell cell = row.createCell(columnIndex, CellType.STRING);
    cell.setCellValue(value);
    cell.setCellStyle(style);
  }

  public static void beautifySheet(HSSFSheet sheet, int columnNum) {
    // 让列宽随着导出的列长自动适应
    for (int colNum = 0; colNum < columnNum; colNum++) {
      int columnWidth = sheet.getColumnWidth(colNum) / 256;
      for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
        HSSFRow currentRow;
        // 当前行未被使用过
        if (sheet.getRow(rowNum) == null) {
          currentRow = sheet.createRow(rowNum);
        } else {
          currentRow = sheet.getRow(rowNum);
        }
        if (currentRow.getCell(colNum) != null) {
          HSSFCell currentCell = currentRow.getCell(colNum);
          if (currentCell.getCellType() == CellType.STRING) {
            int length = currentCell.getStringCellValue().getBytes().length;
            if (columnWidth < length) {
              columnWidth = length;
            }
          }
        }
      }
      int width = (columnWidth + 4) * 256;
      if (colNum == 0) {
        width = 11264;
      }
      sheet.setColumnWidth(colNum, width);
    }
  }

  /**
   * 列头单元格样式
   *
   * @param workbook
   * @return
   */
  public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

    // 设置字体
    HSSFFont font = workbook.createFont();
    // 设置字体大小
    font.setFontHeightInPoints((short) 11);
    // 字体加粗
    font.setBold(true);
    // 设置字体名字
    font.setFontName("Courier New");
    // 设置样式;
    HSSFCellStyle style = workbook.createCellStyle();
    // 设置样式
    setStyle(style);
    // 在样式用应用设置的字体;
    style.setFont(font);

    return style;
  }

  public static HSSFCellStyle setStyle(HSSFCellStyle style) {
    // 设置底边框;
    style.setBorderBottom(BorderStyle.THIN);
    // 设置底边框颜色;
    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    // 设置左边框;
    style.setBorderLeft(BorderStyle.THIN);
    // 设置左边框颜色;
    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    // 设置右边框;
    style.setBorderRight(BorderStyle.THIN);
    // 设置右边框颜色;
    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
    // 设置顶边框;
    style.setBorderTop(BorderStyle.THIN);
    // 设置顶边框颜色;
    style.setTopBorderColor(IndexedColors.BLACK.getIndex());
    // 设置自动换行;
    style.setWrapText(false);
    // 设置水平对齐的样式为居中对齐;
    style.setAlignment(HorizontalAlignment.CENTER);
    // 设置垂直对齐的样式为居中对齐;
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    return style;
  }

  /*
   * 列数据信息单元格样式
   */
  public static HSSFCellStyle getDataStyle(HSSFWorkbook workbook) {
    // 设置字体
    HSSFFont font = workbook.createFont();
    // 设置字体名字
    font.setFontName("Courier New");
    // 设置样式;
    HSSFCellStyle style = workbook.createCellStyle();
    // 设置样式
    setStyle(style);
    // 在样式用应用设置的字体;
    style.setFont(font);
    return style;
  }
}
