import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

import javax.xml.crypto.Data;

import controller.TradeController;
import controller.PortfolioTradeController;
import datarepo.DataRepository;
import datarepo.FileRepository;
import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import model.stocktradings.PortfolioTradeOperation;
import view.CommandPromptView;
import view.View;

public class ProgramRunner {
  public static void main(String[] args) throws IOException {

    //LocalDate date = LocalDate.parse("2022-10-23");
    View view = new CommandPromptView();
    // TODO : make sure the path is not absolute and also it should work on both windows and mac.
    // TODO : let the user know that this is the path where you expect the file to be
    DataRepository<Portfolio> repository = new FileRepository<>("res/portfolio.txt");
    TradeOperation model = new PortfolioTradeOperation(repository);
    TradeController controller = new PortfolioTradeController(view, model);
    controller.execute();
  }

}
