import java.io.IOException;
import controller.TradeController;
import controller.PortfolioTradeController;
import model.datarepo.DataRepository;
import model.datarepo.FileRepository;
import model.dataparseer.DataParser;
import model.dataparseer.PortfolioDataParser;
import model.portfolio.Portfolio;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import model.stocktradings.PortfolioTrader;
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
    DataParser<Portfolio> parser = new PortfolioDataParser();
    PortfolioTradeOperation model = new PortfolioTrader(repository,parser);
    TradeController controller = new PortfolioTradeController(view, model);
    controller.execute();
  }

}
