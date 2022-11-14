package controller.commandexecutors;

import java.util.Set;

import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of executor responsible for getting all the trade name
 * in the application.
 */
public class AllPortfolio implements Executor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    // Gets all the trade names in the application.
    Set<String> portfolios = model.getAllTrades();
    if (portfolios.size() > 0) {
      StringBuilder sb = new StringBuilder("");
      // appends the trade name in the string.
      portfolios.forEach((f) -> {
        sb.append(f + "\n");
      });
      // displays all the trade names.
      view.display("Portfolio Names : \n" + sb);
    } else {
      // if no trade exists in the application.
      view.display("The application does not contain any portfolio.\n");
    }
  }
}
