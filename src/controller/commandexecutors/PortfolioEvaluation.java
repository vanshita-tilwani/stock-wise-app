package controller.commandexecutors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import controller.commandexecutors.Executor;
import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import view.View;

public class PortfolioEvaluation extends AbstractExecutor {
  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      String portfolio = this.readTradeName(view);
      view.display("Enter the date at which you wish to get the evaluation(in YYYY-MM-DD " +
              "format)\n");
      LocalDate date = LocalDate.parse(view.input());
      view.display("The value of portfolio is " + model.value(date, portfolio) + "\n");
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    } catch (DateTimeParseException e) {
      view.display("Please enter the Date in YYYY-MM-DD format and try again.\n");
    }
  }
}
