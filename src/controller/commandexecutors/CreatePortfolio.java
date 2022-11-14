package controller.commandexecutors;

import java.util.Set;

import model.portfolio.Portfolio;
import model.stock.Stock;
import model.stocktradings.PortfolioTradeOperation;
import model.trade.Trade;
import view.View;

/**
 * Abstract class responsible for creating Portfolio. Marked abstract to have common
 * code between classes which creates two different type of Portfolio. Package Private
 * since serves only abstraction of re-use of code.
 */
abstract class CreatePortfolio extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // read the name of the portfolio.
      String name = this.readTradeName(view);
      // Read Trade Data
      Set<Trade<Stock>> shares = this.readTradeData(view);
      if (shares.size() > 0) {
        // asks the model to buy the portfolio if there are any valid shares
        model.create(this.createPortfolio(name, shares));
        view.display("Portfolio created successfully.\n");
      } else {
        // if there are no valid shares, inform the user
        view.display("Portfolio could not be created since all the shares in the "
                + "portfolio are Invalid.\n");
      }

    } catch (NumberFormatException exception) {
      view.display("Please make sure you input valid number of stocks/quantity of stocks.\n");
    } catch (IllegalArgumentException exception) {
      view.display(exception.getMessage());
    }
  }

  /**
   * Method responsible for reading the Portfolio Data for creation of
   * a new Portfolio in the application.
   *
   * @param view View in the MVC design responsible for reading inputs and displaying data.
   * @return Set of trades within a Portfolio.
   */
  protected abstract Set<Trade<Stock>> readTradeData(View view);

  /**
   * Method responsible for creating a Portfolio with name and trades.
   *
   * @param name   The name of the portfolio to be created.
   * @param shares The set of trades within a portfolio to be created.
   * @return A new portfolio.
   */
  protected abstract Portfolio createPortfolio(String name, Set<Trade<Stock>> shares);

}
