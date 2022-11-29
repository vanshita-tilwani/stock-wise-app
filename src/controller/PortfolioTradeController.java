package controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.datarepo.FileRepository;
import model.portfolio.Portfolio;
import model.portfolio.SimulatedPortfolio;
import model.portfolio.TransactionalPortfolio;
import model.stock.Stock;
import model.stocktradings.TradeOperation;
import model.trade.SimulatedStockTrade;
import model.trade.Trade;
import view.View;

public class PortfolioTradeController implements Features {

  private final View view;
  private final TradeOperation<Portfolio> model;

  public PortfolioTradeController(View view, TradeOperation<Portfolio> model) {
    this.view = view;
    this.model = model;
    view.addFeatures(this);
  }

  @Override
  public void createInflexiblePortfolio(String name, Map<String, Double> purchases) {
    try {
      Set<Trade<Stock>> trades = new HashSet<>();
      purchases.forEach((key, value) -> {
        try {
          trades.add(new SimulatedStockTrade(key, value));
        }
        catch (Exception e) {
          this.view.display(e.getMessage());
        }
      });
      var portfolio = new SimulatedPortfolio(name, trades);
      this.model.create(portfolio);
      this.view.display("The portfolio is created successfully!\n");
    }
    catch (NumberFormatException exception) {
      view.display("Please make sure you input valid number of stocks/quantity of stocks.\n");
    } catch (IllegalArgumentException exception) {
      view.display(exception.getMessage());
    }
    catch (DateTimeParseException ex) {
      view.display("The date provided was not in the expected format.\n");
    }
  }

  @Override
  public void createFlexiblePortfolio(String name) {
    try {
      var portfolio = new TransactionalPortfolio(name, new HashSet<>(), new HashSet<>());
      this.model.create(portfolio);
      this.view.display("The portfolio is created successfully!\n");
    }
    catch(Exception e) {
      this.view.display(e.getMessage());
    }
  }

  @Override
  public Set<String> getPortfolios() {
    Set<String> all = model.all();
    return all;
  }

  @Override
  public void buyStock(String portfolio, String stock, Double shares, LocalDate date,
                       Double commission) {
    try{
      model.get(portfolio).buy(stock, shares, date, commission);
      view.display("The purchase was completed successfully!\n");
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void sellStock(String portfolio, String stock, Double shares, LocalDate date,
                        Double commission) {
    try{
      model.get(portfolio).sell(stock, shares, date, commission);
      view.display("The sale was completed successfully!\n");
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void value(String portfolio, LocalDate date) {
    try{
      Double value = model.get(portfolio).value(date);
      view.display("The value of portfolio is $"+value+"\n");
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void costBasis(String portfolio, LocalDate date) {
    try{
      Double costBasis = model.get(portfolio).costBasis(date);
      view.display("The cost basis for the portfolio is $"+costBasis+"\n");
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public String composition(String portfolio) {
    try{
      String composition = model.get(portfolio).composition();
      return composition;
    }
    catch (Exception e) {
      return e.getMessage();
    }
  }

  @Override
  public String composition(String portfolio, LocalDate date) {
    try{
      String composition = model.get(portfolio).composition(date);
      return composition;
    }
    catch (Exception e) {
      return e.getMessage();
    }
  }

  @Override
  public void load(String dataSource) {
    try {
      FileRepository.getInstance().setDataSource(dataSource);
      model.load();
      view.display("The portfolio load completed successfully!\n");
    }
    catch (Exception e) {
      view.display("The portfolio load failed!");
    }
  }

  @Override
  public void save(String dataSource, String portfolioName) {
    try {
      FileRepository.getInstance().setDataSource(dataSource);
      model.save(portfolioName);
      view.display("The portfolio save completed successfully!\n");
    }
    catch (Exception e) {
      view.display("The portfolio save failed!");
    }
  }

  @Override
  public Map<LocalDate, Double> values(String portfolioName, LocalDate from, LocalDate end){
    return model.get(portfolioName).values(from, end);
  }
}
