package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Features;
import model.utility.Utility;

public class TextualView implements View {

  Map<Integer, Runnable> commands;
  private final Scanner scanner;
  private final PrintStream out;

  public TextualView(InputStream input, OutputStream out) {
    this.scanner = new Scanner(input);
    this.out = new PrintStream(out);
    commands = new HashMap<>();
  }

  @Override
  public void addFeatures(Features features) {
    this.initializeFeatures(features);
    while(true) {
      String menuOptions = "Main Menu :\n" +
              "1.  Create a new Simulated Portfolio\n" +
              "2.  Create a new Transactional Portfolio\n" +
              "3.  Purchase a Stock and Add it to Transactional Portfolio\n" +
              "4.  Sell a Stock from the Transactional Portfolio\n" +
              "5.  Get all the available Portfolios in the Application\n" +
              "6.  Get final composition for an Existing Portfolio \n" +
              "7.  Get composition for an Existing Portfolio at a specific date\n" +
              "8.  Get the total value on an Existing Portfolio\n" +
              "9.  Get the cost basis of an Existing Transactional Portfolio\n" +
              "10. Get the performance of an Existing Portfolio over a period of time\n" +
              "11. Save an Existing Portfolio to file\n" +
              "12. Load portfolios to the Application\n" +
              "Enter the menu option you wish to choose.\n" +
              "Press and enter any other key to exit the application.\n";
      this.display(menuOptions);
      int menu = this.readMenu();
      if (!commands.containsKey(menu)) {
        this.display("You have decided to exit the application. See you next time\n");
        break;
      }
      commands.get(menu).run();
    }

  }

  private void initializeFeatures(Features features) {
    commands.put(1, () -> { features.createInflexiblePortfolio(this.readTradeName(),
            this.readTradeData());});
    commands.put(2, () -> { features.createFlexiblePortfolio(this.readTradeName());});
    commands.put(3, () -> { features.buyStock(this.readTradeName(),
            this.readStockSymbol(),
            this.readShares(),
            this.readDateOfTransaction(),
            this.readCommissionFee());});
    commands.put(4, () -> {features.sellStock(this.readTradeName(),
            this.readStockSymbol(),
            this.readShares(),
            this.readDateOfTransaction(),
            this.readCommissionFee());});
    commands.put(5, () -> {
      var portfolios = features.getPortfolios();
      String result = portfolios.size() > 0 ?
              "Portfolio Names : \n" + String.join("\n", portfolios)
              : "The application does not contain any portfolio.\n";
      this.display(result);
    });
    commands.put(6, () -> {
      var composition = features.composition(this.readTradeName());
      this.display(composition);
    });
    commands.put(7, () -> {
      var composition = features.composition(this.readTradeName(),
            this.readDateOfEvaluation());
      this.display(composition);
    });
    commands.put(8, () -> {features.value(this.readTradeName(),
            this.readDateOfEvaluation());});
    commands.put(9, () -> {features.costBasis(this.readTradeName(),
            this.readDateOfEvaluation());});
    commands.put(10, () -> {
      var portfolio = this.readTradeName();
      LocalDate from = this.readDateOfEvaluation();
      LocalDate to = this.readDateOfEvaluation();
      var values = features.values(portfolio,
            from,
            to);
      this.display("Performance of portfolio " + portfolio + " from " + from + " to " + to + "\n");
      this.drawGraph(values);
    });
    commands.put(11, () -> features.save("res/portfolio.txt",this.readTradeName()));
    commands.put(12, () -> features.load("res/portfolio.txt"));

  }

  @Override
  public int readMenu() {
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
    return scanner.nextLine();
  }

  @Override
  public void display(String message) {
    this.out.print(message);
  }

  @Override
  public void draw(Map<LocalDate, Double> portfolioData) {

  }

  private void drawGraph(Map<LocalDate, Double> portfolioData) {
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
  private String readTradeName() {
    this.display("Enter the name of the portfolio\n");
    String portfolio = this.input();
    while (portfolio == null || portfolio.isBlank() || portfolio.isEmpty()) {
      this.display("You have entered an invalid name. Please enter the name again\n");
      portfolio = this.input().trim();
    }
    return portfolio;
  }

  private Double readShares() {
    this.display("Enter the number of shares\n");
    boolean parsed = false;
    Double shares = 0.0;
    while (!parsed) {
      try {
        String input = this.input();
        shares = Double.parseDouble(input);
        parsed = true;
      } catch (NumberFormatException e) {
        this.display("Invalid number of shares\n");
        this.display("Please enter the number of shares again.\n");
      }
    }
    // fractional shares are not allowed
    // If shares are fractional then user is asked to input the number of shares again
    // until he/she enters a whole number.
    while (shares % 1.0 != 0) {
      this.display("Fractional number of shares are not allowed\n");
      this.display("Please enter the number of shares again\n");
      String input = this.input();
      shares = Double.parseDouble(input);
    }
    return shares;
  }

  private String readStockSymbol() {
    this.display("Enter the stock symbol\n");
    String stock = this.input();
    return stock;
  }

  private LocalDate readDateOfTransaction() {
    this.display("Enter the date of the transaction (in YYYY-MM-DD format)\n");
    while (true) {
      try {
        LocalDate date = LocalDate.parse(this.input());
        return date;
      } catch (DateTimeParseException e) {
        this.display("You have entered an invalid date.Please re-enter the date\n");
      }
    }
  }

  private LocalDate readDateOfEvaluation() {
    this.display("Enter the date of the evaluation (in YYYY-MM-DD format)\n");
    while (true) {
      try {
        LocalDate date = LocalDate.parse(this.input());
        return date;
      } catch (DateTimeParseException e) {
        this.display("You have entered an invalid date. Please re-enter the date\n");
      }
    }
  }

  private double readCommissionFee() {
    this.display("Enter the commission fee for this transaction\n");
    while (true) {
      try {
        Double commission = Double.parseDouble(this.input());
        if (commission >= 0.0) {
          return commission;
        }
        this.display("You have entered an invalid commission fee.Please re-enter the fee\n");
      } catch (NumberFormatException e) {
        this.display("You have entered an invalid commission fee.Please re-enter the fee\n");
      }
    }
  }

  private Map<String, Double> readTradeData() {
    Map<String, Double> stockData = new HashMap<>();
    // reads number of trades that the portfolio is composed of.
    int stocks = this.readNumberOfStocks();
    for (int i = 0; i < stocks; i++) {
      // reads the stock symbol
      String stock = this.readStockSymbol();
      // reads the number of shares.
      Double shares = this.readShares();
      stockData.put(stock, stockData.getOrDefault(stock,0.0)+shares);
    }
    // parse the map to set of trades and return the resultant
    return stockData;
  }

  private int readNumberOfStocks() {
    this.display("Enter the number of stocks you wish to purchase\n");
    int stocks = Integer.parseInt(this.input());
    return stocks;
  }
}
