package controller.commandexecutors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of the executor responsible for getting the portfolio
 * evaluation (total value) at a date specified by the user.
 */
public class PortfolioEvaluation extends AbstractExecutor {
  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // reads the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // reads the date of evaluation for the portfolio.
      LocalDate date = this.readDate(view);
      // gets the total value of portfolio and displays it.
      view.display("The value of portfolio is $" + model.value(date, portfolio) + "\n");
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    } catch (DateTimeParseException e) {
      view.display("Please enter the Date in YYYY-MM-DD format and try again.\n");
    }
  }
}
