package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Features;
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
  public void addFeatures(Features features) {
    // TODO : change later
  }

  public int readMenu() {
    StringBuilder menu = new StringBuilder();
    menu.append("Main Menu :\n");
    menu.append("1.  Create a new Simulated Portfolio\n");
    menu.append("2.  Create a new Transactional Portfolio\n");
    menu.append("3.  Purchase a Stock and Add it to Transactional Portfolio\n");
    menu.append("4.  Sell a Stock from the Transactional Portfolio\n");
    menu.append("5.  Get all the available Portfolios in the Application\n");
    menu.append("6.  Get final composition for an Existing Portfolio \n");
    menu.append("7.  Get composition for an Existing Portfolio at a specific date\n");
    menu.append("8.  Get the total value on an Existing Portfolio\n");
    menu.append("9.  Get the cost basis of an Existing Transactional Portfolio\n");
    menu.append("10. Get the performance of an Existing Portfolio over a period of time\n");
    menu.append("11. Save an Existing Portfolio to file\n");
    menu.append("12. Load portfolios to the Application\n");
    menu.append("Enter the menu option you wish to choose.\n");
    menu.append("Press and enter any other key to exit the application.\n");
    this.display(menu.toString());
    try {
      String input = this.input();
      // read the menu option input by the user
      return Integer.parseInt(input);
    } catch (Exception e) {
      // if the input is illegal then returns 0
      return 0;
    }
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
    for (LocalDate key : keys) {
      Double stars = portfolioData.get(key) / scale;
      this.display(key + ": " + "*".repeat(stars.intValue()) + "\n");
    }
    this.display("Scale : * = $" + scale + "\n");
  }

  private double scale(Map<LocalDate, Double> portfolioData) {
    var max = Collections.max(portfolioData.values());
    var min = Collections.min(portfolioData.values());
    Double scale = Utility.scale(max.intValue(), min.intValue());
    return scale;
  }
}
