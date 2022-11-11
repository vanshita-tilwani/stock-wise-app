package controller.commandexecutors;

import controller.commandexecutors.Executor;
import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import view.View;

public class PortfolioComposition extends AbstractExecutor {
  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      String portfolio = this.readTradeName(view);
      view.display(model.get(portfolio).toString());
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
  }
}
