package controller.commandexecutors;

import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import view.View;

public interface Executor {

  void execute(View view, PortfolioTradeOperation model);
}
