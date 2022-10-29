package controller;

import java.util.Date;

import model.stocktradings.Trade;
import view.View;

public class StockTraderController implements TradeController {

  private final View view;
  private final Trade model;

  public StockTraderController(View view, Trade portfolio) {
    this.view = view;
    this.model = portfolio;
  }

  @Override
  public void execute() {

  }

  @Override
  public void create() {

  }

  @Override
  public void composition() {

  }

  @Override
  public void value(Date date) {

  }

  @Override
  public void save() {

  }
}
