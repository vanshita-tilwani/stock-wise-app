import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import controller.PortfolioTradeController;
import controller.TradeController;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import view.TextualView;
import view.View;

/**
 * Testing for PortfolioTradeController.
 */
public class PortfolioControllerUnitTest {

  private StringBuilder modelBuilder;
  private TradeController controller;


  private void setup(String input) throws Exception {
    modelBuilder = new StringBuilder();
    View view = new TextualView(new ByteArrayInputStream(input.getBytes()), new PrintStream(new ByteArrayOutputStream()));
    TradeOperation model = new LoggingModel(modelBuilder);
    controller = new PortfolioTradeController(view, model);
    controller.execute();
  }

  @Test
  public void doesControllerCallCreate() {
    try {
      String expectedPortfolioName = "test1";
      Map<String, Double> stocks = new HashMap<>();
      stocks.put("AAPL", 1.0);
      StringBuilder input = this.createSimulatedPortfolio(expectedPortfolioName, stocks);
      StringBuilder expected = this.getSimulatedPortfolio(expectedPortfolioName, stocks);
      this.setup(input.toString());
      Assert.assertEquals(expected.toString(), modelBuilder.toString());
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void doesControllerCallCreateWithMultipleStock() {
    try {
      String expectedPortfolioName = "test1";
      Map<String, Double> stocks = new HashMap<>();
      stocks.put("AAPL", 1.0);
      stocks.put("NOW", 1.0);
      stocks.put("IBM", 1.0);
      stocks.put("TSLA", 1.0);
      stocks.put("GOOGL", 1.0);
      stocks.put("META", 1.0);
      stocks.put("COST", 1.0);
      StringBuilder input = this.createSimulatedPortfolio(expectedPortfolioName, stocks);
      StringBuilder expected = this.getSimulatedPortfolio(expectedPortfolioName, stocks);
      this.setup(input.toString());
      Assert.assertEquals(expected.toString(), modelBuilder.toString());
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void doesControllerShowAllPortfolios() {
    try {
      String input = getMenuOptions("AllPortfolio") + "\n";
      String expected = "getAllTrades called\n";
      this.setup(input);
      Assert.assertEquals(expected, modelBuilder.toString());
    }
    catch(Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void doesControllerEvaluateValue() {
    try {
      String input = getMenuOptions("PortfolioEvaluation") + "\n";
      String portfolioName = "test1";
      LocalDate date = LocalDate.now();
      String expected = "Value called for "+ portfolioName + " at "+ date +"\n";
      this.setup(input+portfolioName+"\n"+date+"\n");
      Assert.assertEquals(expected, modelBuilder.toString());
    }
    catch(Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void doesControllerGet() {

    try {
      String portfolioName = "test1";
      String input = getMenuOptions("PortfolioComposition") + "\n";
      this.setup(input+portfolioName+"\n");
      String expected = "Get composition method called for " + portfolioName + "\n";
      Assert.assertEquals(expected, modelBuilder.toString());
    }
    catch(Exception e) {
      Assert.fail();
    }
  }


  @Test
  public void doesControlledCallSave() {
    try {
      String portfolioName = "test1";
      String input = getMenuOptions("SavePortfolio") + "\n";
      String expected = "Save portfolio : "+ portfolioName+"\n";
      this.setup(input+portfolioName+"\n");
      Assert.assertEquals(expected, modelBuilder.toString());
    }
    catch(Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void doesControlledCallLoad() {
    try {
      String expected = "Load all the portfolio in the data source\n";
      String input = getMenuOptions("LoadPortfolio") + "\n";
      this.setup(input);
      Assert.assertEquals(expected, modelBuilder.toString());
    }
    catch(Exception e) {
      Assert.fail();
    }
  }

  private StringBuilder getSimulatedPortfolio(String name, Map<String, Double> stocks) {
    StringBuilder input = new StringBuilder("Received : addPortfolio\nTYPE : MASTER\n");
    input.append("Portfolio Name : "+name+"\n");
    input.append("STOCKS : \n");
    stocks.forEach((key, value) -> {
      input.append("Stock Symbol : "+key.toUpperCase()+",Quantity : "+value+"\n");
    });
    input.append("------ END ------\n");
    return input;
  }

  private StringBuilder createSimulatedPortfolio(String name, Map<String, Double> stocks) {
    int operation = this.getMenuOptions("createSimulatedPortfolio");
    StringBuilder input = new StringBuilder( operation+"\n");
    input.append(name+"\n");
    input.append(stocks.keySet().size()+"\n");
    stocks.forEach((key, value) -> {
      input.append(key+"\n");
      input.append(value+"\n");
    });
    return input;
  }

  private Integer getMenuOptions(String operation) {
    Map<String, Integer> commands = new HashMap<>();
    commands.put("createSimulatedPortfolio",1);
    commands.put("createTransactionalPortfolio", 2);
    commands.put("BuyStock", 3);
    commands.put("SellStock", 4);
    commands.put("AllPortfolio", 5);
    commands.put("PortfolioComposition", 6);
    commands.put("PortfolioCompositionAtDate", 7);
    commands.put("PortfolioEvaluation", 8);
    commands.put("EvaluateCostBasis", 9);
    commands.put("EvaluatePortfolioPerformance", 10);
    commands.put("SavePortfolio", 11);
    commands.put("LoadPortfolio", 12);
    return commands.get(operation);
  }
  /*@Test
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

  }*/

  private String getMenuOptions() {
    // Generates all the menu options displayed to the user.
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
    return menu.toString();
  }


}
