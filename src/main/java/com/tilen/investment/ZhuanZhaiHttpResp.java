package com.tilen.investment;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ZhuanZhaiHttpResp {

  private int page;

  private List<Row> rows;
  private int total;

  {
    rows = new ArrayList<>();
  }

  @Data
  public static class Row {
    private String id;
    private Cell cell;
  }

  @Data
  public static class Cell {
    // 转债代码
    private String bond_id; // ": "113579",
    // 转债名
    private String bond_nm; // ": "健友转债",
    // 正股全代码
    private String stock_id; // ": "sh603707",
    // 正股名
    private String stock_nm; // ": "健友股份",
    // 转股价
    private String convert_price; // ": "54.97",

    private String convert_price_valid_from; // ": null,
    // 转股期起
    private String convert_dt; // ": "2020-06-25",
    // 转股期止
    private String maturity_dt; // ": "2025-12-18",
    private String next_put_dt; // ": "2023-12-18",
    private String put_dt; // ": null,
    private String put_notes; // ": null,
    private String put_price; // ": "100.000",
    private String put_inc_cpn_fl; // ": "y",
    private String put_convert_price_ratio; // ": "34.01",
    private String put_count_days; // ": 30,
    private String put_total_days; // ": 30,
    private String put_real_days; // ": 0,
    private String repo_discount_rt; // ": "0.00",
    private String repo_valid_from; // ": null,
    private String repo_valid_to; // ": null,
    private String turnover_rt; // ": "225.42",
    private String redeem_price; // ": "118.000",
    private String redeem_inc_cpn_fl; // ": "n",
    private String redeem_price_ratio; // ": "130.000",
    private String redeem_count_days; // ": 15,
    private String redeem_total_days; // ": 30,
    private String redeem_real_days; // ": 0,
    private String redeem_dt; // ": null,
    private String redeem_flag; // ": "X",
    private String orig_iss_amt; // ": "4.400",
    private String curr_iss_amt; // ": "4.400",
    // 评级
    private String rating_cd; // ": "AA-",
    private String issuer_rating_cd; // ": "AA-",
    private String guarantor; // ": "股票质押担保",
    private String force_redeem; // ": null,
    private String real_force_redeem_price; // ": null,
    private String convert_cd; // ": "未到转股期",
    private String repo_cd; // ": null,
    private String ration; // ": null,
    private String ration_cd; // ": "753301",
    private String apply_cd; // ": "754301",
    private String online_offline_ratio; // ": null,
    private String qflag; // ": "N",
    private String qflag2; // ": "N",
    private String ration_rt; // ": "75.380",
    private String fund_rt; // ": "buy",
    private String margin_flg; // ": "",
    // PB
    private String pb; // ": "4.00",
    private String total_shares; // ": "140000000.0",
    private String sqflg; // ": "Y",
    // 正股价
    private String sprice; // ": "41.25",
    private String svolume; // ": "41429.83",
    // 正股涨跌幅
    private String sincrease_rt; // ": "7.53%",
    private String qstatus; // ": "00",
    private String last_time; // ": "15:00:03",
    // 转股价值
    private String convert_value; // ": "205.84",
    // 溢价率
    private String premium_rt; // ": "-9.95%",
    // 剩余年限
    private String year_left; // ": "5.603",
    // 到期税前收益
    private String ytm_rt; // ": "-6.95%",
    // 到期税后收益
    private String ytm_rt_tax; // ": "-7.61%",
    // 现价
    private String price; // ": "185.350",
    // 全价格
    private String full_price; // ": "185.350",
    // 涨跌幅
    private String increase_rt; // ": "5.61%",
    // 成交额
    private String volume; // ": "186137.76",
    private String convert_price_valid; // ": "Y",
    private String adj_scnt; // ": 0,
    private String adj_cnt; // ": 0,
    private String redeem_icon; // ": "",
    private String redeem_style; // ": "Y",
    private String owned; // ": 0,
    private String noted; // ": 0,
    private String ref_yield_info; // ": "",
    private String adjust_tip; // ": "",
    private String adjusted; // ": "N",
    private String option_tip; // ": "",
    private String left_put_year; // ": "-",
    // 到期时间
    private String short_maturity_dt; // ": "25-12-18",
    // 双低
    private String dblow; // ": "175.40",
    // 强赎触发价
    private String force_redeem_price; // ": "26.05",
    // 回售触发价
    private String put_convert_price; // ": "14.03",
    // 转债占比
    private String convert_amt_ratio; // ": "7.6%",
    private String stock_net_value; // ": "0.00",
    // 正股代码
    private String stock_cd; // ": "603301",
    private String pre_bond_id; // ": "sh113555",
    private String repo_valid; // ": "有效期：-",
    // 转股说明
    private String convert_cd_tip; // ": "未到转股期；2020-06-25 开始转股",
    private String price_tips; // ": "全价：185.350 最后更新：15:00:03"
  }
}
