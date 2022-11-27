package controller.commandexecutors;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of Command Executor responsible for Loading all the portfolios
 * from the data source to the application. The model can be passed the data source that is
 * to be considered for Load. This operation supports loading of portfolios from the File.
 * If the load failed due to IO exception or Parse exception, the user will be displayed
 * an error message.
 */
public class LoadPortfolioCommand extends AbstractExecutor {

  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
    // loads the portfolios from data source to application.
    try {
      model.load();
      view.display("The load of portfolio is successfully completed from portfolio.txt "
              + "from the res folder in your current working directory!\n");
    }
    catch (Exception e){
      view.display("There were issues with portfolio load. Please make sure the file is in"
              + "expected format.\n");
    }
  }
}
