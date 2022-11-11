package model.stocktradings;

import java.time.LocalDate;

import model.portfolio.Portfolio;

public interface PortfolioTradeOperation extends TradeOperation<Portfolio> {

  void buy(String trade, String stock, Double shares, LocalDate date);

  void sell(String trade, String stock, Double shares, LocalDate date);

}
