package controller.commandexecutors;

import java.time.LocalDate;
import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of executor responsible for getting the portfolio composition
 * at a specific date input by the user.
 */
public class PortfolioCompositionAtDate extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // read the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // read the date at which you wish you get the composition.
      LocalDate date = this.readDate(view);
      // returns the composition and displays it.
      view.display(model.get(portfolio).composition(date));
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
  }
}
