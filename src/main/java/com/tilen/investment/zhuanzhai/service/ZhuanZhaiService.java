package com.tilen.investment.zhuanzhai.service;

import com.tilen.investment.common.ExcelUtil;
import com.tilen.investment.common.HttpClient;
import com.tilen.investment.common.excel.format.ZhuanZhaiFormat;
import com.tilen.investment.zhuanzhai.ZhuanZhaiHttpResp;
import com.tilen.investment.zhuanzhai.vo.ZhuanZhaiReqVo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ZhuanZhaiService {
  @Autowired ZhuanZhaiFormat zhuanZhaiFormat;
  private static final String DEFAULT_URL =
      "https://www.jisilu.cn/data/cbnew/cb_list/?___jsl=LST___t=";

  public void getValue(ZhuanZhaiReqVo reqVo, HttpServletResponse response) {}

  public void getDefault(HttpServletResponse response) {
    String url = DEFAULT_URL + System.currentTimeMillis();
    ZhuanZhaiHttpResp data = this.getData(url);
    this.outPut(data, response);
  }
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------
  // ---------------------------------------------------------------------------------

  private ZhuanZhaiHttpResp getData(String url) {
    ZhuanZhaiHttpResp zhuanZhaiHttpResp = HttpClient.doGet(url, ZhuanZhaiHttpResp.class);
    return zhuanZhaiHttpResp;
  }

  private void outPut(ZhuanZhaiHttpResp data, HttpServletResponse response) {
    OutputStream os = null;
    try {

      HSSFWorkbook workbook = ExcelUtil.getWorkbook(data.getRows(), zhuanZhaiFormat);
      response.reset();
      // 设置生成的文件类型
      response.setContentType("application/msexcel");
      // 设置文件头编码方式和文件名
      response.setCharacterEncoding("UTF-8");
      String name = "可转债统计" + DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
      response.setHeader(
          "Content-Disposition",
          "attachment; filename=" + new String(name.getBytes("utf-8"), "ISO8859-1") + ".xls");
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
