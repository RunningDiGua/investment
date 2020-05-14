package com.tilen.investment.common.excel.format;

import com.tilen.investment.common.excel.ExcelColumnType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class ZhuanZhaiFormat implements IFormat<Pair, ExcelColumnType> {

  public static List<Pair<String, String>> headList = new ArrayList<>();
  public static Map<Pair, ExcelColumnType> map = new LinkedHashMap<>();

  static {
    map.put(new Pair<>("代码", "bond_id"), ExcelColumnType.text);
    map.put(new Pair<>("转债名称", "bond_nm"), ExcelColumnType.text);
    map.put(new Pair<>("现价", "price"), ExcelColumnType.num);
    map.put(new Pair<>("转股价", "convert_price"), ExcelColumnType.num);

    map.put(new Pair<>("正股名称", "stock_nm"), ExcelColumnType.text);
    map.put(new Pair<>("正股价", "sprice"), ExcelColumnType.num);
    map.put(new Pair<>("转股开始日期", "convert_dt"), ExcelColumnType.date);
    map.put(new Pair<>("转股结束日期", "maturity_dt"), ExcelColumnType.date);
    map.put(new Pair<>("到期时间", "short_maturity_dt"), ExcelColumnType.date);
    map.put(new Pair<>("剩余年限", "year_left"), ExcelColumnType.num);
    map.put(new Pair<>("到期税前收益率", "ytm_rt"), ExcelColumnType.num);
    map.put(new Pair<>("转股价值", "convert_value"), ExcelColumnType.num);
    map.put(new Pair<>("回售触发价", "put_convert_price"), ExcelColumnType.num);
    map.put(new Pair<>("强赎触发价", "force_redeem_price"), ExcelColumnType.num);
    map.put(new Pair<>("PB", "pb"), ExcelColumnType.text);

    map.put(new Pair<>("溢价率", "premium_rt"), ExcelColumnType.num);
    map.put(new Pair<>("评级", "rating_cd"), ExcelColumnType.text);
    map.put(new Pair<>("涨跌幅", "increase_rt"), ExcelColumnType.num);

    map.put(new Pair<>("双底", "dblow"), ExcelColumnType.num);
    map.put(new Pair<>("正股涨跌", "sincrease_rt"), ExcelColumnType.num);
    map.put(new Pair<>("转股说明", "convert_cd_tip"), ExcelColumnType.text);
    // headList.add(new Pair<>("期权价值",""));
    // headList.add(new Pair<>("纯债价值",""));
    // headList.add(new Pair<>("转债占比",""));
  }

  @Override
  public List<Pair> getColumnList() {
    return new ArrayList(map.keySet());
  }

  @Override
  public ExcelColumnType getFormat(Pair pair) {
    ExcelColumnType excelColumnType = map.get(pair);
    return excelColumnType;
  }
}
