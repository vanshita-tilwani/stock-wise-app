package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.utility.Utility;

/**
 * Implementation of View interface which used
 * input stream and output stream to read and
 * display data respectively.
 */
public class TextualView implements View {
  private final Scanner scanner;
  private final PrintStream out;

  /**
   * Creates an instance of textual based view with
   * input stream and output stream.
   *
   * @param input Input stream to the view.
   * @param out   Output stream to the view.
   */
  public TextualView(InputStream input, OutputStream out) {
    this.scanner = new Scanner(input);
    this.out = new PrintStream(out);
  }

  @Override
  public String input() {
    // reads the user input and returns.
    return scanner.nextLine();
  }

  @Override
  public void display(String message) {
    // displays the information to the user
    this.out.print(message);
  }

  @Override
  public void draw(Map<LocalDate, Double> portfolioData) {
    Double scale = scale(portfolioData);
    List<LocalDate> keys = new ArrayList<>(portfolioData.keySet());
    Collections.sort(keys);
    for(LocalDate key : keys) {
      Double stars = portfolioData.get(key)/scale;
      this.display(key + ": " + "*".repeat(stars.intValue())+ "\n");
    }
    this.display("Scale : * = $"+scale+"\n");
  }

  private double scale(Map<LocalDate, Double> portfolioData) {
    var max = Collections.max(portfolioData.values());
    var min = Collections.min(portfolioData.values());
    Double scale = Utility.scale(max.intValue(), min.intValue());
    return scale;
  }
}
