package model.stocktradings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.strategy.Strategy;

public class StrategyOperation implements TradeOperation<Strategy> {

  private Map<String, Strategy> strategies;
  public StrategyOperation() {
    this.strategies = new HashMap<>();
  }

  @Override
  public void create(Strategy trade) throws IllegalArgumentException {
    if (this.strategies.containsKey(trade)) {
      throw new IllegalArgumentException("The strategy already exists in the application.\n");
    }
    this.strategies.put(trade.name(), trade);
  }

  @Override
  public Strategy get(String trade) throws IllegalArgumentException {
    return this.strategies.get(trade);
  }

  @Override
  public Set<String> all() {
    return this.strategies.keySet();
  }

  @Override
  public void save(String trade) throws Exception {
    // NOT SUPPORTED AS OF NOW
  }

  @Override
  public void load() throws Exception {
    // NOT SUPPORTED AS OF NOW
  }
}
