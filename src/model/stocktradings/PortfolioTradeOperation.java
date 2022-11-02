package model.stocktradings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datarepo.*;
import model.portfolio.Portfolio;
import model.portfolio.PortfolioImpl;
import model.stock.Stock;
import model.trade.StockTradeImpl;
import model.trade.Trade;

public class PortfolioTradeOperation implements TradeOperation<Portfolio> {

  private Map<String, Portfolio> portfolios;
  private final DataRepository<Portfolio> repository;

  public PortfolioTradeOperation(DataRepository<Portfolio> repository) {
    portfolios = new HashMap<>();
    this.repository = repository;
  }

  @Override
  public void buy(Portfolio portfolio) throws IllegalArgumentException {
    if (this.portfolios.containsKey(portfolio.name())) {
      throw new IllegalArgumentException("The portfolio with same name exists." +
              "It cannot be changed after creation.\n");
    }
    this.portfolios.put(portfolio.name(), portfolio);
  }

  @Override
  public double value(LocalDate date, String portfolio) throws IllegalArgumentException {
    LocalDate now = LocalDate.now();
    if (!this.portfolios.containsKey(portfolio)) {
      throw new IllegalArgumentException("The portfolio with name provided does not exist.\n");
    }
    if (date.isAfter(now)) {
      throw new IllegalArgumentException("The entered date is in future.\n");
    }
    Portfolio trade = this.portfolios.get(portfolio);
    return trade.value(date);
  }

  @Override
  public Portfolio get(String trade) throws IllegalArgumentException {
    if (!this.portfolios.containsKey(trade)) {
      throw new IllegalArgumentException("The portfolio with name provided does not exist.\n");
    }
    return this.portfolios.get(trade);
  }

  @Override
  public Set<String> getAllTrades() {
    return this.portfolios.keySet();
  }


  @Override
  public boolean save(String portfolio) throws IllegalArgumentException {
    try {
      if (!this.portfolios.containsKey(portfolio)) {
        throw new IllegalArgumentException("The portfolio with name : " + portfolio +
                " does not exist.");
      }
      repository.save(this.portfolios.get(portfolio));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean load() {
    try {
      List<Portfolio> portfolios = this.parse(repository.read());
      for (Portfolio portfolio : portfolios) {
        if (!this.portfolios.containsKey(portfolio.name())) {
          this.buy(portfolio);
        }
      }
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  private List<Portfolio> parse(String data) {
    // TODO parse
    List<Portfolio> portfolioList = new ArrayList<>();
    String[] portfolios = data.split("Portfolio Name : ");
    for (String portfolio : portfolios) {
      if (portfolio.isEmpty()) {
        continue;
      }
      String[] lines = portfolio.split("\n");
      String portfolioName = lines[0];
      List<Trade<Stock>> shares = new ArrayList<>();
      for (int i = 1; i < lines.length; i++) {
        String line = lines[i];
        String[] lineParsed = line.replace("Stock Symbol : ", "").
                replace("Quantity : ", "").split(",");
        Trade<Stock> share = new StockTradeImpl(lineParsed[0].trim(), Double.parseDouble(lineParsed[1]));
        shares.add(share);
      }
      portfolioList.add(new PortfolioImpl(portfolioName, shares));
    }
    return portfolioList;
  }
}
