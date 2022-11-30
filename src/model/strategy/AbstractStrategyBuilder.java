package model.strategy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to abstract all the strategies.
 */
public abstract class AbstractStrategyBuilder implements StrategyBuilder {

  protected Double principal;
  protected Map<String, Double> weights;
  protected LocalDate start;
  protected double commission;
  protected LocalDate end;
  protected int frequency;

  protected double totalWeight;

  protected AbstractStrategyBuilder() {
    this.principal = 0.0;
    this.start = null;
    this.end = null;
    this.commission = 0.0;
    this.weights = new HashMap<>();
    this.totalWeight = 0.0;
    this.frequency = 1;
  }

  @Override
  public StrategyBuilder addStock(String stock, Double weight) throws IllegalArgumentException {
    if (totalWeight + weight > 100) {
      throw new IllegalArgumentException("The strategy could not be created due to invalid"
              + " stock percentages\n");
    }
    this.totalWeight += weight;
    this.weights.put(stock, this.weights.getOrDefault(stock, 0.0) + weight);
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
