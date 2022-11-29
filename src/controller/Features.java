package controller;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface Features {

  void createInflexiblePortfolio(String name , Map<String, Double> purchases);
  void createFlexiblePortfolio(String name);
  Set<String> getPortfolios();

  void buyStock(String portfolio, String stock, Double shares, LocalDate date,
                Double commission);

  void sellStock(String portfolio, String stock, Double shares, LocalDate date,
                 Double commission);

  void value(String portfolio, LocalDate date);

  void costBasis(String portfolio, LocalDate date);

  String composition(String portfolio);

  String composition(String portfolio, LocalDate date);

  void load(String dataSource);

  void save(String dataSource, String portfolioName);

  Map<LocalDate, Double> values(String portfolioName, LocalDate from, LocalDate end);

  void invest(String portfolioName, Double principal, Map<String, Double> weights, LocalDate date,
              Double commission);

  void invest(String portfolioName, Double principal, Map<String, Double> weights, LocalDate start,
              LocalDate end, int days, Double commission);
}
