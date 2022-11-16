package controller;

import java.util.HashMap;
import java.util.Map;

import controller.commandexecutors.AllPortfoliosCommand;
import controller.commandexecutors.BuyStockCommand;
import controller.commandexecutors.CreateSimulatedPortfolioCommand;
import controller.commandexecutors.CreateTransactionalPortfolioCommand;
import controller.commandexecutors.EvaluateCostBasisCommand;
import controller.commandexecutors.EvaluatePortfolioPerformanceCommand;
import controller.commandexecutors.Executor;
import controller.commandexecutors.LoadPortfolioCommand;
import controller.commandexecutors.PortfolioCompositionCommand;
import controller.commandexecutors.PortfolioCompositionAtDateCommand;
import controller.commandexecutors.PortfolioEvaluationCommand;
import controller.commandexecutors.SavePortfolioCommand;
import controller.commandexecutors.SellStockCommand;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Portfolio Controller is the Controller layer in the design which can be consumed
 * by the Client to trade with stocks within a Portfolio. This controller is
 * responsible for starting the application and controlling the flow of the
 * application and helps the user to navigate the features available to consume in
 * the application.
 */
public class PortfolioTradeController implements TradeController {

  // view responsible for input/display
  private final View view;
  // model responsible for business logic
  private final TradeOperation model;

  /**
   * Instantiates the Controller Object with the view responsible for
   * reading/displaying input data and model responsible for executing
   * business logic of the application.
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
    Map<Integer, Executor> commands = this.getCommands();
    while (true) {

      // read the menu option from the view.
      int menuOption = this.readMenuInput();
      // if the menu option does not exist in commands then exit the application.
      if (!commands.containsKey(menuOption)) {
        view.display("You have decided to exit the application. See you next time\n");
        break;
      }
      // execute the menu option chosen.
      commands.get(menuOption).execute(view, model);
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
  private Map<Integer, Executor> getCommands() {
    // creates a <menu item, runnable> map to execute flow based on user input
    Map<Integer, Executor> commandMap = new HashMap<>();
    commandMap.put(1, new CreateSimulatedPortfolioCommand());
    commandMap.put(2, new CreateTransactionalPortfolioCommand());
    commandMap.put(3, new BuyStockCommand());
    commandMap.put(4, new SellStockCommand());
    commandMap.put(5, new AllPortfoliosCommand());
    commandMap.put(6, new PortfolioCompositionCommand());
    commandMap.put(7, new PortfolioCompositionAtDateCommand());
    commandMap.put(8, new PortfolioEvaluationCommand());
    commandMap.put(9, new EvaluateCostBasisCommand());
    commandMap.put(10, new EvaluatePortfolioPerformanceCommand());
    commandMap.put(11, new SavePortfolioCommand());
    commandMap.put(12, new LoadPortfolioCommand());
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
    menu.append("1.  Create a new Simulated Portfolio\n");
    menu.append("2.  Create a new Transactional Portfolio\n");
    menu.append("3.  Purchase a Stock and Add it to Transactional Portfolio\n");
    menu.append("4.  Sell a Stock from the Transactional Portfolio\n");
    menu.append("5.  Get all the available Portfolios in the Application\n");
    menu.append("6.  Get final composition for an Existing Portfolio \n");
    menu.append("7.  Get composition for an Existing Portfolio at a specific date\n");
    menu.append("8.  Get the total value on an Existing Portfolio\n");
    menu.append("9.  Get the cost basis of an Existing Transactional Portfolio\n");
    menu.append("10. Get the performance of an Existing Portfolio over a period of time\n");
    menu.append("11. Save an Existing Portfolio to file\n");
    menu.append("12. Load portfolios to the Application\n");
    menu.append("Enter the menu option you wish to choose.\n");
    menu.append("Press and enter any other key to exit the application.\n");
    return menu.toString();
  }

}
