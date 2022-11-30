package view.guiscreens;

import java.time.LocalDate;
import controller.Features;

public class PortfolioValue extends AbstractEvaluationScreen {
  public PortfolioValue() {
    super("Trading Application - Portfolio Value Window");
  }

  @Override
  public void evaluateTrade(Features features, String portfolioName, LocalDate date) {
    features.value(portfolioName, date);
  }
}
