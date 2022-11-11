import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import controller.PortfolioTradeController;
import controller.TradeController;
import model.datarepo.DataRepository;
import model.datarepo.FileRepository;
import model.dataparseer.DataParser;
import model.dataparseer.PortfolioDataParser;
import model.portfolio.Portfolio;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.PortfolioTrader;
import model.stocktradings.TradeOperation;
import view.TextualView;
import view.View;

/**
 * Testing for PortfolioTradeController.
 */
public class PortfolioTradeControllerTest {

  private OutputStream out;
  private String input;

  private void setup() throws Exception {
    InputStream in;
    TradeController controller;
    in = new ByteArrayInputStream(input.getBytes());
    this.out = new ByteArrayOutputStream();
    View view = this.getView(in, out);
    PortfolioTradeOperation model = this.getModel();
    controller = new PortfolioTradeController(view, model);
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
      expected.append("Enter the date at which you wish to get the " +
              "evaluation(in YYYY-MM-DD format)\n");
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
      Assert.assertTrue(actual.contains("Portfolio could not be created since " +
              "all the shares in the portfolio are Invalid.\n"));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void MultiplePortfolios() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1" +
              "\n2\n3\ntest1\n3\ntest2";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("Portfolio Names : \ntest2\ntest1"));
      Assert.assertTrue(actual.contains("Portfolio Name : test2\n" +
              "Stock Symbol : TSLA,Quantity : 1.0\n" +
              "Stock Symbol : GOOGL,Quantity : 1.0"));
      Assert.assertTrue(actual.contains("Portfolio Name : test1\n" +
              "Stock Symbol : AAPL,Quantity : 4.0\n" +
              "Stock Symbol : GOOGL,Quantity : 1.0"));

      System.out.println(actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }


  @Test
  public void PortfolioWithSameName() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest1\n2\ngoogl\n1\ntsla\n1" +
              "\n2\n3\ntest1\n3\ntest2";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The portfolio with same name exists." +
              "It cannot be changed after creation.\n"));

      System.out.println(actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void ValueForPortfolioWhichDoesNotExist() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1\naapl\n4\n4\ntest2\n2015-10-23";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The portfolio with name provided does not exist.\n"));

      System.out.println(actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void IfDateisLaterThanToday() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1\naapl\n4\n4\ntest1\n2025-10-23";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The entered date is in future.\n"));

      System.out.println(actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void getPortfolioValue() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1\naapl\n4\n4\ntest1\n2015-10-23";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The value of portfolio is 17840.0"));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_FractionNotAllowed() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1.5\n2\nibm\n10";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("Fractional number of shares are not allowed"));
      System.out.println(actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void getPortfolioValue_MultipleDates() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1\naapl\n4\n4\ntest1\n2015-10-23\n4\ntest1\n2016-10-21";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The value of portfolio is 17840.0"));

      System.out.println(actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void savePortfolioToFileSuccessfully() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1" +
              "\n2\n3\ntest1\n3\ntest2\n5\ntest1";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The portfolio saved to file successfully"));
    } catch (Exception e) {
      Assert.fail();
    }

  }

  @Test
  public void savePortfolioToFileFail() {
    try {
      this.input = "1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1" +
              "\n2\n3\ntest1\n3\ntest2\n5\ntest10";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The save could not be completed. " +
              "Please make sure the portfolio nameis entered correctly and " +
              "the data source(file) exist."));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void loadPortfolioToApplication() {
    try {
      this.input = "6\n2";
      this.setup();
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The load of portfolio is successfully completed"));
    } catch (Exception e) {
      Assert.fail();
    }

  }

  private View getView(InputStream in, OutputStream out) {
    return new TextualView(in, out);
  }

  private PortfolioTradeOperation getModel() throws Exception {
    DataRepository<Portfolio> repository = new FileRepository<>("res/portfolio.txt");
    DataParser<Portfolio> parser = new PortfolioDataParser();
    PortfolioTradeOperation model = new PortfolioTrader(repository, parser);
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
    menu.append("Enter the menu option you wish to choose.\n");
    menu.append("Press and enter any other key to exit the application.\n");
    if (!isValid) {
      menu.append("You have decided to exit the application. See you next time\n");
    }
    return menu;
  }


}
