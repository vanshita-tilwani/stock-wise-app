package controller;

import java.security.spec.ECField;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.portfolio.PortfolioImpl;
import model.stock.Stock;
import model.stockpriceprovider.StockProviderType;
import model.stocktradings.TradeOperation;
import model.trade.Trade;
import model.trade.StockTradeImpl;
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
      if(!commands.containsKey(menuOption)) {
        view.display("You have decided to exit the application. See you next time\n");
        break;
      }
      // execute the menu option chosen.
      commands.get(menuOption).run();
    }
  }

  private StockProviderType getPriceProvider() {
    view.display("Do you wish to choose the Price Provider (y/n)?\n");
    String input = view.input();
    if(input.equals("Y") || input.equals("y")) {
      view.display("Choose a Provider from below : \n");
      view.display("1. Mock Provider\n");
      view.display("2. API Provider\n");
      try {
        int option = Integer.parseInt(view.input());
        return StockProviderType.from(option);
      }
      catch(Exception e) {
        return StockProviderType.API;
      }
    }
    else {
      return StockProviderType.API;
    }
  }

  /**
   * Read input from the menu options to accordingly perform actions.
   * @return the menu options chosen by the user.
   */
  private int readMenuInput() {
    String menuOptions = this.getMenuOptions();
    view.display(menuOptions);
    try {
      return Integer.parseInt(view.input());
    }
    catch (Exception e) {
      return 0;
    }
  }

  /**
   * Returns the command map for the menu options.
   * @return command map.
   */
  private Map<Integer, Runnable> getCommands() {
    Map<Integer, Runnable> commandMap = new HashMap<>();
    commandMap.put(1, () ->  this.createPortfolio());
    commandMap.put(2, () ->  this.getAllPortfolios());
    commandMap.put(3, () ->  this.getPortfolio());
    commandMap.put(4, () ->  this.evaluatePortfolio());
    commandMap.put(5, () ->  this.savePortfolio());
    commandMap.put(6, () ->  this.loadPortfolio());
    return commandMap;
  }


  /**
   * Initializes the menu options for the user to choose from.
   * @return the menu options in string.
   */
  private String getMenuOptions() {
    StringBuilder menu = new StringBuilder();
    menu.append("Main Menu :\n");
    menu.append("1. Create Portfolio\n");
    menu.append("2. Get All the Portfolio Names\n");
    menu.append("3. Get an existing Portfolio composition\n");
    menu.append("4. Get the evaluation of an existing Portfolio\n");
    menu.append("5. Save the portfolio to file\n");
    menu.append("6. Load the portfolio\n");
    menu.append("Enter the menu option you wish to choose.\nPlease any other key to exit the " +
            "application.\n");
    return menu.toString();
  }

  /**
   * Creates the portfolio in the application with user inputs.
   */
  private void createPortfolio() {
    try {
      view.display("Enter the name of the portfolio you wish to create\n");
      String name = view.input();
      Map<String, Double> stockData = view.read();
      Set<String> stocks = stockData.keySet();
      List<Trade<Stock>> shares = new ArrayList<>();
      for (String stock : stocks) {
        Trade<Stock> share;
        try{
          share = new StockTradeImpl(stock, stockData.get(stock));
          shares.add(share);
        }
        catch(Exception e) {
          view.display(e.getMessage());
        }
      }

      if(shares.size() > 0) {
        model.buy(new PortfolioImpl(name, shares));
        view.display("Portfolio created successfully.\n");
      }
      else {
        view.display("Portfolio could not be created since all the shares in the " +
                "portfolio are Invalid.\n");
      }
    }
    catch(IllegalArgumentException exception) {
      view.display(exception.getMessage());
    }

  }

  private void getAllPortfolios() {
    Set<String> portfolios = this.model.getAllTrades();
    if(portfolios.size() > 0) {
      StringBuilder sb = new StringBuilder("");
      portfolios.forEach((f) -> {
        sb.append(f + "\n");
      });
      view.display("Portfolio Names : \n" + sb);
    }
    else {
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
    }
    catch(IllegalArgumentException e) {
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
      view.display("Enter the date at which you wish to get the evaluation(in YYYY-MM-DD format)\n");
      LocalDate date = LocalDate.parse(view.input());
      view.display(model.value(date, portfolio) + " ");
    }
    catch(IllegalArgumentException e) {
      view.display(e.getMessage());
    }

  }

  /**
   * Saves the portfolio in the data source.
   */
  private void savePortfolio() {
    view.display("Enter the name of the portfolio you wish to save\n");
    String portfolio = view.input();
    Boolean result = model.save(portfolio);
    if(result) {
      view.display("The portfolio saved successfully!\n");
    }
    else {
      view.display("The save could not be completed. Please make sure the portfolio name" +
              "is entered correctly and the data source(file) exist.\n");
    }
  }

  /**
   * Loads all the portfolio present in the data source.
   */
  private void loadPortfolio() {
    boolean result = model.load();
    if(result) {
      view.display("The load of portfolio is successfully completed!\n");
    }
    else {
      view.display("There were issues with portfolio load. Please make sure the file is in" +
              "expected format.\n");
    }
  }
}
