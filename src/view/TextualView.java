package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextualView implements View {
  private final Scanner scanner;
  private final PrintStream out;

  public TextualView(InputStream input, OutputStream out) {
    this.scanner = new Scanner(input);
    this.out = new PrintStream(out);
  }

  @Override
  public Map<String, Double> read() throws NumberFormatException {
    this.display("Enter the number of stock trade you wish to carry out\n");
    int stocks = Integer.parseInt(this.scanner.nextLine());
    Map<String, Double> stockData = new HashMap<>();
    for (int i = 0; i < stocks; i++) {
      this.display("Enter the stock symbol you wish to buy\n");
      String stock = scanner.nextLine();
      this.display("Enter the number of shares you wish to buy\n");
      double value = Double.parseDouble(scanner.nextLine());
      if (stockData.containsKey(stock)) {
        stockData.put(stock, stockData.get(stock) + value);
      } else {
        stockData.put(stock, value);
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
