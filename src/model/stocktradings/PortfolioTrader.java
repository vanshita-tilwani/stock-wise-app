package model.stocktradings;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.cache.CacheProvider;
import model.datarepo.DataRepository;
import model.dataparseer.DataParser;
import model.portfolio.Portfolio;
import model.stockpriceprovider.StockDataProvider;
import model.stockpriceprovider.WebAPIStockDataProvider;

/**
 * Represents portfolio trade operations such as buying trade, getting the
 * trade evaluation on a specific date.
 */
public class PortfolioTrader implements PortfolioTradeOperation {

  private Map<String, Portfolio> portfolios;
  private final DataRepository<Portfolio> repository;

  private final DataParser<Portfolio> parser;


  public PortfolioTrader(DataRepository<Portfolio> repository, DataParser<Portfolio> parser) {
    portfolios = new HashMap<>();
    this.repository = repository;
    this.parser = parser;
  }

  @Override
  public void create(Portfolio portfolio) throws IllegalArgumentException {
    // if the portfolio already exist then throw an invalid argument exception
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
      List<Portfolio> portfolios = this.parser.parse(data);
      for (Portfolio portfolio : portfolios) {
        // if the portfolio does not exist then load the portfolio in the application
        if (!this.portfolios.containsKey(portfolio.name())) {
          this.create(portfolio);
        }
      }
      return true;
    } catch (Exception e) {
      // Catch any exception that was faced while reading data from data source and return
      // the return as false to let the caller know that load failed.
      return false;
    }

  }

  @Override
  public void buy(String trade, String stock, Double shares, LocalDate date) {
    this.portfolios.get(trade).buy(stock, shares, date);
  }

  @Override
  public void sell(String trade, String stock, Double shares, LocalDate date) {
    this.portfolios.get(trade).sell(stock, shares, date);
  }
}
