package controller;

import java.time.LocalDate;

public interface Features {

  void createPortfolio(String name);
  void getAllPortfolios();

  void addStockPurchaseToPortfolio(String portfolio, String stock, Double shares, LocalDate date,
                                   Double commission);

  void sellFromPortfolio(String portfolio, String stock, Double shares, LocalDate date,
                         Double commission);

  void evaluateValue(String portfolio, LocalDate date);

  void evaluateCostBasis(String portfolio, LocalDate date);

  void evaluateComposition(String portfolio, LocalDate date);
}
