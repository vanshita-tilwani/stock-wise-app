import java.io.IOException;

import controller.TradeController;
import controller.PortfolioTradeController;
import model.datarepo.DataRepository;
import model.datarepo.FileRepository;
import model.stocktradings.PortfolioTradeOperation;

import view.TextualView;
import view.View;

/**
 * Client code that runs the program.
 */
public class ProgramRunner {

  /**
   * A main method to run this program.
   *
   * @param args arguments to run the method.
   */

  public static void main(String[] args) throws IOException {
    View view = new TextualView(System.in, System.out);
    DataRepository fileRepo = new FileRepository("res/portfolio.txt");
    PortfolioTradeOperation model = new PortfolioTradeOperation(fileRepo);
    TradeController controller = new PortfolioTradeController(view, model);
    controller.execute();
  }

}
