package controller.commandexecutors;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.Port;

import model.portfolio.MasterPortfolio;
import model.portfolio.Portfolio;
import model.stock.Stock;
import model.stockpriceprovider.StockDataProvider;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import model.trade.Trade;
import view.View;

public abstract class CreatePortfolio extends AbstractExecutor {

  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // Asks for the user to input portfolio name to begin with
      view.display("Enter the name of the portfolio you wish to create\n");
      String name = view.input().trim();
      if (name.isEmpty() || name == null) {
        // stop if the name entered is invalid
        view.display("You have entered an Invalid Name. Please try again\n");
      } else {
        // Read Trade Data
        Set<Trade<Stock>> shares = this.readTradeData(view);
        if (shares.size() > 0) {
          // asks the model to buy the portfolio if there are any valid shares
          model.create(this.createPortfolio(name, shares));
          view.display("Portfolio created successfully.\n");
        } else {
          // if there are no valid shares, inform the user
          view.display("Portfolio could not be created since all the shares in the " +
                  "portfolio are Invalid.\n");
        }
      }
    } catch (NumberFormatException exception) {
      view.display("Please make sure you input valid number of stocks/quantity of stocks.\n");
    } catch (IllegalArgumentException exception) {
      view.display(exception.getMessage());
    }
  }



  protected abstract Set<Trade<Stock>> readTradeData(View view);

  protected abstract Portfolio createPortfolio(String name, Set<Trade<Stock>> shares);

}
