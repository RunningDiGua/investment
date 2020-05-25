package com.tilen.investment.stocks;

import lombok.Data;

@Data
public class StocksHttpReq {
  private String question;
  private String secondary_intent = "stock";
  private String perpage = "100";
  private String condition_id;
}
