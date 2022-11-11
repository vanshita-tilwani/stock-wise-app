package controller.commandexecutors;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import model.portfolio.Portfolio;
import model.portfolio.TransactionalPortfolio;
import model.stock.Stock;
import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import model.trade.StockTransaction;
import model.trade.Trade;
import view.View;

public class CreateTransactionalPortfolio extends CreatePortfolio {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // Asks for the user to input portfolio name to begin with
      view.display("Enter the name of the portfolio you wish to create\n");
      String name = view.input().trim();
      if (name.isEmpty() || name == null) {
        // stop if the name entered is invalid
        view.display("You have entered an Invalid Name. Please try again\n");
      } else {
        // Read Trade Data
        Set<Trade<Stock>> shares = this.readTradeData(view);
        if (shares.size() > 0) {
          // asks the model to buy the portfolio if there are any valid shares
          model.create(new TransactionalPortfolio(name, shares));
          view.display("Portfolio created successfully.\n");
        } else {
          // if there are no valid shares, inform the user
          view.display("Portfolio could not be created since all the shares in the " +
                  "portfolio are Invalid.\n");
        }
      }
    } catch (NumberFormatException exception) {
      view.display("Please make sure you input valid number of stocks/quantity of stocks.\n");
    } catch (IllegalArgumentException exception) {
      view.display(exception.getMessage());
    }
  }

  protected Set<Trade<Stock>> readTradeData(View view) {
    Set<Trade<Stock>> trades = new HashSet<>();
    int stocks = this.readNumberOfStocks(view);
    for (int i = 0; i < stocks; i++) {
      // read the stock in a trade
      String stock = this.readStockSymbol(view);
      Double shares = this.readShares(view);
      LocalDate date = this.readDateOfPurchase(view);
      // enter the trade information in the trade list
      Trade<Stock> transaction = new StockTransaction(stock, shares, date);
      trades.add(transaction);
    }
    return trades;
  }

  protected Portfolio createPortfolio(String name, Set<Trade<Stock>> shares) {
    return new TransactionalPortfolio(name, shares);
  }

}
