package model.stocktradings;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import datarepo.*;
import model.portfolio.Portfolio;
import model.portfolio.PortfolioImpl;
import model.stock.Stock;
import model.trade.StockTradeImpl;
import model.trade.Trade;

public class PortfolioTradeOperation implements TradeOperation<Portfolio> {

  private Map<String, Portfolio> portfolios;
  private final DataRepository<Portfolio> repository;
  public PortfolioTradeOperation(DataRepository<Portfolio> repository) {
    portfolios = new HashMap<>();
    this.repository = repository;
  }


  @Override
  public void buy(Portfolio portfolio) {
    this.portfolios.put(portfolio.name(), portfolio);
  }

  @Override
  public double value(LocalDate date, String portfolio) {
    Portfolio trade = this.portfolios.get(portfolio);
    return trade.value(date);
  }

  @Override
  public Portfolio get(String portfolio) {
    return portfolios.get(portfolio);
  }

  @Override
  public void save(String portfolio) {
    try {
      repository.save(this.portfolios.get(portfolio));
    }
    catch(Exception e) {
      // TODO
    }
  }

  @Override
  public void load() {
    try {
      List<Portfolio> portfolios = this.parse(repository.read());
      for(Portfolio portfolio : portfolios) {
        this.buy(portfolio);
      }
    }
    catch(Exception e) {
      // TODO
    }

  }

  private List<Portfolio> parse(String data) {
    // TODO parse
    List<Portfolio> portfolioList = new ArrayList<>();
    String[] portfolios = data.split("Portfolio Name : ");
    for(String portfolio : portfolios) {
      if(portfolio.isEmpty()) {
        continue;
      }
      String[] lines = portfolio.split("\n");
      String portfolioName = lines[0];
      List<Trade<Stock>> shares = new ArrayList<>();
      for(int i = 1; i < lines.length; i ++) {
        String line = lines[i];
        String[] lineParsed = line.replace("Stock Symbol : ", "").
                replace(" Quantity : ", "").split(",");
        Trade<Stock> share = new StockTradeImpl(lineParsed[0], Double.parseDouble(lineParsed[1]));
        shares.add(share);
        System.out.println(line);
      }
      portfolioList.add(new PortfolioImpl(portfolioName, shares));
    }
    return portfolioList;
  }
}
