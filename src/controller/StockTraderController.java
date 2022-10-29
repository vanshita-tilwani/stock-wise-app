package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import model.portfolio.Portfolio;
import model.portfolio.PortfolioImpl;
import model.stocktradings.Trade;
import model.trade.Transaction;
import model.trade.TransactionImpl;
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
    String menuOptions = this.getMenuOptions();
    int menuOption = 0;
    while(menuOption !=3) {
      view.display(menuOptions);
      menuOption = Integer.parseInt(view.input());
      switch (menuOption) {
        case 1:
          model.buy(create());
          break;
        case 2:
          view.display("Enter the number of the portfolio you wish to retrieve\n");
          String name = view.input();
          view.display(model.get(name).toString());
          break;
        default:
          break;
      }
    }
  }

  private String getMenuOptions() {
    StringBuilder menu = new StringBuilder();
    menu.append("Main Menu :");
    menu.append("1. Create Portfolio\n");
    menu.append("2. Get an existing Portfolio composition\n");
    menu.append("3. Exit the application\n");
    menu.append("Enter the menu option you wish to choose\n");
    return menu.toString();
  }

  private Portfolio create() {
    view.display("Enter the name of the portfolio you wish to create\n");
    String name = view.input();
    Map<String, Double> stockData = view.read();
    Set<String> stocks = stockData.keySet();
    List<Transaction> shares = new ArrayList<>();
    for(String stock : stocks) {
      shares.add(new TransactionImpl(stock, stockData.get(stock)));
    }
    return new PortfolioImpl(name, shares);
  }
}
