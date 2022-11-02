import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    String input = "2\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    View view = new TextualView(in,out);
    DataRepository<Portfolio> repository = new FileRepository<>("res/portfolio.txt");
    TradeOperation model = new PortfolioTradeOperation(repository);
    TradeController controller = new PortfolioTradeController(view, model);
    controller.execute();
    String actual = out.toString();
    System.out.println(actual);
  }
}
