package model.portfolio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import model.stock.Stock;
import model.stock.StockImpl;
import model.stockpriceprovider.StockDataProvider;
import model.trade.CumulativeStockTrade;
import model.trade.StockTransaction;
import model.trade.Trade;

/**
 * Implementation of Portfolio which contains an array of company stocks along with
 * the number of stocks. This type of portfolio is flexible and therefore allow
 * addition of stocks after creation.
 */
public class TransactionalPortfolio extends AbstractPortfolio {

  private final Set<Trade<Stock>> purchased;
  private final Set<Trade<Stock>> sold;


  // TODO : can probably have a PortfolioHistoryManager or PortfolioPerfManager
  /**
   * Creates a flexible Portfolio with name and collection of shares.
   * @param name name of the portfolio
   */
  public TransactionalPortfolio(String name, Set<Trade<Stock>> shares) {
    // Assuming that an empty portfolio will be created and then the stock
    // purchases will be made against that portfolio.
    super(name);
    this.purchased = shares;
    this.sold = new HashSet<>();
  }


  @Override
  public double value(LocalDate date) {
    return 0;
  }

  @Override
  public void buy(String stock, Double shares, LocalDate date) throws IllegalArgumentException {
    Trade share = new StockTransaction(stock, shares, date);
    this.purchased.add(share);
  }

  @Override
  public void sell(String stock, Double shares, LocalDate date) throws IllegalArgumentException {
    // TODO : need to maintain Portfolio History
    // TODO : check if the share does not exists, if it does not throw exception.
    double availableToSell = 0;
    Stock newStock = new StockImpl(stock);
    for (Trade<Stock> trade : purchased) {
      Stock purchasedStock = trade.get();
      if(purchasedStock.equals(newStock) && !trade.purchased().isAfter(date)) {
        availableToSell = availableToSell + trade.quantity();
      }
    }
    if(availableToSell < shares) {
      throw new IllegalArgumentException("You do not have enough shares of the stock to sell\n");
    }
    Trade share = new StockTransaction(stock, shares, date);
    this.sold.add(share);
  }

  @Override
  public double cost(LocalDate date) {
    // TODO : implement
    AtomicReference<Double> cost = new AtomicReference<>((double) 0);
    this.purchased.forEach(t -> {
      cost.set(cost.get() + t.value(t.purchased()));
    });
    return 0;
  }

  @Override
  public String toString() {
    return "";

  }

  private Map<Stock, Trade<Stock>> initialize(Set<Trade<Stock>> transactions) {
    Map<Stock, Trade<Stock>> consolidated = new HashMap<>();
    transactions.forEach((t) -> {
      Stock key = t.get();
      Double value = t.quantity();
      if(consolidated.containsKey(key)) {
        consolidated.get(key).buy(value);
      }
      else {
        consolidated.put(key, new CumulativeStockTrade(key.name(), value));
      }
    });
    return consolidated;
  }


}
