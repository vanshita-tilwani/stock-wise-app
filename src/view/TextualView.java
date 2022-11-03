package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
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
  public Map<String, Double> readTrade() throws NumberFormatException {
    // Get the number of trades in a portfolio
    this.display("Enter the number of stock trade you wish to carry out\n");
    int stocks = Integer.parseInt(this.scanner.nextLine());
    Map<String, Double> stockData = new HashMap<>();
    // Loop on the number of trades in a portfolio to get all the inputs
    for (int i = 0; i < stocks; i++) {
      // read the stock in a trade
      this.display("Enter the stock symbol you wish to buy\n");
      String stock = scanner.nextLine();
      // read the number of shares of a stock in a given trade
      this.display("Enter the number of shares you wish to buy\n");
      String shares = scanner.nextLine();
      // fractional shares are not allowed
      // If shares are fractional then user is asked to input the number of shares again
      // until he/she enters a whole number.
      Double parseDouble = Double.parseDouble(shares);
      while (parseDouble % 1.0 != 0) {
        this.display("Fractional number of shares are not allowed\n");
        this.display("Please enter the number of shares you wish to buy again\n");
        shares = scanner.nextLine();
        parseDouble = Double.parseDouble(shares);
      }
      // enter the trade information in the trade list
      if (stockData.containsKey(stock)) {
        // if stock already exists, then consolidate the amount of shares.
        stockData.put(stock, stockData.get(stock) + parseDouble);
      } else {
        stockData.put(stock, parseDouble);
      }
    }
    return stockData;
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
}
