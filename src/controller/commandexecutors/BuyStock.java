package controller.commandexecutors;

import java.time.LocalDate;

import model.stock.Stock;
import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import model.trade.StockTransaction;
import model.trade.Trade;
import view.View;

public class BuyStock extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      String portfolio = this.readTradeName(view);
      String stock = this.readStockSymbol(view);
      Double shares = this.readShares(view);
      LocalDate date = this.readDateOfPurchase(view);
      model.buy(portfolio, stock, shares, date);
      view.display("The purchase was completed successfully!\n");
    }
    catch(IllegalArgumentException e) {
      view.display(e.getMessage());
    }
  }
}
