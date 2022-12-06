package model.strategy;

import java.time.LocalDate;
import java.util.Map;
import model.portfolio.Portfolio;
import model.stockpriceprovider.StockDataProvider;
import model.stockpriceprovider.StockDataProviderFactory;
import model.trade.TransactionalStockTrade;

/**
 * Class Implementing the Investment strategy.
 */
public class InvestmentStrategy implements Strategy {

  protected final String name;
  protected final Double principal;
  protected final Map<String, Double> weights;
  protected final LocalDate startDate;
  protected final LocalDate endDate;
  protected final int frequency;
  protected final Double commission;

  /**
   * Initializes a Strategy with name, principal amount, weights for each stock, start date and
   * end date for the strategy, recurring frequency and commission for the strategy.
   *
   * @param name       name of the strategy.
   * @param principal  principal amount of the strategy.
   * @param weights    stock weights of the strategy.
   * @param commission commission for the strategy.
   * @param start      start date of the strategy.
   * @param end        end date of the strategy.
   * @param days       frequency of the strategy.
   */
  protected InvestmentStrategy(String name, Double principal, Map<String, Double> weights,
                               Double commission, LocalDate start, LocalDate end, int days) {
    this.name = name;
    this.principal = principal;
    this.weights = weights;
    this.commission = commission;
    this.startDate = start;
    this.endDate = end;
    this.frequency = days;
  }


  @Override
  public String name() {
    return this.name;
  }

  @Override
  public void apply(Portfolio portfolio) {
    buyPeriodic(portfolio);
  }

  /** To make a purchase in the portfolio at the specified date.
   *
   * @param portfolio portfolio for which stock purchase needs to be made.
   * @param principal the principal value for the purchase.
   * @param stockData the stocks that need to be bought.
   * @param date the date of purchase.
   * @param commission the commission fee for the purchase.
   */
  private void buyOnce(Portfolio portfolio, Double principal, Map<String, Double> stockData, LocalDate date,
                       Double commission) {
    for (Map.Entry<String, Double> entry : stockData.entrySet()) {
      var share = new TransactionalStockTrade(entry.getKey(), principal, entry.getValue(),
              date, commission);
      portfolio.buy(entry.getKey(),share.quantity(),date, commission);
    }
  }

  /** To make periodic purchase in the portfolio for the specified range.
   *
   * @param portfolio portfolio for which stock purchase needs to be made.
   */
  private void buyPeriodic(Portfolio portfolio) {
    LocalDate currDate = this.startDate;
    var endDate = this.endDate;
    if (endDate == null) {
      endDate = LocalDate.now();
    }
    while (true) {
      // TODO : handle purchases on holiday
      currDate = nextAvailableDate(currDate);
      if (currDate.isAfter(endDate) || currDate.isAfter(LocalDate.now())) {
        break;
      }
      buyOnce(portfolio,principal, this.weights, currDate, commission);
      currDate = currDate.plusDays(frequency);
    }
  }

  /**
   * Get the next available date
   *
   * @param tradeDate the trade date.
   * @return next available date.
   */
  private LocalDate nextAvailableDate(LocalDate tradeDate) {
    StockDataProvider stockDataProvider = StockDataProviderFactory.getDataProvider();
    while (!stockDataProvider.isAvailable(tradeDate) && !tradeDate.isAfter(LocalDate.now())) {
      tradeDate = tradeDate.plusDays(1);
    }
    return tradeDate;
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
      return new InvestmentStrategy(this.name, this.principal, this.weights, this.commission,
              this.start, this.end, this.frequency);
    }
  }
}
