package model.strategy;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import model.stock.Stock;
import model.trade.Trade;

public class RecurringStrategy extends AbstractStrategy {

  public RecurringStrategy(Double principal, Map<String, Double> weights, Double commission,
                           LocalDate start, LocalDate end, int days) {
    super(principal, weights, commission, start, end, days);
  }


  public Set<Trade<Stock>> trades(Predicate<Trade<Stock>> predicate) {

    return null;
  }
  public static class RecurringStrategyBuilder extends AbstractStrategyBuilder {


    private LocalDate end;
    private int frequency;
    public RecurringStrategyBuilder() {
      super();
      this.end = null;
      this.frequency = 0;
    }

    @Override
    public StrategyBuilder setEndDate(LocalDate date) {
      this.end = date;
      return this;
    }

    @Override
    public StrategyBuilder setFrequency(int frequency) {
      this.frequency = frequency;
      return this;
    }

    @Override
    public Strategy build() {
      return new RecurringStrategy(this.principal, this.weights, this.commission, this.start,
              this.end, this.frequency);
    }
  }
}
