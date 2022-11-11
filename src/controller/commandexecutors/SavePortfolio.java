package controller.commandexecutors;

import controller.commandexecutors.Executor;
import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import view.View;

public class SavePortfolio extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    String portfolio = this.readTradeName(view);
    Boolean result = model.save(portfolio);
    if (result) {
      view.display("The portfolio saved to file successfully to portfolio.txt in res folder in" +
              " your current working directory!\n");
    } else {
      view.display("The save could not be completed. Please make sure the portfolio name" +
              "is entered correctly and the data source(file) exist.\n");
    }
  }
}
