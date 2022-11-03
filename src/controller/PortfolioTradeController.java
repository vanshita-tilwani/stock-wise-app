package controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.portfolio.PortfolioImpl;
import model.stock.Stock;
import model.stocktradings.TradeOperation;
import model.trade.StockTradeImpl;
import model.trade.Trade;
import view.View;

/**
 * Implementation of the controller to trade with portfolios.
 */
public class PortfolioTradeController implements TradeController {

  private final View view;
  private final TradeOperation model;

  /**
   * Creates a Controller object with view and model as arguments.
   *
   * @param view  The view in MVC design
   * @param model The model in MVC design.
   */
  public PortfolioTradeController(View view, TradeOperation model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void execute() {
    // gets the commands as per the menu options to create, retrieve,
    // evaluate, save or load the portfolios.
    Map<Integer, Runnable> commands = this.getCommands();
    while (true) {
      // read the menu option from the view.
      int menuOption = this.readMenuInput();
      // if the menu option does not exist in commands then exit the application.
      if (!commands.containsKey(menuOption)) {
        view.display("You have decided to exit the application. See you next time\n");
        break;
      }
      // execute the menu option chosen.
      commands.get(menuOption).run();
    }
  }

  /**
   * Read input from the menu options to accordingly perform actions.
   *
   * @return the menu options chosen by the user.
   */
  private int readMenuInput() {
    // Displays the menu options to the user
    String menuOptions = this.getMenuOptions();
    view.display(menuOptions);
    try {
      // read the menu option input by the user
      return Integer.parseInt(view.input());
    } catch (Exception e) {
      // if the input is illegal then returns 0
      return 0;
    }
  }

  /**
   * Returns the command map for the menu options.
   *
   * @return command map.
   */
  private Map<Integer, Runnable> getCommands() {
    // creates a <menu item, runnable> map to execute flow based on user input
    Map<Integer, Runnable> commandMap = new HashMap<>();
    commandMap.put(1, () -> this.createPortfolio());
    commandMap.put(2, () -> this.getAllPortfolios());
    commandMap.put(3, () -> this.getPortfolio());
    commandMap.put(4, () -> this.evaluatePortfolio());
    commandMap.put(5, () -> this.savePortfolio());
    commandMap.put(6, () -> this.loadPortfolio());
    return commandMap;
  }


  /**
   * Initializes the menu options for the user to choose from.
   *
   * @return the menu options in string.
   */
  private String getMenuOptions() {
    // Generates all the menu options displayed to the user.
    StringBuilder menu = new StringBuilder();
    menu.append("Main Menu :\n");
    menu.append("1. Create Portfolio\n");
    menu.append("2. Get All the Portfolio Names\n");
    menu.append("3. Get an existing Portfolio composition\n");
    menu.append("4. Get the evaluation of an existing Portfolio\n");
    menu.append("5. Save the portfolio to file\n");
    menu.append("6. Load the portfolio\n");
    menu.append("Enter the menu option you wish to choose.\n");
    menu.append("Press and enter any other key to exit the application.\n");
    return menu.toString();
  }

  /**
   * Creates the portfolio in the application with user inputs.
   */
  private void createPortfolio() {
    try {
      // Asks for the user to input portfolio name to begin with
      view.display("Enter the name of the portfolio you wish to create\n");
      String name = view.input().trim();
      if (name.isEmpty() || name == null) {
        // stop if the name entered is invalid
        view.display("You have entered an Invalid Name. Please try again\n");
      } else {
        // Asks the view to read the trade information
        Map<String, Double> stockData = view.readTrade();
        // Prepares the shares list to create portfolio
        List<Trade<Stock>> shares = this.parseInputAndGetPortfolio(stockData);
        if (shares.size() > 0) {
          // asks the model to buy the portfolio if there are any valid shares
          model.buy(new PortfolioImpl(name, shares));
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

  /**
   * Parses the stock data read by view.
   * @param stockData Stock data read by view
   * @return list of trades
   */
  private List<Trade<Stock>> parseInputAndGetPortfolio(Map<String, Double> stockData) {
    Set<String> stocks = stockData.keySet();
    List<Trade<Stock>> shares = new ArrayList<>();
    for (String stock : stocks) {
      Trade<Stock> share;
      try {
        share = new StockTradeImpl(stock, stockData.get(stock));
        shares.add(share);
      } catch (Exception e) {
        view.display(e.getMessage());
      }
    }
    return shares;
  }

  /**
   * Returns all the portfolios created by the user.
   */
  private void getAllPortfolios() {
    Set<String> portfolios = this.model.getAllTrades();
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

  /**
   * To retrieve the portfolio composition.
   */
  private void getPortfolio() {
    try {
      view.display("Enter the name of the portfolio you wish to retrieve\n");
      String portfolio = view.input();
      view.display(model.get(portfolio).toString());
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
  }

  /**
   * Evaluates the total value of a portfolio at the date input by user.
   */
  private void evaluatePortfolio() {
    try {
      view.display("Enter the name of the portfolio you wish to evaluate\n");
      String portfolio = view.input();
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

  /**
   * Saves the portfolio in the data source.
   */
  private void savePortfolio() {
    view.display("Enter the name of the portfolio you wish to save\n");
    String portfolio = view.input();
    Boolean result = model.save(portfolio);
    if (result) {
      view.display("The portfolio saved to file successfully to portfolio.txt in res folder in" +
              " your current working directory!\n");
    } else {
      view.display("The save could not be completed. Please make sure the portfolio name" +
              "is entered correctly and the data source(file) exist.\n");
    }
  }

  /**
   * Loads all the portfolio present in the data source.
   */
  private void loadPortfolio() {
    boolean result = model.load();
    if (result) {
      view.display("The load of portfolio is successfully completed from portfolio.txt " +
              "from the res folder in your current working directory!\n");
    } else {
      view.display("There were issues with portfolio load. Please make sure the file is in" +
              "expected format.\n");
    }
  }
}
