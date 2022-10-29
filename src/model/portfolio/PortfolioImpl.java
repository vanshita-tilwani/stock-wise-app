package model.portfolio;

import java.util.Date;

import model.trade.Transaction;

public class PortfolioImpl implements Portfolio {
  private final String name;
  private final Transaction[] shares;

  public PortfolioImpl(String name, Transaction[] shares) {
    this.name = name;
    this.shares = shares;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public String composition() {
    return null;
  }

  @Override
  public double value(Date date) {
    return 0;
  }

  @Override
  public void save() {

  }
}
