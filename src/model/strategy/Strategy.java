package model.strategy;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import model.stock.Stock;
import model.trade.Trade;

public interface Strategy {

  Double principle();

  Map<String, Double> stockProportion();

  LocalDate startDate();

  LocalDate endDate();

  int frequency();

  double commission();
}
