import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;

import model.stockpriceprovider.StockDataProvider;
import model.stockpriceprovider.WebAPIStockDataProvider;

/**
 * Class ot test the WebAPIStockDataProvider.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebAPIStockDataProviderTest {

  @Test
  public void canCreateObject() {
    StockDataProvider provider = WebAPIStockDataProvider.getInstance();
    Assert.assertNotNull(provider);
  }

  @Test
  public void a_valid() {
    StockDataProvider provider = WebAPIStockDataProvider.getInstance();
    Assert.assertTrue(provider.isValid("IBM"));
  }

  @Test
  public void a_invalid() {
    StockDataProvider provider = WebAPIStockDataProvider.getInstance();
    Assert.assertTrue(provider.isValid("NOW"));
  }

  @Test
  public void available() {
    StockDataProvider provider = WebAPIStockDataProvider.getInstance();
    Assert.assertTrue(provider.isAvailable("IBM", LocalDate.parse("2022-10-24")));
  }

  @Test
  public void unavailable() {
    StockDataProvider provider = WebAPIStockDataProvider.getInstance();
    Assert.assertFalse(provider.isAvailable("IBM", LocalDate.parse("2022-10-23")));
  }


  @Test
  public void value1() {
    StockDataProvider provider = WebAPIStockDataProvider.getInstance();
    Assert.assertEquals(144.34, provider.price("IBM",
            LocalDate.now().minusDays(1)), 0.01);
  }

  @Test
  public void value2() {
    StockDataProvider provider = WebAPIStockDataProvider.getInstance();
    Assert.assertEquals(144.2, provider.price("IBM",
            LocalDate.now().minusDays(2)), 0.01);
  }

  @Test
  public void value3() {
    StockDataProvider provider = WebAPIStockDataProvider.getInstance();
    Assert.assertEquals(129.9, provider.price("IBM",
            LocalDate.parse("2022-10-23")), 0.01);
  }
}
