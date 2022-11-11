package controller.commandexecutors;

import java.util.Set;

import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import view.View;

public class AllPortfolio implements Executor {
  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    Set<String> portfolios = model.getAllTrades();
    if (portfolios.size() > 0) {
      StringBuilder sb = new StringBuilder("");
      portfolios.forEach((f) -> {
        sb.append(f + "\n");
      });
      view.display("Portfolio Names : \n" + sb);
    } else {
      view.display("The application does not contain any portfolio.\n");
    }
  }
}
