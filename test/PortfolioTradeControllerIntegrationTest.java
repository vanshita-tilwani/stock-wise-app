import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import controller.PortfolioTradeController;
import model.datarepo.FileRepository;
import model.stocktradings.PortfolioTradeOperation;
import view.TextualView;
import view.View;

/**
 * Integration tests for PortfolioTradeController.
 */
public class PortfolioTradeControllerIntegrationTest {

  private OutputStream out;

  private void setup(String input) throws Exception {

    this.out = new ByteArrayOutputStream();
    View view = this.getView(new ByteArrayInputStream(input.getBytes()), out);
    PortfolioTradeOperation model = this.getModel();
    new PortfolioTradeController(view, model);
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
      this.setup("8\ntest1\n23-10-2022\n2022-10-23");
      String actual = out.toString();
      String expected = "You have entered an invalid date.Please re-enter the date\n";
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_InvalidStockNumbers() {
    try {
      this.setup("1\ntest1\nfdh\n1\ngoog\n10");
      String actual = out.toString();
      String expected = "Invalid number of stocks\n"
              + "Enter the number of stocks you wish to purchase\n";
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_InvalidStockQuantity() {
    try {
      this.setup("1\ntest1\n1\nGOOG\nhfd\n1");
      String actual = out.toString();
      String expected = "Invalid number of shares\n"
              + "Please enter the number of shares again.\n";
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_ValidStocks() {
    try {
      this.setup("1\ntest1\n4\nGOOG\n1\nNOW\n4\nAAPL\n6\nMSFT\n10\n5");
      String actual = out.toString();
      Assert.assertTrue(actual.contains("Portfolio Names : \ntest1\n"));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createPortfolio_InvalidStocks() {
    try {
      this.setup("1\ntest1\n4\nGOO\n1\nNO\n4\nAPL\n6\nMST\n10\n");
      String actual = out.toString();
      Assert.assertTrue(actual.contains("Portfolio could not be created since "
              + "all the shares in the portfolio are Invalid.\n"));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void MultiplePortfolios() {
    try {
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1"
              + "\n5\n6\ntest1\n6\ntest2");
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("Portfolio Names : \ntest2\ntest1"));
      Assert.assertTrue(actual.contains("Portfolio Name : test2\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOGL,Quantity : 1.0\n"
              + "Stock Symbol : TSLA,Quantity : 1.0"));
      Assert.assertTrue(actual.contains("Portfolio Name : test1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOGL,Quantity : 1.0\n"
              + "Stock Symbol : AAPL,Quantity : 4.0"));


      System.out.println(actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }


  @Test
  public void PortfolioWithSameName() {
    try {
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest1\n2\ngoogl\n1\ntsla\n1"
              + "\n5\n6\ntest1\n6\ntest2");
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The portfolio with same name exists."
              + "It cannot be changed after creation.\n"));

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
      Assert.assertTrue(actual.contains("The value of portfolio is $1195.65"));

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
      Assert.assertTrue(actual.contains("The value of portfolio is $1290.46"));

      System.out.println(actual);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void savePortfolioToFileSuccessfully() {
    try {
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1"
              + "\n5\n6\ntest1\n6\ntest2\n11\ntest1");
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The portfolio saved to file successfully"));
    } catch (Exception e) {
      Assert.fail();
    }

  }

  @Test
  public void savePortfolioToFileFail() {
    try {
      this.setup("1\ntest1\n2\ngoogl\n1\naapl\n4\n1\ntest2\n2\ngoogl\n1\ntsla\n1"
              + "\n5\n6\ntest1\n6\ntest2\n11\ntest10");
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains("The save could not be completed. "
              + "Please make sure the portfolio name is entered correctly and "
              + "the data source(file) exist."));
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


  @Test
  public void createTransactionalPortfolio() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "6\ntransaction1");
      String actual = this.out.toString();
      String expected = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 10.0\n"
              + "Stock Symbol : AAPL,Quantity : 10.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void addPurchaseToTransactionalPortfolio() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "3\ntransaction1\nnow\n15\n2022-11-09\n50\n"
              + "6\ntransaction1");
      String actual = this.out.toString();
      String expected = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 10.0\n"
              + "Stock Symbol : AAPL,Quantity : 10.0\n"
              + "Stock Symbol : NOW,Quantity : 15.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void addPurchaseToTransactionalPortfolio2() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "3\ntransaction1\ngoog\n15\n2022-11-09\n50\n"
              + "6\ntransaction1");
      String actual = this.out.toString();
      String expected = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 25.0\n"
              + "Stock Symbol : AAPL,Quantity : 10.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void addPurchaseToPortfolio_SimulatedPortfolio() {
    try {
      this.setup("1\nmaster1\n2\ngoog\n8\naapl\n6\n3\nmaster1\nnow\n5\n2022-10-10\n"
              + "12\n");
      String expected = "Purchasing a new Stock is not allowed in this portfolio\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void addPurchaseToPortfolio_PortfolioDoesNotExist() {
    try {
      this.setup("3\nmaster1\nnow\n5\n2022-10-10\n12\n");
      String actual = this.out.toString();
      String expected = "The portfolio with name provided does not exist.\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void sellFromPortfolio_PortfolioDoesNotExist() {
    try {

      this.setup("1\nmaster1\n2\ngoog\n8\naapl\n6\n4\nmaster1\nnow\n5\n2022-10-10\n"
              + "12\n");
      String actual = this.out.toString();
      String expected = "Selling an existing Stock is not allowed in Simulated Portfolio\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void sellFromPortfolio_NotEnoughStocksToSell() {
    try {

      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "4\ntransaction1\nnow\n3\n2021-10-10\n12");
      String actual = this.out.toString();
      String expected = "You do not have enough shares of the stock to sell\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void sellFromPortfolio() {
    try {

      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "4\ntransaction1\ngoog\n3\n2022-11-15\n12\n"
              + "6\ntransaction1");
      String actual = this.out.toString();
      String expected = "The sale was completed successfully!\n";
      String expectedComposition = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 7.0\n"
              + "Stock Symbol : AAPL,Quantity : 10.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected));
      Assert.assertTrue(actual.contains(expectedComposition));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void sellFromPortfolio_SimulatedPortfolio() {
    try {

      this.setup("4\nmaster1\nnow\n5\n2022-10-10\n12\n");
      String actual = this.out.toString();
      String expected = "The portfolio with name provided does not exist.\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void buyOnHoliday() {
    try {

      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "3\ntransaction1\ngoog\n3\n2022-10-23\n12\n"
              + "6\ntransaction1");
      String actual = this.out.toString();
      String expected = "The Stock Market is closed on the specified date.Invalid trade Date.\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void saleOnHoliday() {
    try {

      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "4\ntransaction1\ngoog\n3\n2022-11-06\n12\n"
              + "6\ntransaction1");
      String actual = this.out.toString();
      String expected = "The Stock Market is closed on the specified date.Invalid trade Date.\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void invalidCommissionFee() {
    try {

      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\nabc\n1");
      String actual = this.out.toString();
      String expected = "You have entered an invalid commission fee.Please re-enter the fee\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void invalidCommissionFee_Negative() {
    try {

      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n-12\n1");
      String actual = this.out.toString();
      String expected = "You have entered an invalid commission fee.Please re-enter the fee\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void GetCompositionAtDate_DateNotInCorrectFormat() {
    try {

      this.setup("7\ntest1\n11-20-2022\n2024-11-20");
      String actual = this.out.toString();
      String expected = "You have entered an invalid date.Please re-enter the date\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void GetCompositionAtDate_NoPortfolio() {
    try {

      this.setup("7\ntest1\n2022-10-10\n");
      String actual = this.out.toString();
      String expected = "The portfolio with name provided does not exist.\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void CompositionAtDate() {
    try {

      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "7\ntransaction1\n2022-10-25\n"
              + "7\ntransaction1\n2022-10-18\n"
              + "7\ntransaction1\n2022-11-01");
      String actual = this.out.toString();
      String expected1 = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 10.0\n"
              + "------ END ------\n";
      String expected2 = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction1\n"
              + "STOCKS : \n"
              + "------ END ------\n";
      String expected3 = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 10.0\n"
              + "Stock Symbol : AAPL,Quantity : 10.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected1));
      Assert.assertTrue(actual.contains(expected2));
      Assert.assertTrue(actual.contains(expected3));


    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void CompositionAtDate_SimulatedPortfolio() {
    try {

      this.setup("1\nmaster1\n2\nGOOG\n10\nAAPL\n10\n7\nmaster1\n2022-10-25\n7\nmaster1\n"
              + "2022-10-18\n7\nmaster1\n2022-11-01");
      String actual = this.out.toString();
      String expected1 = "TYPE : SIMULATED\n"
              + "Portfolio Name : master1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 10.0\n"
              + "Stock Symbol : AAPL,Quantity : 10.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected1));


    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void value_transactionalPortfolio_AfterToday() {
    try {
      this.setup("2\ntransaction1\n3\ngoog\n10\n2022-10-24\n100\n3\naapl\n10\n2022-11-01\n10\n8\n"
              + "transaction1\n2023-10-19");
      String actual = this.out.toString();
      String expected1 = "The entered date is in future.\n";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void value_transactionalPortfolio1() {
    try {
      this.setup("2\ntransaction1\n3\ngoog\n10\n2022-10-24\n100\n3\naapl\n10\n2022-11-01\n10\n8\n"
              + "transaction1\n2022-10-19");
      String actual = this.out.toString();
      String expected1 = "The value of portfolio is $0.0";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void value_transactionalPortfolio2() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "8\ntransaction1\n2022-10-24");
      String actual = this.out.toString();
      String expected1 = "The value of portfolio is $1029.7";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void value_transactionalPortfolio3() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "8\ntransaction1\n2022-11-02");
      String actual = this.out.toString();
      String expected1 = "The value of portfolio is $2321.0";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void costBasis_Simulated() {
    try {
      this.setup("1\nmaster1\n2\ngoog\n10\ngoog\n10\n9\nmaster1\n2022-10-24");
      String actual = this.out.toString();
      String expected1 = "The cost basis cannot be evaluated for SimulatedPortfolio.\n";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void costBasis_NoPortfolio() {
    try {
      this.setup("9\nmaster1\n2022-10-24");
      String actual = this.out.toString();
      String expected1 = "The portfolio with name provided does not exist.\n";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void costBasis_TransactionalPortfolio1() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "9\ntransaction1\n2022-10-23");
      String actual = this.out.toString();
      String expected1 = "The cost basis for the portfolio is $0.0";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void costBasis_TransactionalPortfolio2() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "9\ntransaction1\n2022-10-24");
      String actual = this.out.toString();
      String expected1 = "The cost basis for the portfolio is $1129.7";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void costBasis_TransactionalPortfolio3() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction1\naapl\n10\n2022-11-01\n10\n"
              + "9\ntransaction1\n2022-11-02");
      String actual = this.out.toString();
      String expected1 = "The cost basis for the portfolio is $2646.2";
      Assert.assertTrue(actual.contains(expected1));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void saveTransactionalPortfolioToFile1() {
    try {
      this.setup("2\ntransaction10\n"
              + "3\ntransaction10\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction10\naapl\n10\n2022-11-01\n10\n"
              + "11\ntransaction10\n");
      String actual = this.out.toString();
      String expected = "The portfolio saved to file successfully";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void buyStock_ConsolidateSameTradesInTransactional() {
    try {
      this.setup("2\ntransaction10\n"
              + "3\ntransaction10\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction10\naapl\n10\n2022-11-01\n10\n"
              + "3\ntransaction10\ngoog\n5\n2022-10-24\n100\n"
              + "6\ntransaction10");
      String actual = this.out.toString();
      String expected = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction10\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 15.0\n"
              + "Stock Symbol : AAPL,Quantity : 10.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void buy_NegativeShares() {
    try {
      this.setup("2\ntransaction10\n"
              + "3\ntransaction10\ngoog\n-10\n10\n2022-10-24\n100");
      String actual = this.out.toString();
      String expected = "Invalid number of shares\n"
              + "Please enter the number of shares again.";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void buy_ZeroShares() {
    try {
      this.setup("2\ntransaction10\n"
              + "3\ntransaction10\ngoog\n0\n10\n2022-10-24\n100");
      String actual = this.out.toString();
      String expected = "Invalid number of shares\n"
              + "Please enter the number of shares again.";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void sell_NegativeShares() {
    try {
      this.setup("2\ntransaction10\n"
              + "4\ntransaction10\ngoog\n-10\n10\n2022-10-24\n100");
      String actual = this.out.toString();
      String expected = "Invalid number of shares\n"
              + "Please enter the number of shares again.";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void sell_ZeroShares() {
    try {
      this.setup("2\ntransaction10\n"
              + "4\ntransaction10\ngoog\n0\n10\n2022-10-24\n100");
      String actual = this.out.toString();
      String expected = "Invalid number of shares\n"
              + "Please enter the number of shares again.";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }


  @Test
  public void saveTransactionalPortfolioToFile2() {
    try {
      this.setup("2\ntransaction10\n"
              + "3\ntransaction10\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction10\naapl\n10\n2022-11-01\n10\n"
              + "4\ntransaction10\ngoog\n5\n2022-10-25\n100\n"
              + "11\ntransaction10\n");
      String actual = this.out.toString();
      String expected = "The portfolio saved to file successfully";
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void loadTransactionalPortfolioToFile() {
    try {
      this.setup("2\ntransaction10\n"
              + "3\ntransaction10\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction10\naapl\n10\n2022-11-01\n10\n"
              + "11\ntransaction10\n12\n6\ntransaction10\n");
      String actual = this.out.toString();
      String expected = "The load of portfolio is successfully";
      String expectedComposition = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : transaction10\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 10.0\n"
              + "Stock Symbol : AAPL,Quantity : 10.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected));
      Assert.assertTrue(actual.contains(expectedComposition));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void portfolioPerformance_Transactional2() {
    try {
      this.setup("2\ntransaction10\n"
              + "3\ntransaction10\ngoog\n10\n2022-10-24\n100\n"
              + "3\ntransaction10\naapl\n10\n2022-11-01\n10\n"
              + "4\ntransaction10\ngoog\n5\n2022-10-27\n10\n"
              + "3\ntransaction10\nnow\n10\n2022-11-03\n100\n"
              + "4\ntransaction10\naapl\n2\n2022-11-07\n50\n"
              + "10\ntransaction10\n2022-10-24\n2022-11-15\n");
      String expected = "Performance of portfolio transaction10 from 2022-10-24 to 2022-11-15\n"
              + "2022-10-24: *****\n"
              + "2022-10-25: *****\n"
              + "2022-10-26: ****\n"
              + "2022-10-27: **\n"
              + "2022-10-28: **\n"
              + "2022-10-29: **\n"
              + "2022-10-30: **\n"
              + "2022-10-31: **\n"
              + "2022-11-01: *********\n"
              + "2022-11-02: *********\n"
              + "2022-11-03: ****************************\n"
              + "2022-11-04: ***************************\n"
              + "2022-11-05: ***************************\n"
              + "2022-11-06: ***************************\n"
              + "2022-11-07: *************************\n"
              + "2022-11-08: **************************\n"
              + "2022-11-09: *************************\n"
              + "2022-11-10: ****************************\n"
              + "2022-11-11: ****************************\n"
              + "2022-11-12: ****************************\n"
              + "2022-11-13: ****************************\n"
              + "2022-11-14: ****************************\n"
              + "2022-11-15: *****************************\n"
              + "Scale : * = $200.0";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));

    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void portfolioPerformance_InvalidPortfolio() {
    try {
      this.setup("2\ntransaction10\n"
              + "3\ntransaction10\ngoog\n10\n2022-10-24\n100\n"
              + "10\ntransaction1\n2022-10-24\n2022-11-15");
      String expected = "The portfolio with name provided does not exist";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }


  @Test
  public void portfolioPerformance_Simulated() {
    try {
      this.setup("1\nmaster1\n2\ngoog\n10\naapl\n10\n10\n"
              + "master1\n2022-10-24\n2022-11-10");
      String actual = this.out.toString();
      System.out.println(actual);
      String expected = "Performance of portfolio master1 from 2022-10-24 to 2022-11-10\n"
              + "2022-10-24: *******************************\n"
              + "2022-10-25: ********************************\n"
              + "2022-10-26: ******************************\n"
              + "2022-10-27: *****************************\n"
              + "2022-10-28: *******************************\n"
              + "2022-10-29: *******************************\n"
              + "2022-10-30: *******************************\n"
              + "2022-10-31: *******************************\n"
              + "2022-11-01: ******************************\n"
              + "2022-11-02: *****************************\n"
              + "2022-11-03: ***************************\n"
              + "2022-11-04: ****************************\n"
              + "2022-11-05: ****************************\n"
              + "2022-11-06: ****************************\n"
              + "2022-11-07: ****************************\n"
              + "2022-11-08: ****************************\n"
              + "2022-11-09: ***************************\n"
              + "2022-11-10: ******************************\n"
              + "Scale : * = $80.0\n";
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }


  @Test
  public void portfolioPerformance_Simulated2() {
    try {
      this.setup("1\nmaster1\n2\ngoog\n10\naapl\n10\n10\n"
              + "master1\n2022-10-24\n2022-11-10");
      String actual = this.out.toString();
      String expected = "Performance of portfolio master1 from 2022-10-24 to 2022-11-10\n"
              + "2022-10-24: *******************************\n"
              + "2022-10-25: ********************************\n"
              + "2022-10-26: ******************************\n"
              + "2022-10-27: *****************************\n"
              + "2022-10-28: *******************************\n"
              + "2022-10-29: *******************************\n"
              + "2022-10-30: *******************************\n"
              + "2022-10-31: *******************************\n"
              + "2022-11-01: ******************************\n"
              + "2022-11-02: *****************************\n"
              + "2022-11-03: ***************************\n"
              + "2022-11-04: ****************************\n"
              + "2022-11-05: ****************************\n"
              + "2022-11-06: ****************************\n"
              + "2022-11-07: ****************************\n"
              + "2022-11-08: ****************************\n"
              + "2022-11-09: ***************************\n"
              + "2022-11-10: ******************************\n"
              + "Scale : * = $80.0\n";
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void SimulatedPortfolioPerformance_InvalidStartDate() {
    try {
      this.setup("1\nmaster1\n2\ngoog\n10\naapl\n10\n10\n"
              + "master1\nabc\n2022-10-24\n2022-11-10");
      String expected = "You have entered an invalid date.Please re-enter the date\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void SimulatedPortfolioPerformance_InvalidEndDate() {
    try {
      this.setup("1\nmaster1\n2\ngoog\n10\naapl\n10\n"
              + "10\nmaster1\n2022-10-24\nabc\n2022-11-10");
      String expected = "You have entered an invalid date.Please re-enter the date\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void TransactionalPortfolioPerformance_InvalidStartDate() {
    try {
      this.setup("2\nmaster1\n"
              + "3\nmaster1\ngoog\n10\n2022-10-24\n10\n10\n"
              + "master1\nabc\n2022-10-24\n2022-11-10");
      String expected = "You have entered an invalid date.Please re-enter the date\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createAlreadyExistingFlexiblePortfolio() {
    try {
      this.setup("2\nmaster1\n"
              + "3\nmaster1\ngoog\n10\n2022-10-24\n10\n"
              + "2\nmaster1\n");
      String expected = "The portfolio with same name exists."
              + "It cannot be changed after creation.\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void TransactionalPortfolioPerformance_InvalidEndDate() {
    try {
      this.setup("2\nmaster1\n3\nmaster1\ngoog\n10\n2022-10-24\n10\n10\n"
              + "master1\n2022-10-24\nacc\n2022-11-10");
      String expected = "You have entered an invalid date.Please re-enter the date\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void PortfolioNameEmpty() {
    try {
      this.setup("1\n \nsample1\n2\ngoog\n10\naapl\n10\n");
      String actual = this.out.toString();
      System.out.println(actual);
      String expected = "You have entered an invalid name. Please enter the name again\n";
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }


  @Test
  public void ConsolidateTrades_Simulated() {
    try {
      this.setup("1\nmaster1\n2\ngoog\n10\ngoog\n10\n6\nmaster1");
      String actual = this.out.toString();
      System.out.println(actual);
      String expected = "TYPE : SIMULATED\n"
              + "Portfolio Name : master1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 20.0\n"
              + "------ END ------\n";
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void Illegal_TransactionPortfolio() {
    try {
      this.setup("2\ntransaction1\n"
              + "3\ntransaction1\ngoog\n1\n2022-10-23\n10\n");
      String actual = this.out.toString();
      System.out.println(actual);
      String expected = "The Stock Market is closed on the specified date.Invalid trade Date.\n";
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createStrategy_InvalidName() {

    try {
      String input = "13\n \ns1\n2000\n1\ngoog\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "You have entered an invalid name. Please enter the name again\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createStrategy_InvalidPrincipal() {

    try {
      String input = "13\ns1\nabc\n2000\n1\ngoog\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid amount entered\n"
              + "Enter the amount you wish to invest\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock() {

    try {
      String input = "13\ns1\n2000\n1\ngoog\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Strategy created successfully\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid1() {

    try {
      String input = "13\ns1\n2000\n1\ngoog\n50\n2022-10-24\n0";
      this.setup(input);
      String expected = "The strategy could not be created due to invalid stock percentages\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid2() {

    try {
      String input = "13\ns1\n2000\n1\ngoog\n101\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid weight\n"
              + "Please Enter the weight of stock again\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid3() {

    try {
      String input = "13\ns1\n2000\n1\ngoog\nabc\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid weight\n"
              + "Please Enter the weight of stock again\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid4() {

    try {
      String input = "13\ns1\n2000\n1\ngoog\n-100\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid weight\n"
              + "Please Enter the weight of stock again\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid5() {

    try {
      String input = "13\ns1\n2000\nabc\n1\ngoog\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid number of stocks\n"
              + "Enter the number of stocks you wish to purchase";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid6() {

    try {
      String input = "13\ns1\n2000\nabc\n-100\n1\ngoog\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid number of stocks\n"
              + "Enter the number of stocks you wish to purchase";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid7() {

    try {
      String input = "13\ns1\n2000\nabc\n0\n1\ngoog\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid number of stocks\n"
              + "Enter the number of stocks you wish to purchase";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid8() {

    try {
      String input = "13\ns1\n2000\nabc\n-1.5\n1\ngoog\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid number of stocks\n"
              + "Enter the number of stocks you wish to purchase";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid9() {

    try {
      String input = "13\ns1\n2000\nabc\n1.5\n1\ngoog\n100\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid number of stocks\n"
              + "Enter the number of stocks you wish to purchase";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid10() {

    try {
      String input = "13\ns1\n2000\n1\ngoog\n100\n10-24-2022\n2022-10-24\n0";
      this.setup(input);
      String expected = "You have entered an invalid date.Please re-enter the date";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_OneStock_Invalid11() {

    try {
      String input = "13\ns1\n2000\n1\ngoog\n100\n2022-10-24\n-1\n0";
      this.setup(input);
      String expected = "You have entered an invalid commission fee.Please re-enter the fee";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_MultipleStocks() {
    try {
      String input = "13\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\n0";
      this.setup(input);
      String expected = "Strategy created successfully";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_MultipleStocks_InvalidRatio1() {
    try {
      String input = "13\ns1\n2000\n4\ngoog\n26\naapl\n25\nnow\n25\nibm\n25\n24\n2022-10-24\n0";
      this.setup(input);
      String expected = "Invalid Weight for the stock\n"
              + "Enter the weight of shares";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createOneTimeStrategy_MultipleStocks_InvalidRatio2() {
    try {
      String input = "13\ns1\n2000\n4\ngoog\n26\naapl\n25\nnow\n25\nibm\n25\n20\n2022-10-24\n0";
      this.setup(input);
      String expected = "The strategy could not be created due to invalid stock percentages\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void getStrategies_NoStrategy() {
    try {
      String input = "15\n";
      this.setup(input);
      String expected = "The application does not contain any strategy.\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void getStrategies_Single() {
    try {
      String input = "13\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\n0\n"
              + "15\n";
      this.setup(input);
      String expected = "Strategy Names : \n"
              + "s1\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void getStrategies_Multiple() {
    try {
      String input = "13\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\n0\n"
              + "13\ns2\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\n0\n"
              + "15\n";
      this.setup(input);
      String expected = "Strategy Names : \n"
              + "s1\n"
              + "s2\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void applyOneTimeStrategy_Case1() {
    try {
      String input = "13\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\n0\n"
              + "13\ns2\n2000\n1\ngoog\n100\n2022-10-24\n0\n"
              + "2\nsample1\n"
              + "16\nsample1\ns1\n"
              + "16\nsample1\ns2\n"
              + "6\nsample1";
      this.setup(input);
      String expected = "Strategy applied successfully to the portfolio\n";
      String composition = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : sample1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 4.855783237836263\n"
              + "Stock Symbol : AAPL,Quantity : 3.345600535296086\n"
              + "Stock Symbol : IBM,Quantity : 3.768181475619866\n"
              + "Stock Symbol : NOW,Quantity : 1.3640331732867743\n"
              + "------ END ------\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
      Assert.assertTrue(actual.contains(composition));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void applyOneTimeStrategy_Case2() {
    try {
      String input = "13\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\n0\n"
              + "13\ns2\n2000\n1\ngoog\n100\n2022-10-24\n0\n"
              + "1\nsample1\n1\ngoog\n10\n"
              + "16\nsample1\ns1\n"
              + "16\nsample1\ns2\n"
              + "6\nsample1";
      this.setup(input);
      String expected = "This portfolio does not support applying investment strategies\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void applyOneTimeStrategy_Case3() {
    try {
      String input = "13\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\n0\n"
              + "13\ns2\n2000\n1\ngoog\n100\n2022-10-24\n0\n"
              + "16\nsample1\ns1\n"
              + "16\nsample1\ns2\n"
              + "6\nsample1";
      this.setup(input);
      String expected = "The portfolio with name provided does not exist.\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createAndApplyRecurringStrategy_WithEndDate() {
    try {
      String input = "2\nsample1\n14\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25"
              + "\nibm\n25\n2022-10-24\ny\n"
              + "2022-10-28\n1\n0\n15\n16\nsample1\ns1\n7\nsample1\n2022-10-26";
      this.setup(input);
      String composition = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : sample1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 14.894013845386866\n"
              + "Stock Symbol : AAPL,Quantity : 9.975573251076106\n"
              + "Stock Symbol : IBM,Quantity : 11.23298902018278\n"
              + "Stock Symbol : NOW,Quantity : 4.056081874162479\n"
              + "------ END ------\n";
      String expected = "Strategy created successfully";
      String expectedStrategy = "Strategy Names : \n"
              + "s1\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
      Assert.assertTrue(actual.contains(expectedStrategy));
      Assert.assertTrue(actual.contains(composition));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createAndApplyMultipleRecurringStrategy_WithEndDate() {
    try {
      String input = "2\nsample1\n"
              + "14\ns1\n2000\n1\ngoog\n100\n2022-10-24\ny\n2022-10-28\n1\n0\n"
              + "14\ns2\n1000\n1\naapl\n100\n2022-10-24\nn\n1\n0\n"
              + "13\ns3\n2000\n1\nnow\n100\n2022-10-24\n0\n"
              + "15\n"
              + "16\nsample1\ns1\n"
              + "16\nsample1\ns2\n"
              + "16\nsample1\ns3\n"
              + "7\nsample1\n2022-10-24\n"
              + "8\nsample1\n2022-10-24\n";
      this.setup(input);
      String composition = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : sample1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 19.423132951345053\n"
              + "Stock Symbol : AAPL,Quantity : 6.691201070592172\n"
              + "Stock Symbol : NOW,Quantity : 5.456132693147097\n"
              + "------ END ------\n";
      String expected = "Strategy created successfully";
      String expectedStrategy = "Strategy Names : \n"
              + "s3\n"
              + "s1\n"
              + "s2\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
      Assert.assertTrue(actual.contains(expectedStrategy));
      Assert.assertTrue(actual.contains(composition));
      Assert.assertTrue(actual.contains("The value of portfolio is $5000.0\n"));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void applyOneTimeStrategy() {
    try {
      String input = "13\ns1\n2000\n4\nNFLX\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-28\n0\n"
              + "2\nsample1\n"
              + "16\nsample1\ns1\n"
              + "8\nsample1\n2022-10-28\n";
      this.setup(input);
      String expectedValue = "The value of portfolio is $2000.0";
      String expected = "Strategy created successfully";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
      Assert.assertTrue(actual.contains(expectedValue));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createRecurringStrategy_InvalidCase() {
    try {
      String input = "14\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\nd\nn\n"
              + "1\n0\n15";
      this.setup(input);
      String expected = "Invalid Input\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createRecurringStrategy_WithoutEndDate() {
    try {
      String input = "14\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\nn\n"
              + "1\n0\n15";
      this.setup(input);
      String expected = "Strategy created successfully";
      String expectedStrategy = "Strategy Names : \n"
              + "s1\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
      Assert.assertTrue(actual.contains(expectedStrategy));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void applyRecurringStrategy_Frequency1() {
    try {
      String input = "14\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-23\nn\n"
              + "10\n0\n"
              + "2\ns1\n"
              + "16\ns1\ns1\n"
              + "6\ns1";
      this.setup(input);
      String expectedComposition = "TYPE : TRANSACTIONAL\n"
              + "Portfolio Name : s1\n"
              + "STOCKS : \n"
              + "Stock Symbol : GOOG,Quantity : 21.174181461749246\n"
              + "Stock Symbol : AAPL,Quantity : 13.693699157344208\n"
              + "Stock Symbol : IBM,Quantity : 14.323842830173367\n"
              + "Stock Symbol : NOW,Quantity : 5.137467129321508\n"
              + "------ END ------\n";
      String expected = "Strategy applied successfully to the portfolio\n";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
      Assert.assertTrue(actual.contains(expectedComposition));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createRecurringStrategy_InvalidFrequency() {
    try {
      String input = "14\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\nn\n"
              + "-1\n1\n0";
      this.setup(input);
      String expected = "Invalid Frequency Amount\n"
              + "Enter the Frequency in days";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void createRecurringStrategy_FractionalFrequency() {
    try {
      String input = "14\ns1\n2000\n4\ngoog\n25\naapl\n25\nnow\n25\nibm\n25\n2022-10-24\nn\n"
              + "1.2\n1\n0";
      this.setup(input);
      String expected = "Invalid Frequency Amount\n"
              + "Enter the Frequency in days";
      String actual = this.out.toString();
      Assert.assertTrue(actual.contains(expected));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  private View getView(InputStream in, OutputStream out) {
    return new TextualView(in, out);
  }

  private PortfolioTradeOperation getModel() throws Exception {
    var fileRepo = FileRepository.getInstance();
    fileRepo.setDataSource("res/portfolio.txt");
    PortfolioTradeOperation model = new PortfolioTradeOperation(fileRepo);
    return model;
  }

  private StringBuilder getMenuOptions(boolean isValid) {
    StringBuilder menu = new StringBuilder();
    menu.append("Main Menu :\n");
    menu.append("1.  Create a new Simulated Portfolio\n");
    menu.append("2.  Create a new Transactional Portfolio\n");
    menu.append("3.  Purchase a Stock and Add it to Transactional Portfolio\n");
    menu.append("4.  Sell a Stock from the Transactional Portfolio\n");
    menu.append("5.  Get all the available Portfolios in the Application\n");
    menu.append("6.  Get final composition for an Existing Portfolio \n");
    menu.append("7.  Get composition for an Existing Portfolio at a specific date\n");
    menu.append("8.  Get the total value on an Existing Portfolio\n");
    menu.append("9.  Get the cost basis of an Existing Transactional Portfolio\n");
    menu.append("10. Get the performance of an Existing Portfolio over a period of time\n");
    menu.append("11. Save an Existing Portfolio to file\n");
    menu.append("12. Load portfolios to the Application\n");
    menu.append("13. Create a one time investment strategy\n");
    menu.append("14. Create a recurring investment strategy\n");
    menu.append("15. Get all the existing strategies\n");
    menu.append("16. Apply a strategy to a portfolio\n");
    menu.append("Enter the menu option you wish to choose.\n");
    menu.append("Press and enter any other key to exit the application.\n");
    if (!isValid) {
      menu.append("You have decided to exit the application. See you next time\n");
    }
    return menu;
  }


}
