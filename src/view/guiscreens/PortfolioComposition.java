package view.guiscreens;

import java.time.LocalDate;

import controller.Features;

public class PortfolioComposition extends AbstractEvaluationScreen {

  public PortfolioComposition() {
    super("Trading Application - Portfolio Composition Window");
  }

  @Override
  public void evaluateTrade(Features features, String portfolioName, LocalDate date) {
    String composition = features.composition(portfolioName, date);
    String finalComposition = "<html>" + composition.replace("\n","<br>");
    this.display(finalComposition);
  }

}
