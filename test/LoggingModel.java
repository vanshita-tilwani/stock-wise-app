import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.portfolio.Portfolio;
import model.portfolio.SimulatedPortfolio;
import model.stocktradings.TradeOperation;

public class LoggingModel implements TradeOperation<Portfolio> {
  private StringBuilder log;

  public LoggingModel(StringBuilder log) {
    this.log=log;
  }

  @Override
  public Map<LocalDate, Double> analyze(String portfolio, LocalDate from, LocalDate to) throws IllegalArgumentException {
    log.append("Analyze Portfolio:"+portfolio+" From Date:"+from+" To Date:"+to+"\n" );
    return null;
  }

  @Override
  public void create(Portfolio trade) throws IllegalArgumentException {
    log.append("Received : addPortfolio\n"+trade.toString());

  }

  @Override
  public double value(String portfolio, LocalDate date) throws IllegalArgumentException {
    log.append("Value called for "+ portfolio + " at "+ date +"\n");
    return 0;
  }

  @Override
  public Portfolio get(String trade) throws IllegalArgumentException {
    log.append("Get composition method called for " + trade + "\n");
    return new SimulatedPortfolio(trade, new HashSet<>());
  }

  @Override
  public Set<String> all() {
    log.append("getAllTrades called\n");
    return new HashSet<>();
  }

  @Override
  public boolean save(String trade) throws IllegalArgumentException {
    log.append("Save portfolio : "+ trade+"\n");
    return true;
  }

  @Override
  public boolean load() {
    log.append("Load all the portfolio in the data source\n");
    return false;
  }
}
