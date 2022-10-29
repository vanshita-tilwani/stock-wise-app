package model.stocktradings;

import java.time.LocalDate;

public interface Trade<T> {

  void buy(T trade);

  double value(LocalDate date, String trade);

  T get(String trade);
}
