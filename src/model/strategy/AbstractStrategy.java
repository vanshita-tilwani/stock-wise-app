package model.strategy;

import java.time.LocalDate;
import java.util.Map;

public abstract class AbstractStrategy implements Strategy {

  protected final Double principal;
  protected final Map<String, Double> weights;
  protected final LocalDate startDate;
  protected final LocalDate endDate;
  protected final int frequency;
  protected final Double commission;

  protected AbstractStrategy(Double principal, Map<String, Double> weights, Double commission,
                          LocalDate start, LocalDate end, int days) {
    this.principal = principal;
    this.weights = weights;
    this.commission = commission;
    this.startDate = start;
    this.endDate = end;
    this.frequency= days;
  }

  @Override
  public Double principle() {
    return this.principal;
  }

  @Override
  public Map<String, Double> stockProportion() {
    return this.weights;
  }

  @Override
  public LocalDate startDate() {
    return this.startDate;
  }

  @Override
  public LocalDate endDate() {
    return this.endDate;
  }

  @Override
  public int frequency() {
    return this.frequency;
  }

  @Override
  public double commission() {
    return this.commission;
  }
}
