import java.io.IOException;
import java.time.LocalDate;

import controller.TradeController;
import controller.PortfolioTradeController;
import datarepo.DataRepository;
import datarepo.FileRepository;
import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import model.stocktradings.PortfolioTradeOperation;
import view.TextualView;
import view.View;

public class ProgramRunner {
  public static void main(String[] args) throws IOException {
    View view = new TextualView(System.in, System.out);
    DataRepository<Portfolio> repository = new FileRepository<>("res/portfolio.txt");
    TradeOperation model = new PortfolioTradeOperation(repository);
    TradeController controller = new PortfolioTradeController(view, model);
    controller.execute();
  }

}
