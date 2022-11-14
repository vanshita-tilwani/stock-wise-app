package controller;

import java.util.HashMap;
import java.util.Map;

import controller.commandexecutors.AllPortfolio;
import controller.commandexecutors.BuyStock;
import controller.commandexecutors.CreateCumulativePortfolio;
import controller.commandexecutors.CreateTransactionalPortfolio;
import controller.commandexecutors.EvaluateCostBasis;
import controller.commandexecutors.EvaluatePortfolioPerformance;
import controller.commandexecutors.Executor;
import controller.commandexecutors.LoadPortfolio;
import controller.commandexecutors.PortfolioComposition;
import controller.commandexecutors.PortfolioCompositionAtDate;
import controller.commandexecutors.PortfolioEvaluation;
import controller.commandexecutors.SavePortfolio;
import controller.commandexecutors.SellStock;
import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of the controller to trade with portfolios.
 */
public class PortfolioTradeController implements TradeController {

  // view responsible for input/display
  private final View view;
  // model responsible for business logic
  private final PortfolioTradeOperation model;


  /**
   * Creates a Controller object with view and model as arguments.
   *
   * @param view  The view in MVC design
   * @param model The model in MVC design.
   */
  public PortfolioTradeController(View view, PortfolioTradeOperation model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void execute() {
    // TODO : ask for data provider type or set default if time permits.
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
    commandMap.put(1, new CreateCumulativePortfolio());
    commandMap.put(2, new CreateTransactionalPortfolio());
    commandMap.put(3, new BuyStock());
    commandMap.put(4, new SellStock());
    commandMap.put(5, new AllPortfolio());
    commandMap.put(6, new PortfolioComposition());
    commandMap.put(7, new PortfolioCompositionAtDate());
    commandMap.put(8, new PortfolioEvaluation());
    commandMap.put(9, new EvaluateCostBasis());
    commandMap.put(10, new EvaluatePortfolioPerformance());
    commandMap.put(11, new SavePortfolio());
    commandMap.put(12, new LoadPortfolio());
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
    menu.append("1. Create Master Portfolio\n");
    menu.append("2. Create Transactional Portfolio\n");
    menu.append("3. Buy Stock\n");
    menu.append("4. Sell Stock\n");
    menu.append("5. Get All the Portfolio Names\n");
    menu.append("6. Get an existing Portfolio composition(end of all transactions)\n");
    menu.append("7. Gen an existing Portfolio composition at a specific date\n");
    menu.append("8. Get the evaluation of an existing Portfolio\n");
    menu.append("9. Get the cost basis of a Portfolio\n");
    menu.append("10. Get the portfolio Performance over a Period of time\n");
    menu.append("11. Save the portfolio to file\n");
    menu.append("12. Load the portfolio\n");
    menu.append("Enter the menu option you wish to choose.\n");
    menu.append("Press and enter any other key to exit the application.\n");
    return menu.toString();
  }

}
