import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioImpl;

public class PortfolioImplTest {

  @Test
  public void PortfolioCreate() {
    String expectedName = "test1";
    Map<String, Double> stocks = new HashMap<>();
    stocks.put("GOOG", 2.0);
    stocks.put("NOW", 7.0);
    stocks.put("AAPL", 7.0);
    stocks.put("MSFT", 7.0);
    Portfolio portfolio = new PortfolioImpl(expectedName, stocks);
    Assert.assertEquals(expectedName, portfolio.name());
    Assert.assertNotNull(portfolio);
    Double expectedValue = 1302.0;
    Double actualValue = portfolio.value(LocalDate.parse("2022-10-26"));
    Assert.assertEquals(expectedValue, actualValue);
    String expectedPortfolio = "Portfolio Name : test1\n" +
            "Stock Symbol : MSFT,Quantity : 7.0\n" +
            "Stock Symbol : GOOG,Quantity : 2.0\n" +
            "Stock Symbol : AAPL,Quantity : 7.0\n" +
            "Stock Symbol : NOW,Quantity : 7.0\n";
    String actual = portfolio.toString();
    Assert.assertEquals(expectedPortfolio, actual);
  }



}
