package model.stocktradings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import datarepo.DataRepository;
import model.portfolio.Portfolio;
import model.portfolio.PortfolioImpl;
import model.stock.Stock;
import model.trade.StockTradeImpl;
import model.trade.Trade;

/**
 * Represents portfolio trade operations such as buying trade, getting the
 * trade evaluation on a specific date.
 */
public class PortfolioTradeOperation implements TradeOperation<Portfolio> {

  private Map<String, Portfolio> portfolios;
  private final DataRepository<Portfolio> repository;

  public PortfolioTradeOperation(DataRepository<Portfolio> repository) {
    portfolios = new HashMap<>();
    this.repository = repository;
  }

  @Override
  public void buy(Portfolio portfolio) throws IllegalArgumentException {
    // if the portfolio already exist then throw a invalid argument exception
    if (this.portfolios.containsKey(portfolio.name())) {
      throw new IllegalArgumentException("The portfolio with same name exists." +
              "It cannot be changed after creation.\n");
    }
    this.portfolios.put(portfolio.name(), portfolio);
  }

  @Override
  public double value(LocalDate date, String portfolio) throws IllegalArgumentException {
    LocalDate now = LocalDate.now();
    // if the portfolio does not exist then throw an exception to let the controller know.
    if (!this.portfolios.containsKey(portfolio)) {
      throw new IllegalArgumentException("The portfolio with name provided does not exist.\n");
    }
    // if the date is after today's date then throw exception since data cannot be
    // available for a future date.
    if (date.isAfter(now)) {
      throw new IllegalArgumentException("The entered date is in future.\n");
    }
    Portfolio trade = this.portfolios.get(portfolio);
    return trade.value(date);
  }

  @Override
  public Portfolio get(String trade) throws IllegalArgumentException {
    // if the portfolio does not exist throw an exception
    if (!this.portfolios.containsKey(trade)) {
      throw new IllegalArgumentException("The portfolio with name provided does not exist.\n");
    }
    return this.portfolios.get(trade);
  }

  @Override
  public Set<String> getAllTrades() {
    return this.portfolios.keySet();
  }


  @Override
  public boolean save(String portfolio) throws IllegalArgumentException {
    try {
      // if the portfolio does not exist throw the exception
      if (!this.portfolios.containsKey(portfolio)) {
        throw new IllegalArgumentException("The portfolio with name : " + portfolio +
                " does not exist.");
      }
      // save the portfolio in the data source
      repository.save(this.portfolios.get(portfolio));
      return true;
    } catch (Exception e) {
      // Catch any exception that was faced while saving data to the data source and return
      // the return as false to let the caller know that save failed.
      return false;
    }
  }

  @Override
  public boolean load() {
    try {
      // Read the data from data source
      String data = repository.read();
      // Parse the data into required format
      List<Portfolio> portfolios = this.parse(data);
      for (Portfolio portfolio : portfolios) {
        // if the portfolio does not exist then load the portfolio in the application
        if (!this.portfolios.containsKey(portfolio.name())) {
          this.buy(portfolio);
        }
      }
      return true;
    } catch (Exception e) {
      // Catch any exception that was faced while reading data from data source and return
      // the return as false to let the caller know that load failed.
      return false;
    }

  }

  /**
   * Parses the data from data source in the required format.
   * @param data Data read from data source.
   * @return parsed data.
   */
  private List<Portfolio> parse(String data) {
    // Reads input from file and parses it
    List<Portfolio> portfolioList = new ArrayList<>();
    String[] portfolios = data.split("Portfolio Name : ");
    for (String portfolio : portfolios) {
      // if portfolio is empty then skip this one
      if (portfolio.isEmpty()) {
        continue;
      }
      //Parse all the stock trades in the portfolio.
      String[] lines = portfolio.split("\n");
      String portfolioName = lines[0];
      List<Trade<Stock>> shares = new ArrayList<>();
      for (int i = 1; i < lines.length; i++) {
        String line = lines[i];
        // Parses the stock value and quantity from stock trade
        String[] lineParsed = line.replace("Stock Symbol : ", "").
                replace("Quantity : ", "").split(",");
        Trade<Stock> share = new StockTradeImpl(lineParsed[0].trim(),
                Double.parseDouble(lineParsed[1]));
        // maintains a list of trade
        shares.add(share);
      }
      // Creates a portfolio with stock trades.
      portfolioList.add(new PortfolioImpl(portfolioName, shares));
    }
    return portfolioList;
  }
}
