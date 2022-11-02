import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import model.trade.StockTradeImpl;
import model.trade.Trade;

public class StockTradeImplTest {

  private Trade stockTrade;


  @Test
  public void createStockTrade() {
    this.stockTrade = new StockTradeImpl("GOOG", 10);
    String expected = "Stock Symbol : GOOG,Quantity : 10.0\n";
    Assert.assertNotNull(this.stockTrade);
    Assert.assertEquals(expected, this.stockTrade.toString());
  }

  @Test
  public void testStock() {
    this.stockTrade = new StockTradeImpl("GOOG", 10);
    String expected = "GOOG";
    Assert.assertNotNull(this.stockTrade.get());
    Assert.assertEquals(expected, this.stockTrade.get().toString());
  }

  @Test
  public void value() {
    this.stockTrade = new StockTradeImpl("GOOG", 10);
    Double expected = 630.0;
    Double actual = this.stockTrade.value(LocalDate.parse("2022-10-26"));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void value2() {
    this.stockTrade = new StockTradeImpl("GOOG", 10);
    Double expected = 630.0;
    Double value = this.stockTrade.value(LocalDate.parse("2022-10-23"));
    Assert.assertEquals(expected, value);
  }
}
