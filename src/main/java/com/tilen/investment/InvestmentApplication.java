package com.tilen.investment;

import com.tilen.investment.common.JsonMapper;
import java.io.IOException;
import java.nio.charset.Charset;

import com.tilen.investment.zhuanzhai.ZhuanZhaiHttpResp;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvestmentApplication {

  public static void main(String[] args) throws IOException {
    String toString = IOUtils.resourceToString("/test.txt", Charset.defaultCharset());
    ZhuanZhaiHttpResp obj = JsonMapper.getObj(toString, ZhuanZhaiHttpResp.class);

    SpringApplication.run(InvestmentApplication.class, args);
  }
}
