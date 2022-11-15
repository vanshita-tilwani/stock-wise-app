package controller.commandexecutors;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import model.portfolio.Portfolio;
import model.portfolio.TransactionalPortfolio;
import model.stock.Stock;
import model.trade.TransactionalStockTrade;
import model.trade.Trade;
import view.View;

/**
 * Implementation of executor responsible for creating a portfolio of
 * type transactional in the application.
 */
public class CreateTransactionalPortfolioCommand extends CreatePortfolioCommand {

  @Override
  protected Set<Trade<Stock>> readTradeData(View view) {
    Set<Trade<Stock>> trades = new HashSet<>();
    // reads the number of trades the portfolio is composed of.
    int stocks = this.readNumberOfStocks(view);
    for (int i = 0; i < stocks; i++) {
      // reads the stock symbol
      String stock = this.readStockSymbol(view);
      // reads the number of shares.
      Double shares = this.readShares(view);
      // reads the date of purchase of the trade.
      LocalDate date = this.readDateOfPurchase(view);
      // reads the commission fee for the trade.
      Double commission = this.readCommissionFee(view);
      // enter the trade information in the trade list
      Trade<Stock> transaction = new TransactionalStockTrade(stock, shares, date, commission);
      trades.add(transaction);
    }
    return trades;
  }

  @Override
  protected Portfolio createPortfolio(String name, Set<Trade<Stock>> shares) {
    // creates a transactional type of portfolio.
    return new TransactionalPortfolio(name, shares);
  }

}
