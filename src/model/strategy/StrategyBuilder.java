package model.strategy;

import java.time.LocalDate;

public interface StrategyBuilder {

  StrategyBuilder addStock(String stock, Double weight);

  StrategyBuilder setCommission(Double commission);

  StrategyBuilder setPrincipal(Double principal);

  StrategyBuilder setStartDate(LocalDate date);

  StrategyBuilder setEndDate(LocalDate date);

  StrategyBuilder setFrequency(int frequency);

  Strategy build();

}
