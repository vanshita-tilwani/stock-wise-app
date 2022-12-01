package view.guiscreens;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents a screen to create dollar cost avg portfolio.
 */
public class DollarCostAveragingPortfolio extends RecurringInvestmentStrategy {

  private JTextField portfolioName;

  /**
   * Initializes a screen to create dollar cost avg portfolio.
   */
  public DollarCostAveragingPortfolio() {
    super();
    this.portfolioName = this.createTextField("Enter the portfolio name");
    this.remove(this.mainPanel);
    this.remove(this.mainPanel);
    this.mainPanel = initMainPanel();
    var panel = new JPanel();
    panel.add(new JLabel("Enter the name of the portfolio : "));
    this.portfolioName = this.createTextField("Enter the portfolio name");
    panel.add(this.portfolioName);
    this.mainPanel.add(panel);
    this.add(mainPanel, BorderLayout.CENTER);
    renderFrame();
  }

  @Override
  protected String tradeName() {
    return this.portfolioName.getText();
  }

}
