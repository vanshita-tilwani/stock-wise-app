package view.guiscreens;

import java.time.LocalDate;
import controller.Features;

public class PortfolioCostBasis extends AbstractEvaluationScreen {

  public PortfolioCostBasis() {
    super("Trading Application - Portfolio Cost Basis Window");
  }

  @Override
  public void evaluateTrade(Features features, String portfolioName, LocalDate date) {
    features.costBasis(portfolioName, date);
  }
}
