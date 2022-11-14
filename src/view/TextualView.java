package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

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
    portfolioData.forEach((key, value) -> {
      this.display(key + ": " + "*".repeat(value.intValue() / 1000));
    });
    this.display("Scale : * = $1000");
  }
}
