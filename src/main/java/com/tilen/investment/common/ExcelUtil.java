package com.tilen.investment.common;

import com.tilen.investment.common.excel.ExcelColumnType;
import com.tilen.investment.common.excel.ExcelColumnTypeHandler;
import com.tilen.investment.common.excel.format.IFormat;
import com.tilen.investment.common.excel.format.ZhuanZhaiFormat;
import com.tilen.investment.zhuanzhai.ZhuanZhaiHttpResp;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ExcelUtil {

  // private static String testList =
  //
  public static final String SALES_DATA_TITLE = "可转债数据表";
  // public static List<Pair<String, String>> headList = new ArrayList<>();
  //
  // static {
  //   headList.add(new Pair<>("代码", "bond_id"));
  //   headList.add(new Pair<>("转债名称", "bond_nm"));
  //   headList.add(new Pair<>("现价", "price"));
  //   headList.add(new Pair<>("涨跌幅", "increase_rt"));
  //   headList.add(new Pair<>("正股名称", "stock_nm"));
  //   headList.add(new Pair<>("正股价", "sprice"));
  //   headList.add(new Pair<>("正股涨跌", "sincrease_rt"));
  //   headList.add(new Pair<>("PB", "pb"));
  //   headList.add(new Pair<>("转股价", "convert_price"));
  //   headList.add(new Pair<>("转股价值", "convert_value"));
  //   headList.add(new Pair<>("溢价率", "premium_rt"));
  //   // headList.add(new Pair<>("纯债价值",""));
  //   headList.add(new Pair<>("评级", "rating_cd"));
  //   // headList.add(new Pair<>("期权价值",""));
  //   headList.add(new Pair<>("回售触发价", "put_convert_price"));
  //   headList.add(new Pair<>("强赎触发价", "force_redeem_price"));
  //   headList.add(new Pair<>("转股开始日期", "convert_dt"));
  //   headList.add(new Pair<>("转股结束日期", "maturity_dt"));
  //   // headList.add(new Pair<>("转债占比",""));
  //   headList.add(new Pair<>("到期时间", "short_maturity_dt"));
  //   headList.add(new Pair<>("剩余年限", "year_left"));
  //   headList.add(new Pair<>("到期税前收益率", "ytm_rt"));
  //   headList.add(new Pair<>("双底", "dblow"));
  //   headList.add(new Pair<>("转股说明", "convert_cd_tip"));
  // }

  public static void main(String[] args) {
    //
    // try {
    //  test();
    // } catch (IOException e) {
    //  log.error(e.getMessage());
    //  e.printStackTrace();
    // }

    for (int i = 0; i < 10; i++) {
      System.out.println("out - " + i);
      for (int j = 0; j < 10; j++) {
        if (j < 3) {
          continue;
        }
        System.out.println("inner - " + j);
      }
    }
  }

  public static void test() throws IOException {
    String toString = IOUtils.resourceToString("/test.txt", Charset.defaultCharset());
    ZhuanZhaiHttpResp zhuanzhai = JsonMapper.getObj(toString, ZhuanZhaiHttpResp.class);
    List<ZhuanZhaiHttpResp.Row> rows = zhuanzhai.getRows();
    //
    ZhuanZhaiFormat iFormat = new ZhuanZhaiFormat();
    HSSFWorkbook workbook = getWorkbook(zhuanzhai.getRows(), new ZhuanZhaiFormat());
    workbook.write(
        new File("/Users/wangchangdong/desktop/测试导出" + System.currentTimeMillis() + ".xls"));
  }

  /**
   * 组装数据
   *
   * @return
   */
  public static HSSFWorkbook getWorkbook(
      List<ZhuanZhaiHttpResp.Row> list, IFormat<Pair, ExcelColumnType> iFormat) {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet(SALES_DATA_TITLE);
    Integer rowStart = 0;

    // 在索引0的位置创建行(最顶端的行开始的第二行)
    HSSFRow rowRowName = sheet.createRow(rowStart);
    // 获取列头样式对象
    HSSFCellStyle columnTopStyle = Style.getColumnTopStyle(workbook);

    // 将列头设置到sheet的单元格中
    int columnNum = iFormat.getColumnList().size();
    List<Pair> headList = iFormat.getColumnList();
    for (int i = 0; i < columnNum; i++) {
      // 创建列头对应个数的单元格
      HSSFCell cellRowName = rowRowName.createCell(i);
      // 设置列头单元格的数据类型
      cellRowName.setCellType(CellType.STRING);
      HSSFRichTextString text = new HSSFRichTextString((String) headList.get(i).getKey());
      // 设置列头单元格的值
      cellRowName.setCellValue(text);
      // 设置列头单元格样式
      cellRowName.setCellStyle(columnTopStyle);
    }
    HSSFCellStyle style = Style.getDataStyle(workbook);

    // sheet = sheetHead(workbook, sheet, reqVo, drugstoreName);
    Integer rowNum = 0;

    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        //      itemVoNum = itemVoNum + 1;
        if (rowNum == 0) {
          rowNum = i + rowStart + 1;
        } else {
          rowNum = rowNum + 1;
        }
        HSSFRow row = sheet.createRow(rowNum);
        for (int cr = 0; cr < headList.size(); cr++) {
          ZhuanZhaiHttpResp.Cell icell = list.get(i).getCell();
          if (shuldContinue(icell)) {
            continue;
          }
          Object value = ClassUtil.getValue(icell, "get", (String) headList.get(cr).getValue());
          String content = (String) value;
          combineCell(
              workbook,
              sheet,
              row,
              style,
              content,
              rowNum,
              rowNum,
              cr,
              cr,
              true,
              iFormat.getFormat(headList.get(cr)));
        }
      }
    }
    Style.beautifySheet(sheet, headList.size());
    return workbook;
  }

  private static boolean shuldContinue(ZhuanZhaiHttpResp.Cell cell) {
    if ("待上市".equalsIgnoreCase(cell.getPrice_tips())) {
      return true;
    }
    return false;
  }

  private static String moneyPercentToString(Integer money) {
    if (money == 0) {
      return "0";
    }
    DecimalFormat df = new DecimalFormat("0.00");
    String res = df.format((float) money / 100);
    return res;
  }

  /**
   * 合并详情单元格
   *
   * @param sheet
   * @param row
   * @param style
   * @param content
   * @param firstRow
   * @param lastRow
   * @param firstCol
   * @param lastCol
   * @param combine
   * @return
   */
  public static HSSFSheet combineCell(
      HSSFWorkbook workbook,
      HSSFSheet sheet,
      HSSFRow row,
      HSSFCellStyle style,
      String content,
      Integer firstRow,
      Integer lastRow,
      Integer firstCol,
      Integer lastCol,
      Boolean combine,
      ExcelColumnType type) {
    if (!Objects.equals(firstRow, lastRow)) {
      CellRangeAddress range = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
      sheet.addMergedRegion(range);
      HSSFCell cellObj = row.createCell(firstCol);
      ExcelColumnTypeHandler.handle(workbook, cellObj, content, type);
      // cellObj.setCellValue(content);

      //      CellStyle cs = workbook.createCellStyle();
      //      cs.setAlignment(HorizontalAlignment.CENTER);
      //      cs.setVerticalAlignment(VerticalAlignment.CENTER);
      //      cellObj.setCellStyle(cs);

    } else {
      HSSFCell cellObj = row.createCell(firstCol);
      ExcelColumnTypeHandler.handle(workbook, cellObj, content, type);
      // cellObj.setCellValue(content);
      //      Style.setCell(row, content, firstCol, style);
    }
    return sheet;
  }
}
