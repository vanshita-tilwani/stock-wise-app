package model.strategy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStrategyBuilder implements StrategyBuilder {
  protected Double principal;
  protected Map<String, Double> weights;
  protected LocalDate start;
  protected double commission;

  private double totalWeight;
  protected AbstractStrategyBuilder() {
    this.principal = 0.0;
    this.start = null;
    this.commission = 0.0;
    this.weights = new HashMap<>();
    this.totalWeight = 0.0;
  }
  @Override
  public StrategyBuilder addStock(String stock, Double weight) {
    if(totalWeight + weight > 100) {
      throw new IllegalArgumentException("Strategy could not be created due to invalid " +
              "weights");
    }
    this.totalWeight += weight;
    this.weights.put(stock, this.weights.getOrDefault(stock, 0.0)+weight);
    return this;
  }

  @Override
  public StrategyBuilder setCommission(Double commission) {
    this.commission = commission;
    return this;
  }

  @Override
  public StrategyBuilder setPrincipal(Double principal) {
    this.principal = principal;
    return this;
  }

  @Override
  public StrategyBuilder setStartDate(LocalDate date) {
    this.start = date;
    return this;
  }
}
