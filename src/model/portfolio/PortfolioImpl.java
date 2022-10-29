package model.portfolio;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import model.trade.Transaction;

public class PortfolioImpl implements Portfolio {
  private final String name;
  private final List<Transaction> shares;

  public PortfolioImpl(String name, List<Transaction> shares) {
    this.name = name;
    this.shares = shares;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public double value(LocalDate date) {
    return 0;
  }

  @Override
  public void save() {

  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("The portfolio with ").append(this.name).append(" contains the following stocks :\n");
    for(Transaction share : shares) {
      sb.append(share.toString()).append('\n');
    }
    return sb.toString();
  }
}
