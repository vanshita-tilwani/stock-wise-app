package controller.commandexecutors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.portfolio.SimulatedPortfolio;
import model.portfolio.Portfolio;
import model.stock.Stock;
import model.trade.SimulatedStockTrade;
import model.trade.Trade;
import view.View;

/**
 * Responsible for creating SimulatedPortfolio which is used for planning/analyzing the set
 * of stock combinations for trading. SimulatedPortfolio is only used for planning/analysis
 * and does not support trading of stocks (i.e. purchasing more stocks and adding it in the
 * portfolio, selling stocks from the portfolio).
 */
public class CreateSimulatedPortfolioCommand extends CreatePortfolioCommand {

  @Override
  protected Set<Trade<Stock>> readTradeData(View view) {
    Map<String, Trade<Stock>> stockData = new HashMap<>();
    // reads number of trades that the portfolio is composed of.
    int stocks = this.readNumberOfStocks(view);
    for (int i = 0; i < stocks; i++) {
      // reads the stock symbol
      String stock = this.readStockSymbol(view);
      // reads the number of shares.
      Double shares = this.readShares(view);
      if (stockData.containsKey(stock)) {
        // if stock already exists, then consolidate the amount of shares.
        stockData.get(stock).buy(shares);
      } else {
        try {
          // if stock does not already exist, add.
          stockData.put(stock, new SimulatedStockTrade(stock, shares));
        } catch (IllegalArgumentException e) {
          view.display(e.getMessage());
        }
      }
    }
    // parse the map to set of trades and return the resultant
    return new HashSet<>(stockData.values());
  }

  @Override
  protected Portfolio createPortfolio(String name, Set<Trade<Stock>> shares) {
    // creates a master/aggregated type of portfolio used for analysis purposes.
    return new SimulatedPortfolio(name, shares);
  }

  @Override
  protected void displayPortfolioMessage(View view) {
    view.display("NOTE : This type of portfolio is only for planning/analyzing trading" +
            " of stocks in a Portfolio.\nIt does not support buying/sale of stocks and" +
            " other features involving purchase/sale of stock trades.\n");
  }
}
