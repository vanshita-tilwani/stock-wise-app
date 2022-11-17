import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;

import view.TextualView;
import view.View;

/**
 * Testing the TextualView class.
 */
public class TextualViewTest {

  private OutputStream out;
  private View view;

  @Test
  public void testDisplay() {
    String input = "Test Check";
    this.setup(input);
    view.display(input);
    Assert.assertEquals(input, this.out.toString());
  }

  @Test
  public void testInput() {
    String input = "Test Check";
    this.setup(input);
    String actual = view.input();
    Assert.assertEquals(input, actual);
  }

  @Test
  public void testDraw() {
    String expected = "2022-11-16: *****\n" +
            "2022-11-17: **********\n" +
            "2022-11-18: ***************\n" +
            "2022-11-19: ********************\n" +
            "2022-11-20: *************************\n" +
            "Scale : * = $0.2\n";
    this.setup("");
    HashMap<LocalDate, Double> map = new HashMap<>();
    LocalDate date = LocalDate.parse("2022-11-16");
    map.put(date, 1.0);
    map.put(date.plusDays(1), 2.0);
    map.put(date.plusDays(2), 3.0);
    map.put(date.plusDays(3), 4.0);
    map.put(date.plusDays(4), 5.0);

    this.view.draw(map);
    Assert.assertEquals(expected, this.out.toString());
  }

  private void setup(String input) {
    InputStream in;
    in = new ByteArrayInputStream(input.getBytes());
    this.out = new ByteArrayOutputStream();
    view = new TextualView(in, this.out);
  }

}
