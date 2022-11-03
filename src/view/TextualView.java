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
   * @param out Output stream to the view.
   */
  public TextualView(InputStream input, OutputStream out) {
    this.scanner = new Scanner(input);
    this.out = new PrintStream(out);
  }

  @Override
  public Map<String, Double> readTrade() throws NumberFormatException {
    this.display("Enter the number of stock trade you wish to carry out\n");
    int stocks = Integer.parseInt(this.scanner.nextLine());
    Map<String, Double> stockData = new HashMap<>();
    for (int i = 0; i < stocks; i++) {
      this.display("Enter the stock symbol you wish to buy\n");
      String stock = scanner.nextLine();
      this.display("Enter the number of shares you wish to buy\n");
      String shares = scanner.nextLine();
      Double parseDouble = Double.parseDouble(shares);
      while(parseDouble % 1.0 != 0) {
        this.display("Fractional number of shares are not allowed\n");
        this.display("Please enter the number of shares you wish to buy again\n");
        shares = scanner.nextLine();
        parseDouble = Double.parseDouble(shares);
      }
      if (stockData.containsKey(stock)) {
        stockData.put(stock, stockData.get(stock) + parseDouble);
      } else {
        stockData.put(stock, parseDouble);
      }
    }
    return stockData;
  }

  @Override
  public String input() {
    return scanner.nextLine();
  }

  @Override
  public void display(String message) {
    this.out.print(message);
  }
}
