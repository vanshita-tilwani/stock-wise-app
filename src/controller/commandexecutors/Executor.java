package controller.commandexecutors;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Interface responsible for executing Commands. The different type of commands are
 * Create Portfolio, Purchase a Stock, Sell a Stock, etc. Used by Controlled to
 * abstract the code and making each Command execute a Application Feature.
 */
public interface Executor {

  /**
   * Executes the command i.e. Create Portfolio, Buy Stock, Sell Stock,
   * Save Portfolio, Load Portfolio etc.
   *
   * @param view View in the MVC design responsible for reading and displaying data.
   * @param model The model in MVC design responsible for executing Business logic.
   */
  void execute(View view, TradeOperation<Portfolio> model);
}
