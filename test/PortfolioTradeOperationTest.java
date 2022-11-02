import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import datarepo.FileRepository;
import model.portfolio.PortfolioImpl;
import model.stock.Stock;
import model.stocktradings.PortfolioTradeOperation;
import model.stocktradings.TradeOperation;
import model.trade.StockTradeImpl;
import model.trade.Trade;

public class PortfolioTradeOperationTest {

  private TradeOperation trade;

  @Test
  public void canCreateObject() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void buy_newportfolio() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void buy_PortfolioExists() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      this.trade.buy(new PortfolioImpl("test1", shares));
      Assert.fail();
    } catch (IllegalArgumentException e) {
      // Pass here
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void value_AfterToday() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      this.trade.value(LocalDate.parse("2030-10-10"), "test1");
      Assert.fail();
    } catch (IllegalArgumentException e) {
      // Pass here
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void value_InvalidPortfolio() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      this.trade.value(LocalDate.parse("2030-10-10"), "test2");
      Assert.fail();
    } catch (IllegalArgumentException e) {
      // Pass here
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void value() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      Double value = this.trade.value(LocalDate.parse("2019-10-10"), "test1");
      Double expected = 126.0;
      Assert.assertEquals(expected, value);
    } catch (IllegalArgumentException e) {
      Assert.fail();
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void getPortfolio_MissingPortfolio() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      this.trade.get("test2");
      Assert.fail();
    } catch (IllegalArgumentException e) {
      // Pass here
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void getPortfolio() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      Object portfolio = this.trade.get("test1");
      Assert.assertNotNull(portfolio);
    } catch (IllegalArgumentException e) {
      Assert.fail();
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void getAllTrades() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      Set<String> allTrades = this.trade.getAllTrades();
      Assert.assertNotNull(allTrades);
      Assert.assertTrue(allTrades.size() == 1);
      Assert.assertTrue(allTrades.containsAll(new ArrayList<>(Arrays.asList("test1"))));
    } catch (IllegalArgumentException e) {
      Assert.fail();
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void savePortfolio() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      Assert.assertTrue(this.trade.save("test1"));
    } catch (IllegalArgumentException e) {
      Assert.fail();
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void savePortfolio_Fail() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      List<Trade<Stock>> shares = new ArrayList<>();
      shares.add(new StockTradeImpl("GOOG", 2.0));
      this.trade.buy(new PortfolioImpl("test1", shares));
      Assert.assertFalse(this.trade.save("test10"));
    } catch (IllegalArgumentException e) {
      Assert.fail();
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void loadPortfolio() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res/portfolio.txt"));
      Assert.assertNotNull(this.trade);
      Assert.assertTrue(this.trade.load());
    } catch (IllegalArgumentException e) {
      Assert.fail();
    } catch (IOException e) {
      Assert.fail();
    }
  }

  @Test
  public void loadPortfolio_Fail() {
    try {
      this.trade = new PortfolioTradeOperation(new FileRepository<>("res"));
      Assert.assertNotNull(this.trade);
      Assert.assertFalse(this.trade.load());
    } catch (IllegalArgumentException e) {
      Assert.fail();
    } catch (IOException e) {
      Assert.fail();
    }
  }
}
