package com.tilen.investment.common.excel.format;

import java.util.List;

public interface IFormat<T, O> {
  O getFormat(T t);

  List<T> getColumnList();
}
