package model.strategy;

import java.time.LocalDate;
import java.util.Map;

public class OneTimeStrategy extends AbstractStrategy {


  public OneTimeStrategy(Double principal, Map<String, Double> weights, Double commission,
                           LocalDate start, LocalDate end, int days) {
    super(principal, weights, commission, start, end, days);
  }


  @Override
  public String toString() {
    // TODO : to preserve Strategy
    return "";
  }

  public static class OneTimeStrategyBuilder extends AbstractStrategyBuilder {

    public OneTimeStrategyBuilder() {
      super();
    }

    @Override
    public StrategyBuilder setEndDate(LocalDate date) {
      return this;
    }

    @Override
    public StrategyBuilder setFrequency(int frequency) {
      return this;
    }

    @Override
    public Strategy build() {
      return new OneTimeStrategy(this.principal, this.weights, this.commission, this.start,
              this.start, 1);
    }
  }
}
