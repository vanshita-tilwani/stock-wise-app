package model.portfolio;

import java.util.Date;

public class Portfolio implements IPortfolio {
  private final String name;

  public Portfolio(String name) {
    this.name = name;
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
