package controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.datarepo.FileRepository;
import model.portfolio.Portfolio;
import model.portfolio.TransactionalPortfolio;
import model.stocktradings.TradeOperation;
import view.View;

public class GUIController implements Features {

  private final View view;
  // model responsible for business logic
  private final TradeOperation<Portfolio> model;

  public GUIController(View view, TradeOperation<Portfolio> model) {
    this.view = view;
    this.model = model;
    view.addFeatures(this);
  }

  @Override
  public void createPortfolio(String name) {
    try {
      var portfolio = new TransactionalPortfolio(name, new HashSet<>(), new HashSet<>());
      this.model.create(portfolio);
      this.view.display("The portfolio is created successfully!");
    }
    catch(Exception e) {
      this.view.display(e.getMessage());
    }
  }

  @Override
  public Set<String> getAllPortfolios() {
    Set<String> all = model.all();
    return all;

  }

  @Override
  public void addStockPurchaseToPortfolio(String portfolio, String stock, Double shares, LocalDate date,
                                          Double commission) {
    try{
      model.get(portfolio).buy(stock, shares, date, commission);
      view.display("The purchase is completed!");
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void sellFromPortfolio(String portfolio, String stock, Double shares, LocalDate date,
                                Double commission) {
    try{
      model.get(portfolio).sell(stock, shares, date, commission);
      view.display("The sale is completed!");
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void evaluateValue(String portfolio, LocalDate date) {
    try{
      Double value = model.get(portfolio).value(date);
      view.display("The value of the portfolio is $"+value+"!");
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void evaluateCostBasis(String portfolio, LocalDate date) {
    try{
      Double costBasis = model.get(portfolio).costBasis(date);
      view.display("The value of the portfolio is $"+costBasis+"!");
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void evaluateComposition(String portfolio, LocalDate date) {
    try{
      String composition = model.get(portfolio).composition(date).replace("\n","<br>");
      view.display("<html>" + composition);
    }
    catch (Exception e) {
      view.display(e.getMessage());
    }
  }

  @Override
  public void loadPortfolio(String dataSource) {
    try {
      FileRepository.getInstance().setDataSource(dataSource);
      model.load();
      view.display("The portfolio load completed successfully!");
    }
    catch (Exception e) {
      view.display("The portfolio load failed!");
    }
  }

  @Override
  public void savePortfolio(String dataSource, String portfolioName) {
    try {
      FileRepository.getInstance().setDataSource(dataSource);
      model.save(portfolioName);
      view.display("The portfolio save completed successfully!");
    }
    catch (Exception e) {
      view.display("The portfolio save failed!");
    }
  }

  @Override
  public Map<LocalDate, Double> getBarChartData(String portfolioName, LocalDate from, LocalDate end){
    return model.get(portfolioName).values(from, end);
  }
}
