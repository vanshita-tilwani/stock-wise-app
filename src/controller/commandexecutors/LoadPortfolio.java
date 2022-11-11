package controller.commandexecutors;

import controller.commandexecutors.Executor;
import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import view.View;

public class LoadPortfolio extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    boolean result = model.load();
    if (result) {
      view.display("The load of portfolio is successfully completed from portfolio.txt " +
              "from the res folder in your current working directory!\n");
    } else {
      view.display("There were issues with portfolio load. Please make sure the file is in" +
              "expected format.\n");
    }
  }
}
