package model.trade;

import java.util.Date;

import model.stock.StockImpl;

public class TransactionImpl implements Transaction{
  @Override
  public StockImpl stock() {
    return null;
  }

  @Override
  public Date purchaseDate() {
    return null;
  }

  @Override
  public double count() {
    return 0;
  }
}
