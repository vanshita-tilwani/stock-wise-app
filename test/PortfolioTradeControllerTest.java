import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

  private InputStream in;
  private OutputStream out;
  private String input;
  private TradeController controller;

  private void setup() throws Exception {
    this.in = new ByteArrayInputStream(input.getBytes());
    this.out = new ByteArrayOutputStream();
    View view = this.getView(in, out);
    TradeOperation model = this.getModel();
    this.controller = new PortfolioTradeController(view, model);
    controller.execute();
  }

  @Test
  public void doesControllerDisplayMenu() {

    try {
      this.input = "";
      this.setup();
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(false);
      Assert.assertEquals(expected.toString(), actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void invalidMenuOption() {
    try {
      this.input = "h";
      this.setup();
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(false);
      Assert.assertEquals(expected.toString(), actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void getAllPortFolios_NoPortfolioFlow() {
    try {
      this.input = "2";
      this.setup();
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("The application does not contain any portfolio.\n");
      expected.append(this.getMenuOptions(false));
      Assert.assertEquals(expected.toString(), actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void getPortfolioComposition_NoPortfolioExist() {
    try {
      this.input = "3\ntest1";
      this.setup();
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio you wish to retrieve\n");
      expected.append("The portfolio with name provided does not exist.\n");
      expected.append(this.getMenuOptions(false));
      Assert.assertEquals(expected.toString(), actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void getPortfolioEvaluation_InvalidDate() {
    try {
      this.input = "4\ntest1\n23-10-2022";
      this.setup();
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio you wish to evaluate\n");
      expected.append("Enter the date at which you wish to get the evaluation(in YYYY-MM-DD format)\n");
      expected.append("Please enter the Date in YYYY-MM-DD format and try again.\n");
      expected.append(this.getMenuOptions(false));
      Assert.assertEquals(expected.toString(), actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_InvalidStockNumbers() {
    try {
      this.input = "1\ntest1\nfdh";
      this.setup();
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio you wish to create\n");
      expected.append("Enter the number of stock trade you wish to carry out\n");
      expected.append("Please make sure you input valid number of stocks/quantity of stocks.\n");
      expected.append(this.getMenuOptions(false));
      Assert.assertEquals(expected.toString(), actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_InvalidStockQuantity() {
    try {
      this.input = "1\ntest1\n1\nGOOG\nhfd";
      this.setup();
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio you wish to create\n");
      expected.append("Enter the number of stock trade you wish to carry out\n");
      expected.append("Enter the stock symbol you wish to buy\n");
      expected.append("Enter the number of shares you wish to buy\n");
      expected.append("Please make sure you input valid number of stocks/quantity of stocks.\n");
      expected.append(this.getMenuOptions(false));
      Assert.assertEquals(expected.toString(), actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_ValidStocks() throws InterruptedException {
    try {
      this.input = "1\ntest1\n4\nGOOG\n1\nNOW\n4\nAAPL\n6\nMSFT\n10\n2";
      this.setup();
      String actual = out.toString();
      Assert.assertTrue(actual.contains("Portfolio Names : \ntest1\n"));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_InvalidStocks() {
    try {
      this.input = "1\ntest1\n4\nGOO\n1\nNO\n4\nAPL\n6\nMST\n10\n2";
      this.setup();
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio you wish to create\n");
      expected.append("Enter the number of stock trade you wish to carry out\n");
      for (int i = 0; i < 4; i++) {
        expected.append("Enter the stock symbol you wish to buy\n");
        expected.append("Enter the number of shares you wish to buy\n");
      }
      expected.append("NO is an Invalid Stock\n");
      expected.append("GOO is an Invalid Stock\n");
      expected.append("APL is an Invalid Stock\n");
      expected.append("MST is an Invalid Stock\n");
      expected.append("Portfolio could not be created since all the shares in the portfolio are Invalid.\n");
      expected.append(this.getMenuOptions(true));
      expected.append("The application does not contain any portfolio.\n");
      expected.append(this.getMenuOptions(false));
      Assert.assertTrue(actual.contains("Portfolio could not be created since all the shares in the portfolio are Invalid.\n"));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  private View getView(InputStream in, OutputStream out) {
    return new TextualView(in, out);
  }

  private TradeOperation getModel() throws Exception {
    DataRepository<Portfolio> repository = new FileRepository<>("res/portfolio.txt");
    TradeOperation model = new PortfolioTradeOperation(repository);
    return model;
  }

  private StringBuilder getMenuOptions(boolean isValid) {
    StringBuilder menu = new StringBuilder();
    menu.append("Main Menu :\n");
    menu.append("1. Create Portfolio\n");
    menu.append("2. Get All the Portfolio Names\n");
    menu.append("3. Get an existing Portfolio composition\n");
    menu.append("4. Get the evaluation of an existing Portfolio\n");
    menu.append("5. Save the portfolio to file\n");
    menu.append("6. Load the portfolio\n");
    menu.append("Enter the menu option you wish to choose.\nPlease any other key to exit the " +
            "application.\n");
    if (!isValid) {
      menu.append("You have decided to exit the application. See you next time\n");
    }
    return menu;
  }


}
