package view;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class CommandPromptView implements View {

  private final Scanner scanner;

  public CommandPromptView() {
    this.scanner = new Scanner(System.in).useLocale(Locale.US);
  }
  @Override
  public Map<String, Double> read() {
    this.display("Enter the number of stock trade you wish to carry out\n");
    int stocks = Integer.parseInt(this.scanner.nextLine());
    Map<String, Double> stockData = new HashMap<>();
    for(int i = 0; i < stocks; i ++) {
      this.display("Enter the stock symbol you wish to buy\n");
      String stock = scanner.nextLine();
      this.display("Enter the number of shares you wish to buy\n");
      double value = Double.parseDouble(scanner.nextLine());
      stockData.put(stock, value);
    }
    return stockData;
  }

  @Override
  public String input() {
    return scanner.nextLine();
  }
  @Override
  public void display(String message) {
    System.out.print(message);
  }
}
