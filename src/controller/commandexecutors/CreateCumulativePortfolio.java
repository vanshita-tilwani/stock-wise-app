package controller.commandexecutors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import model.portfolio.MasterPortfolio;
import model.portfolio.Portfolio;
import model.stock.Stock;
import model.stockpriceprovider.StockDataProvider;
import model.trade.CumulativeStockTrade;
import model.trade.Trade;
import view.View;

public class CreateCumulativePortfolio extends CreatePortfolio {

  @Override
  protected Set<Trade<Stock>> readTradeData(View view) {
    Map<String, Double> stockData = new HashMap<>();
    int stocks = this.readNumberOfStocks(view);
    for (int i = 0; i < stocks; i++) {
      String stock = this.readStockSymbol(view);
      Double shares = this.readShares(view);
      // enter the trade information in the trade list
      if (stockData.containsKey(stock)) {
        // if stock already exists, then consolidate the amount of shares.
        stockData.put(stock, stockData.get(stock) + shares);
      } else {
        stockData.put(stock, shares);
      }
    }
    return this.parseInputAndGetPortfolio(view, stockData);
  }

  @Override
  protected Portfolio createPortfolio(String name, Set<Trade<Stock>> shares) {
    return new MasterPortfolio(name, shares);
  }

  /**
   * Parses the stock data read by view.
   * @param stockData Stock data read by view
   * @return list of trades
   */
  private Set<Trade<Stock>> parseInputAndGetPortfolio(View view, Map<String, Double> stockData) {
    Set<String> stocks = stockData.keySet();
    Set<Trade<Stock>> shares = new HashSet<>();
    for (String stock : stocks) {
      Trade<Stock> share;
      try {
        share = new CumulativeStockTrade(stock, stockData.get(stock));
        shares.add(share);
      } catch (Exception e) {
        view.display(e.getMessage());
      }
    }
    return shares;
  }
}
