package controller.commandexecutors;

import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * An interface for executing various commands by the controller in order
 * to abstract the code.
 */
public interface Executor {

  /**
   * Executes the command i.e. Create Portfolio, Buy Stock, Sell Stock,
   * Save Portfolio, Load Portfolio etc.
   *
   * @param view View in the MVC design responsible for reading and displaying data.
   * @param model The model in MVC design responsible for executing Business logic.
   */
  void execute(View view, PortfolioTradeOperation model);
}
