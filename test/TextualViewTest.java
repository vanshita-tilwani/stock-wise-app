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





  private void setup() {
    InputStream in;
    in = new ByteArrayInputStream(this.input.getBytes());
    this.out = new ByteArrayOutputStream();
    view = new TextualView(in, this.out);
  }

}
