package model.stocktradings;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import model.portfolio.Portfolio;

public class PortfolioTrade implements Trade<Portfolio> {

  private Map<String, Portfolio> portfolios;
  public PortfolioTrade() {
    portfolios = new HashMap<>();
  }

  @Override
  public void buy(Portfolio portfolio) {
    this.portfolios.put(portfolio.name(), portfolio);
  }

  @Override
  public double value(LocalDate date, String portfolio) {
    Portfolio trade = this.portfolios.get(portfolio);
    return trade.value(date);
  }

  @Override
  public Portfolio get(String portfolio) {
    return portfolios.get(portfolios);
  }
}
