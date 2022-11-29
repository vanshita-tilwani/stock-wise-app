package controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import model.datarepo.FileRepository;
import model.portfolio.Portfolio;
import model.portfolio.SimulatedPortfolio;
import model.portfolio.TransactionalPortfolio;
import model.stock.Stock;
import model.stockpriceprovider.StockDataProvider;
import model.stockpriceprovider.WebAPIStockDataProvider;
import model.stocktradings.TradeOperation;
import model.trade.SimulatedStockTrade;
import model.trade.Trade;
import model.trade.TransactionalStockTrade;
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
      if (trades.size() > 0) {
        // asks the model to buy the portfolio if there are any valid shares
        var portfolio = new SimulatedPortfolio(name, trades);
        this.model.create(portfolio);
        view.display("Portfolio created successfully.\n");
      } else {
        // if there are no valid shares, inform the user
        view.display("Portfolio could not be created since all the shares in the "
                + "portfolio are Invalid.\n");
      }
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
    catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    } catch (IllegalArgumentException exception) {
      view.display(exception.getMessage());
    }
  }

  @Override
  public void sellStock(String portfolio, String stock, Double shares, LocalDate date,
                        Double commission) {
    try{
      model.get(portfolio).sell(stock, shares, date, commission);
      view.display("The sale was completed successfully!\n");
    }
    catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    }
    catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void value(String portfolio, LocalDate date) {
    try{
      Double value = model.get(portfolio).value(date);
      view.display("The value of portfolio is $"+value+"\n");
    }
    catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void costBasis(String portfolio, LocalDate date) {
    try{
      Double costBasis = model.get(portfolio).costBasis(date);
      view.display("The cost basis for the portfolio is $"+costBasis+"\n");
    }
    catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
    catch (DateTimeParseException ex) {
      view.display("The date provided was not in the expected format.\n");
    }
  }

  @Override
  public String composition(String portfolio) {
    try{
      String composition = model.get(portfolio).composition();
      return composition;
    }
    catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  @Override
  public String composition(String portfolio, LocalDate date) {
    try{
      String composition = model.get(portfolio).composition(date);
      return composition;
    }
    catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  @Override
  public void load(String dataSource) {
    try {
      FileRepository.getInstance().setDataSource(dataSource);
      model.load();
      view.display("The load of portfolio is successfully completed\n");
    }
    catch (Exception e) {
      view.display("There were issues with portfolio load. Please make sure the file is in"
              + "expected format.\n");
    }
  }

  @Override
  public void save(String dataSource, String portfolioName) {
    try {
      FileRepository.getInstance().setDataSource(dataSource);
      model.save(portfolioName);
      view.display("The portfolio saved to file successfully\n");
    }
    catch (Exception e) {
      view.display("The save could not be completed. Please make sure the portfolio name"
              + " is entered correctly and the data source(file) exist.\n");
    }
  }

  @Override
  public Map<LocalDate, Double> values(String portfolioName, LocalDate from, LocalDate end) {
    try {
      return model.get(portfolioName).values(from, end);
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    } catch (DateTimeParseException ex) {
      view.display("The date provided was not in the expected format.\n");
    }
    return null;
  }

  @Override
  public void invest(String portfolioName, Double principal, Map<String, Double> weights,
                     LocalDate date, Double commission) {
    double totalWeight = 0.0;
    for (Map.Entry<String, Double> entry : weights.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();
      totalWeight += value;
      if(totalWeight > 100.0) {
        throw new IllegalArgumentException("The one time investment failed due to invalid" +
                " weights\n");
      }
      try {
        var trade = new TransactionalStockTrade(key, principal, value, date, commission);
        model.get(portfolioName).buy(trade.get().name(), trade.quantity(),
                trade.tradeDate(), trade.commission());
      }
      catch (UnsupportedOperationException e) {
        view.display(e.getMessage());
      } catch (IllegalArgumentException exception) {
        view.display(exception.getMessage());
      }
    }
  }

  @Override
  public void invest(String portfolioName, Double principal, Map<String, Double> weights,
                     LocalDate start, LocalDate end, int days, Double commission) {
    LocalDate currDate = start;
    LocalDate presentDate = LocalDate.now();
    if(presentDate.isBefore(end) || end == null) {
      end = presentDate;
    }
    while(!(currDate.isAfter(end))) {
      for (Map.Entry<String, Double> entry : weights.entrySet()) {
        var trade = new TransactionalStockTrade(entry.getKey(), principal, entry.getValue(),
                currDate, commission);
        model.get(portfolioName).buy(trade.get().name(), trade.quantity(),
                trade.tradeDate(), trade.commission());
      }
      currDate = currDate.plusDays(days);
    }

  }
}
