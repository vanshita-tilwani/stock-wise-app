import java.io.IOException;
import controller.TradeController;
import controller.PortfolioTradeController;
import datarepo.DataRepository;
import datarepo.FileRepository;
import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
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
    DataRepository<Portfolio> repository = new FileRepository<>("res/portfolio.txt");
    TradeOperation model = new PortfolioTradeOperation(repository);
    TradeController controller = new PortfolioTradeController(view, model);
    controller.execute();
  }

}
