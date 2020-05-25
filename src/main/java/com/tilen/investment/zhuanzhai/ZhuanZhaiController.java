package com.tilen.investment.zhuanzhai;

import com.tilen.investment.zhuanzhai.service.ZhuanZhaiService;
import com.tilen.investment.zhuanzhai.vo.ZhuanZhaiReqVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
