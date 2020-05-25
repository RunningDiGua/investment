package com.tilen.investment.stocks;

import lombok.Data;

@Data
public class StocksHttpResp {
  private String status_code;
  private String status_msg;
  private String logid;
  private String data;
}
