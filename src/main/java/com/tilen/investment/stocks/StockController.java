package com.tilen.investment.stocks;

import com.tilen.investment.stocks.service.StockService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ss")
public class StockController {
  @Autowired StockService stockService;

  @GetMapping("default")
  public void getDefault(HttpServletResponse response) {
    stockService.getDefault(response);
  }
}
