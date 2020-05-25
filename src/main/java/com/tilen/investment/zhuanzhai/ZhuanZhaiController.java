package com.tilen.investment.zhuanzhai;

import com.tilen.investment.zhuanzhai.service.ZhuanZhaiService;
import com.tilen.investment.zhuanzhai.vo.ZhuanZhaiReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("zz")
public class ZhuanZhaiController {
  @Autowired ZhuanZhaiService service;

  @PostMapping
  public void getValue(@RequestBody ZhuanZhaiReqVo reqVo, HttpServletResponse response) {
    service.getValue(reqVo, response);
  }

  @GetMapping("default")
  public void getDefault(HttpServletResponse response) {
    service.getDefault(response);
  }
}
