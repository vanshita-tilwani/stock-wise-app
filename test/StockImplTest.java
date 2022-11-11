import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import model.stock.Stock;
import model.stock.StockImpl;
import model.stockpriceprovider.MockStockDataProvider;

/**
 * Testing for StockImpl.
 */
public class StockImplTest {

  @Test
  public void validStockSymbol() {
    Stock stock = new StockImpl("TSLA");
    Assert.assertEquals("TSLA", stock.name());
    Assert.assertEquals("TSLA", stock.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void InvalidStockSymbol() {
    new StockImpl("TSL");
  }

  @Test
  public void value1() {
    Stock stock = new StockImpl("NKE");
    LocalDate date = LocalDate.parse("2014-12-01");
    Double actual = stock.price(date);
    Double expected = 2034.0;
    Assert.assertEquals(expected, actual);
  }
}
