import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import model.portfolio.Portfolio;
import model.portfolio.TransactionalPortfolio;
import model.stock.Stock;
import model.trade.Trade;
import model.trade.TransactionalStockTrade;

public class TransactionalPortfolioTest extends AbstractPortfolioTest{

  @Test(expected = IllegalArgumentException.class)
  public void createPortfolio_InvalidPurchaseDate() {
    Set<Trade<Stock>> shares = new HashSet<>();
    shares.add(this.createPurchase("GOO", 10.0,
            LocalDate.parse("2030-10-24"),1.0));
    this.portfolio = createPortfolio("portfolio1", shares);
  }

  @Test
  public void valueBeforePurchase() {
    Set<Trade<Stock>> shares = new HashSet<>();
    shares.add(this.createPurchase("GOOG", 10.0,
            LocalDate.parse("2022-10-24"),1.0));
    this.portfolio = createPortfolio("portfolio1", shares);
    Assert.assertEquals(0.0,
            this.portfolio.value(LocalDate.parse("2022-10-19")),0.01);
  }

  @Test
  public void add() {
    Set<Trade<Stock>> shares = new HashSet<>();
    shares.add(this.createPurchase("GOOG", 10.0,
            LocalDate.parse("2022-10-24"),1.0));
    this.portfolio = createPortfolio("portfolio1", shares);
    this.portfolio.add("NOW",10.0,LocalDate.parse("2022-11-04"),30.0);
    String actual = this.portfolio.composition();
    String expected = "TYPE : TRANSACTIONAL\n" +
            "Portfolio Name : portfolio1\n" +
            "STOCKS : \n" +
            "Stock Symbol : GOOG,Quantity : 10.0\n" +
            "Stock Symbol : NOW,Quantity : 10.0\n" +
            "------ END ------\n";
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sell_insufficient() {
    Set<Trade<Stock>> shares = new HashSet<>();
    shares.add(this.createPurchase("GOOG", 10.0,
            LocalDate.parse("2022-10-24"),1.0));
    this.portfolio = createPortfolio("portfolio1", shares);
    this.portfolio.sell("GOOG",11.0,LocalDate.parse("2022-11-04"),30.0);

  }

  @Test
  public void sell() {
    Set<Trade<Stock>> shares = new HashSet<>();
    shares.add(this.createPurchase("GOOG", 10.0,
            LocalDate.parse("2022-10-24"),1.0));
    this.portfolio = createPortfolio("portfolio1", shares);
    this.portfolio.sell("GOOG",5.0,LocalDate.parse("2022-11-04"),30.0);
    String actual = this.portfolio.composition();
    String expected = "TYPE : TRANSACTIONAL\n" +
            "Portfolio Name : portfolio1\n" +
            "STOCKS : \n" +
            "Stock Symbol : GOOG,Quantity : 5.0\n" +
            "------ END ------\n";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void costBasis() {
    Set<Trade<Stock>> shares = new HashSet<>();
    shares.add(this.createPurchase("GOOG", 10.0,
            LocalDate.parse("2022-10-24"),1.0));
    this.portfolio = createPortfolio("portfolio1", shares);
    Double invested = this.portfolio.costBasis(LocalDate.parse("2022-11-04"));
    Double expected = 1030.7;
    Assert.assertEquals(expected, invested, 0.01);
  }

  @Test
  public void costBasis_BeforePurchase() {
    Set<Trade<Stock>> shares = new HashSet<>();
    shares.add(this.createPurchase("GOOG", 10.0,
            LocalDate.parse("2022-10-24"),1.0));
    this.portfolio = createPortfolio("portfolio1", shares);
    Double invested = this.portfolio.costBasis(LocalDate.parse("2022-10-04"));
    Double expected = 0.0;
    Assert.assertEquals(expected, invested, 0.01);
  }

  @Test
  public void composition_AtDate() {
    Set<Trade<Stock>> shares = new HashSet<>();
    shares.add(this.createPurchase("GOOG", 10.0,
            LocalDate.parse("2022-10-24"),1.0));
    this.portfolio = createPortfolio("portfolio1", shares);
    String portfolioComposition = this.portfolio.composition(LocalDate.parse("2022-10-23"));
    String expected = "Portfolio Name : portfolio1\n" +
            "STOCKS : \n" +
            "------ END ------\n";
    Assert.assertTrue(portfolioComposition.contains(expected));
  }


  @Override
  protected Portfolio createPortfolio(String name, Set<Trade<Stock>> shares) {
    return new TransactionalPortfolio(name, shares, new HashSet<>());
  }

  @Override
  protected Trade<Stock> createPurchase(String stock, Double quantity, LocalDate date,
                                        Double commission) {
    return new TransactionalStockTrade(stock, quantity, date, commission);
  }
}
