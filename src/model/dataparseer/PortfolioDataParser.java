package model.dataparseer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.portfolio.Portfolio;
import model.portfolio.MasterPortfolio;
import model.stock.Stock;
import model.stockpriceprovider.WebAPIStockDataProvider;
import model.trade.CumulativeStockTrade;
import model.trade.Trade;

/**
 * Implements a file data parser for Portfolio
 */
public class PortfolioDataParser implements DataParser<Portfolio> {
  @Override
  public List<Portfolio> parse(String data) {
    List<Portfolio> portfolioList = new ArrayList<>();
    String[] portfolios = data.split("Portfolio Name : ");
    for (String portfolio : portfolios) {
      // if portfolio is empty then skip this one
      if (portfolio.isEmpty()) {
        continue;
      }
      //Parse all the stock trades in the portfolio.
      String[] lines = portfolio.split("\n");
      String portfolioName = lines[0];
      Set<Trade<Stock>> shares = new HashSet<>();
      for (int i = 1; i < lines.length; i++) {
        String line = lines[i];
        // Parses the stock value and quantity from stock trade
        String[] lineParsed = line.replace("Stock Symbol : ", "").
                replace("Quantity : ", "").split(",");
        Trade<Stock> share = new CumulativeStockTrade(lineParsed[0].trim(),
                Double.parseDouble(lineParsed[1]));
        // maintains a list of trade
        shares.add(share);
      }
      // Creates a portfolio with stock trades.
      // TODO
      portfolioList.add(new MasterPortfolio(portfolioName, shares));
    }
    return portfolioList;
  }
}
