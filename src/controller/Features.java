package controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface Features {

  void createPortfolio(String name);
  Set<String> getAllPortfolios();

  void addStockPurchaseToPortfolio(String portfolio, String stock, Double shares, LocalDate date,
                                   Double commission);

  void sellFromPortfolio(String portfolio, String stock, Double shares, LocalDate date,
                         Double commission);

  void evaluateValue(String portfolio, LocalDate date);

  void evaluateCostBasis(String portfolio, LocalDate date);

  void evaluateComposition(String portfolio, LocalDate date);

  void loadPortfolio(String dataSource);
  void savePortfolio(String dataSource, String portfolioName);

  Map<LocalDate, Double> getBarChartData(String portfolioName, LocalDate from, LocalDate end);
}
