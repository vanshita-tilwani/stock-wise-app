package controller.commandexecutors;

import java.time.LocalDate;

import model.stock.Stock;
import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.trade.StockTransaction;
import model.trade.Trade;
import view.View;

public class SellStock extends AbstractExecutor{

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      String portfolio = this.readTradeName(view);
      String stock = this.readStockSymbol(view);
      Double shares = this.readShares(view);
      LocalDate date = this.readDateOfSale(view);
      model.sell(portfolio, stock, shares, date);
      view.display("The sale was completed successfully!\n");
    }
    catch(IllegalArgumentException e) {
      view.display(e.getMessage());
    }
  }
}
