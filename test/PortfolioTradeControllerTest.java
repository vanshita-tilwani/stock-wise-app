import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import controller.PortfolioTradeController;
import controller.TradeController;
import datarepo.DataRepository;
import datarepo.FileRepository;
import model.portfolio.Portfolio;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import view.TextualView;
import view.View;

public class PortfolioTradeControllerTest {

  @Test
  public void test1() throws IOException {
    FileInputStream inputStream = null;
    try {
      inputStream = new FileInputStream("input");
    }
    catch(FileNotFoundException e) {
      Assert.fail();
    }
    OutputStream out = null;

    out = new ByteArrayOutputStream();
    View view = new TextualView(inputStream,out);
    DataRepository<Portfolio> repository = new FileRepository<>("res/portfolio.txt");
    TradeOperation model = new PortfolioTradeOperation(repository);
    TradeController controller = new PortfolioTradeController(view, model);
    controller.execute();
    String actual = out.toString();
    System.out.println(out);
  }
}
