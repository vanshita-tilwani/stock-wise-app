package model.strategy;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class Implementing the Investment strategy.
 */
public class InvestmentStrategy implements Strategy {

  protected final Double principal;
  protected final Map<String, Double> weights;
  protected final LocalDate startDate;
  protected final LocalDate endDate;
  protected final int frequency;
  protected final Double commission;

  protected InvestmentStrategy(Double principal, Map<String, Double> weights, Double commission,
                               LocalDate start, LocalDate end, int days) {
    this.principal = principal;
    this.weights = weights;
    this.commission = commission;
    this.startDate = start;
    this.endDate = end;
    this.frequency = days;
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

  /**
   * A class for the investment strategy.
   */
  public static class InvestmentStrategyBuilder extends AbstractStrategyBuilder {

    /**
     * Constructor to initialize the class variables.
     */
    public InvestmentStrategyBuilder() {
      super();
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
      if (this.totalWeight != 100.0) {
        throw new IllegalArgumentException("The strategy could not be created due to invalid"
                + " stock percentages\n");
      }
      return new InvestmentStrategy(this.principal, this.weights, this.commission, this.start,
              this.end, this.frequency);
    }
  }
}
