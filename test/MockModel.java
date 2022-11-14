import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import model.datarepo.DataRepository;
import model.portfolio.Portfolio;
import model.stocktradings.PortfolioTradeOperation;

public class MockModel implements PortfolioTradeOperation {
  private StringBuilder log;
  private final DataRepository<Portfolio> repository;

  public MockModel(DataRepository<Portfolio> repository, StringBuilder log) {
    this.repository=repository;
    this.log=log;
  }


  @Override
  public void buyStock(String portfolio, String stock, Double shares, LocalDate date, Double commission) throws IllegalArgumentException, UnsupportedOperationException {
    log.append("Buy Portfolio:" +portfolio+" Stock:"+stock+" Shares:"+shares+" Date:"+date+" Commission:"+commission+"\n" );
  }

  @Override
  public void sellStock(String portfolio, String stock, Double shares, LocalDate date, Double commission) throws IllegalArgumentException, UnsupportedOperationException {
    log.append("Sell Portfolio:"+portfolio+" Stock:"+stock+" Shares:"+shares+" Date:"+date+" Commission:"+commission+"\n" );

  }

  @Override
  public Map<LocalDate, Double> analyzePortfolio(String portfolio, LocalDate from, LocalDate to) throws IllegalArgumentException {
    log.append("Analyze Portfolio:"+portfolio+" From Date:"+from+" To Date:"+to+"\n" );
    return null;
  }

  @Override
  public void create(Portfolio trade) throws IllegalArgumentException {
    log.append("Create method called\n");
  }

  @Override
  public double value(LocalDate date, String trade) throws IllegalArgumentException {
    log.append("Value method called\n");
    return -10000.0;
  }

  @Override
  public Portfolio get(String trade) throws IllegalArgumentException {
    log.append("get method called\n");
    return null;
  }

  @Override
  public Portfolio get(String trade, LocalDate date) throws IllegalArgumentException {
    log.append("get method based on date called\n");
    return null;
  }

  @Override
  public Set<String> getAllTrades() {
    log.append("getAllTrades called\n");
    return (Set<String>) new ArrayList<String>();
  }

  @Override
  public boolean save(String trade) throws IllegalArgumentException {
    log.append("save portfolio:"+ trade+"\n");
    return false;
  }

  @Override
  public boolean load() {
    log.append("load method called\n");
    return false;
  }
}
