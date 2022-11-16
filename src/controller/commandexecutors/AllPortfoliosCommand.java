package controller.commandexecutors;

import java.util.Set;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of Executor responsible for executing command to show all the available
 * Trades Performed in a given session in the Application by a User.
 */
public class AllPortfoliosCommand implements Executor {

  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
    // Gets all the trade names in the application.
    Set<String> portfolios = model.all();
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
