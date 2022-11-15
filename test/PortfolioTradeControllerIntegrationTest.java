import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import controller.PortfolioTradeController;
import controller.TradeController;
import model.datarepo.FileRepository;
import model.stocktradings.PortfolioTradeOperation;
import view.TextualView;
import view.View;

/**
 * Testing for PortfolioTradeController.
 */
public class PortfolioTradeControllerIntegrationTest {

  private OutputStream out;

  private TradeController controller;

  private void setup(String input) throws Exception {

    this.out = new ByteArrayOutputStream();
    View view = this.getView(new ByteArrayInputStream(input.getBytes()), out);
    PortfolioTradeOperation model = this.getModel();
    controller = new PortfolioTradeController(view, model);
    controller.execute();
  }

  @Test
  public void doesControllerDisplayMenu() {

    try {
      this.setup("");
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
      this.setup("h");
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
      this.setup("5");
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
      this.setup("6\ntest1");
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio\n");
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
      this.setup("8\ntest1\n23-10-2022");
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio\n");
      expected.append("Enter the date at which you wish to get the retrieve the " +
              "information(in YYYY-MM-DD format)\n");
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
      this.setup("1\ntest1\nfdh");
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio\n");
      expected.append("Enter the number of stocks you wish to purchase\n");
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
      this.setup("1\ntest1\n1\nGOOG\nhfd");
      String actual = out.toString();
      StringBuilder expected = this.getMenuOptions(true);
      expected.append("Enter the name of the portfolio\n");
      expected.append("Enter the number of stocks you wish to purchase\n");
      expected.append("Enter the stock symbol\n");
      expected.append("Enter the number of shares\n");
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
      this.setup("1\ntest1\n4\nGOOG\n1\nNOW\n4\nAAPL\n6\nMSFT\n10\n2");
      String actual = out.toString();
      Assert.assertTrue(actual.contains("Portfolio Names : \ntest1\n"));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_InvalidStocks() {
    try {
      this.setup("1\ntest1\n4\nGOO\n1\nNO\n4\nAPL\n6\nMST\n10\n2");
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
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1" +
              "\n5\n6\ntest1\n6\ntest2");
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
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest1\n2\ngoogl\n1\ntsla\n1" +
              "\n5\n6\ntest1\n6\ntest2");
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
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n8\ntest2\n2015-10-23");
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
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n8\ntest1\n2025-10-23");
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
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n8\ntest1\n2015-10-23");
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The value of portfolio is 17840.0"));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_FractionNotAllowed() {
    try {
      this.setup("1\ntest1\n2\ngoogl\n1.5\n2\nibm\n10");
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
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n8\ntest1\n2015-10-23\n8\ntest1\n2016-10-21");
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
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1" +
              "\n5\n6\ntest1\n6\ntest2\n11\ntest1");
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The portfolio saved to file successfully"));
    } catch (Exception e) {
      Assert.fail();
    }

  }

  @Test
  public void savePortfolioToFileFail() {
    try {
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1" +
              "\n5\n6\ntest1\n6\ntest2\n11\ntest10");
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The save could not be completed. " +
              "Please make sure the portfolio name is entered correctly and " +
              "the data source(file) exist."));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void loadPortfolioToApplication() {
    try {
      this.setup("12\n5");
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
    PortfolioTradeOperation model = new PortfolioTradeOperation(new FileRepository("res/portfolio.txt"));
    return model;
  }

  private StringBuilder getMenuOptions(boolean isValid) {
    StringBuilder menu = new StringBuilder();
    menu.append("Main Menu :\n");
    menu.append("1. Create Master Portfolio\n");
    menu.append("2. Create Transactional Portfolio\n");
    menu.append("3. Buy Stock\n");
    menu.append("4. Sell Stock\n");
    menu.append("5. Get All the Portfolio Names\n");
    menu.append("6. Get an existing Portfolio composition(end of all transactions)\n");
    menu.append("7. Gen an existing Portfolio composition at a specific date\n");
    menu.append("8. Get the evaluation of an existing Portfolio\n");
    menu.append("9. Get the cost basis of a Portfolio\n");
    menu.append("10. Get the portfolio Performance over a Period of time\n");
    menu.append("11. Save the portfolio to file\n");
    menu.append("12. Load the portfolio\n");
    menu.append("Enter the menu option you wish to choose.\n");
    menu.append("Press and enter any other key to exit the application.\n");
    if (!isValid) {
      menu.append("You have decided to exit the application. See you next time\n");
    }
    return menu;
  }


}
