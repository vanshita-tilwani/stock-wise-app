import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import view.TextualView;
import view.View;

/**
 * Testing the TextualView class.
 */
public class TextualViewTest {

  private String input;
  private OutputStream out;
  private View view;

  @Test
  public void testDisplay() {
    this.input = "Test Check";
    this.setup();
    view.display(this.input);
    Assert.assertEquals(this.input, this.out.toString());
  }

  @Test
  public void testInput() {
    this.input = "Test check";
    this.setup();
    String actual = view.input();
    Assert.assertEquals(this.input, actual);
  }

  @Test(expected = NumberFormatException.class)
  public void testRead_InvalidNumberofStocks() {
    this.input = "hfsf";
    this.setup();
    view.readTrade();
  }

  @Test(expected = NumberFormatException.class)
  public void testRead_InvalidQuantityofStocks() {
    this.input = "1\nGOOG\nhh";
    this.setup();
    view.readTrade();
  }

  @Test
  public void testRead_SingleStock() {
    this.input = "1\nGOOG\n1";
    this.setup();
    Map<String, Double> stockData = view.readTrade();
    Assert.assertTrue(stockData.containsKey("GOOG"));
    Double expected = Double.valueOf(1);
    Assert.assertEquals(expected, stockData.get("GOOG"));
  }

  @Test
  public void testRead_MultipleStock() {
    this.input = "3\nGOOG\n1\nNOW\n2\nMSFT\n3";
    this.setup();
    Map<String, Double> stockData = view.readTrade();
    Assert.assertTrue(stockData.containsKey("GOOG"));
    Double expected1 = Double.valueOf(1);
    Assert.assertEquals(expected1, stockData.get("GOOG"));
    Assert.assertTrue(stockData.containsKey("NOW"));
    Double expected2 = Double.valueOf(2);
    Assert.assertEquals(expected2, stockData.get("NOW"));
    Assert.assertTrue(stockData.containsKey("MSFT"));
    Double expected3 = Double.valueOf(3);
    Assert.assertEquals(expected3, stockData.get("MSFT"));
  }

  private void setup() {
    InputStream in;
    in = new ByteArrayInputStream(this.input.getBytes());
    this.out = new ByteArrayOutputStream();
    view = new TextualView(in, this.out);
  }

}
