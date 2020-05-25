package com.tilen.investment.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Slf4j
public class OutPut {
  public static void output(HSSFWorkbook workbook, String fileName, HttpServletResponse response) {
    OutputStream os = null;
    try {

      response.reset();
      // 设置生成的文件类型
      response.setContentType("application/msexcel");
      // 设置文件头编码方式和文件名
      response.setCharacterEncoding("UTF-8");

      response.setHeader(
          "Content-Disposition",
          "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".xls");
      os = response.getOutputStream();

      try {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
      } catch (Exception e) {
        log.error("数据下载失败", e);
      }
      workbook.write(os);

    } catch (IOException e) {
      log.error("");
    } catch (Exception e) {

    } finally {
      if (os != null) {
        try {
          os.flush();
          os.close();
        } catch (IOException e) {
        }
      }
    }
  }
}
