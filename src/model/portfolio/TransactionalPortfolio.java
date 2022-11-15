package model.portfolio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import model.stock.Stock;
import model.stock.StockImpl;
import model.trade.SimulatedStockTrade;
import model.trade.TransactionalStockTrade;
import model.trade.Trade;

/**
 * Implementation of Portfolio which contains an array of company stocks along with
 * the number of stocks. This type of portfolio is flexible and therefore allow
 * addition of stocks after creation.
 */
public class TransactionalPortfolio extends AbstractPortfolio {

  private final Set<Trade<Stock>> purchased;
  private final Set<Trade<Stock>> sold;

  /**
   * Creates a transaction Portfolio with name and collection of purchases.
   *
   * @param name   name of the portfolio
   * @param shares the shares of stocks purchased.
   */
  public TransactionalPortfolio(String name, Set<Trade<Stock>> shares) {
    // Assuming that an empty portfolio will be created and then the stock
    // purchases will be made against that portfolio.
    super(name);
    this.purchased = shares;
    this.sold = new HashSet<>();
  }

  /**
   * Creates a transaction Portfolio with name and collection of purchases and sale.
   *
   * @param name      the name of the portfolio.
   * @param purchased the shares of stocks purchased.
   * @param sold      the shares of stocks sold.
   */
  public TransactionalPortfolio(String name, Set<Trade<Stock>> purchased, Set<Trade<Stock>> sold) {
    // Assuming that an empty portfolio will be created and then the stock
    // purchases will be made against that portfolio.
    super(name);
    this.purchased = purchased;
    this.sold = sold;
  }

  @Override
  public void add(String stock, Double shares, LocalDate date, Double commission)
          throws IllegalArgumentException {
    Trade share = new TransactionalStockTrade(stock, shares, date, commission);
    this.purchased.add(share);
  }

  @Override
  public void sell(String stock, Double shares, LocalDate date, Double commission)
          throws IllegalArgumentException {
    // get all available shares with the
    Predicate<Trade<Stock>> predicate = x -> x.get().equals(new StockImpl(stock))
            && !x.tradeDate().isAfter(date);
    double availableShares = this.countIf(this.purchased, predicate);
    double sold = this.countIf(this.sold, predicate);
    double availableToSell = availableShares - sold;
    if (availableToSell < shares) {
      throw new IllegalArgumentException("You do not have enough shares of the stock to sell\n");
    }
    Trade share = new TransactionalStockTrade(stock, shares, date, commission);
    this.sold.add(share);
  }


  @Override
  public double costBasis(LocalDate date) throws UnsupportedOperationException {
    double cost = 0;
    for (Trade<Stock> trade : purchased) {
      cost = cost + trade.value(trade.tradeDate()) + trade.commission();
    }
    return cost;
  }

  @Override
  public double value(LocalDate date) {
    Predicate<Trade<Stock>> predicate = x -> !x.tradeDate().isAfter(date);
    Set<Trade<Stock>> cumulative = this.consolidateIf(predicate);
    double value = this.evaluateValue(cumulative, date);
    return value;
  }

  @Override
  public String composition() {
    Predicate<Trade<Stock>> predicate = x -> true;
    Set<Trade<Stock>> shares = this.consolidateIf(predicate);
    return getComposition(shares);
  }

  @Override
  public String composition(LocalDate date) {
    Predicate<Trade<Stock>> predicate = x -> !x.tradeDate().isAfter(date);
    Set<Trade<Stock>> shares = this.consolidateIf(predicate);
    return getComposition(shares);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder().append("TYPE : TRANSACTIONAL\n");
    sb.append(super.toString());
    sb.append("PURCHASES : \n");
    for (Trade<Stock> trade : purchased) {
      sb.append(trade.toString());
    }
    sb.append("SALE : \n");
    for (Trade<Stock> trade : sold) {
      sb.append(trade.toString());
    }
    sb.append("------ END ------\n");
    return sb.toString();
  }

  /**
   * Consolidate the purchases and sale of the portfolio depending on the predicate.
   *
   * @param predicate the filter used to consolidate all transactions.
   * @return the consolidated set of trades.
   */
  private Set<Trade<Stock>> consolidateIf(Predicate<Trade<Stock>> predicate) {
    HashMap<Stock, Trade<Stock>> consolidated = new HashMap<>();
    for (Trade<Stock> trade : this.purchased) {
      if (predicate.test(trade)) {
        if (consolidated.containsKey(trade.get())) {
          consolidated.get(trade.get()).buy(trade.quantity());
        } else {
          consolidated.put(trade.get(),
                  new SimulatedStockTrade(trade.get().name(), trade.quantity()));
        }
      }
    }
    for (Trade<Stock> trade : this.sold) {
      if (predicate.test(trade)) {
        Stock stock = trade.get();
        consolidated.get(stock).sell(trade.quantity());
      }
    }
    return new HashSet<>(consolidated.values());
  }

  /**
   * Count the total number of shares using the filter.
   *
   * @param shares    the trade to be counted.
   * @param predicate the filter used to count the number of shares in each trade.
   * @return the total number of shares.
   */
  private double countIf(Set<Trade<Stock>> shares, Predicate<Trade<Stock>> predicate) {
    double available = 0;
    for (Trade<Stock> trade : shares) {
      if (predicate.test(trade)) {
        available = available + trade.quantity();
      }
    }
    return available;
  }


}
