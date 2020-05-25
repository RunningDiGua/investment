package com.tilen.investment.stocks;

import java.time.LocalDateTime;

public class StockQueryBuilder {
  private static int year;
  private static int month;
  private static int day;

  static {
    LocalDateTime now = LocalDateTime.now();
    year = now.getYear();
    month = now.getMonth().getValue();
    day = now.getDayOfMonth();

    deploySchedule();
  }

  public static void main(String[] args) {
    String roeSuggest = getROESuggest(2016, 5, 20);
    // 2012年到2018年ROE≥15%,2019年9月30日ROE≥11.25%,上市时间大于5年,行业,2018年营收增长率,2018年净利润增长率,2019年9月30日营收增长率,2019年9月30日净利润增长率,2015年到2018年营业收入,2015年到2018年应收帐款,2015年到2018年的存货,2016年到2018年的流动比率.
    System.out.println("建议的爱问财搜索条件为：");
    System.out.println(roeSuggest);
  }

  private static void deploySchedule() {
    System.out.println(
        "每年4-1至4-30日,披露一季报\n"
            + "每年7-1至8-31日,披露中报\n"
            + "每年10-1至10-31日,披露三季报\n"
            + "每年1-1至4-30日,披露上年年报");
    System.out.println("--------------");
    System.out.println("当前已经披露的最新报表为：" + getLatestOne(year, month));
  }

  private static String getLatestOne(int year, int month) {
    if (month < 5) {
      return year - 1 + "年三季报";
    }
    if (month < 9) {
      return year - 1 + "年年报 + " + year + "年一季报";
    }
    if (month < 11) {
      return year + "年中报";
    }
    return year + "三季报";
  }

  public static String getROESuggest(int year, int month, int day) {
    StringBuilder builder = new StringBuilder();
    double roeUnit = 3.75;
    int roeYear = year;
    double roe = 0;
    String roeString = "";
    String roeMonthStr = "";
    int from, to = 0;
    if (month < 5) {
      to = year - 1 - 1;
      roeYear = roeYear - 1;
      roe = roeUnit * 3;
      roeMonthStr = "9月30日";
    } else if (month < 9) {
      to = year - 1;
      roeYear = roeYear;
      roe = roeUnit * 1;
      roeMonthStr = "3月31日";
    } else if (month < 11) {
      to = year - 1;
      roeYear = roeYear;
      roe = roeUnit * 2;
      roeMonthStr = "6月30日";
    } else {
      to = year - 1;
      roeYear = roeYear;
      roe = roeUnit * 3;
      roeMonthStr = "9月30日";
    }
    from = to - 6;
    roeString = roe + "%";
    builder
        .append(from)
        .append("年到")
        .append(to)
        .append("年ROE大于等于15%")
        .append(",")
        .append(roeYear)
        .append("年")
        .append(roeMonthStr)
        .append("ROE大于等于")
        .append(roeString)
        .append(",")
        .append("上市时间>5年")
        .append(",行业")
        .append(",")
        .append(to)
        .append("年营收增长率")
        .append(",")
        .append(to)
        .append("年净利润增长率")
        .append(",")
        .append(roeYear)
        .append("年")
        .append(roeMonthStr)
        .append("营收增长率")
        .append(",")
        .append(roeYear)
        .append("年")
        .append(roeMonthStr)
        .append("净利润增长率")
        .append(",")
        .append(to - 3)
        .append("年到")
        .append(to)
        .append("年营业收入")
        .append(",")
        .append(to - 3)
        .append("年到")
        .append(to)
        .append("年应收帐款")
        .append(",")
        .append(to - 3)
        .append("年到")
        .append(to)
        .append("年的存货")
        .append(",")
        .append(to - 2)
        .append("年到")
        .append(to)
        .append("年的流动比率");
    return builder.toString();
  }
}
