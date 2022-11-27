package view.guiscreens;

import java.awt.*;

import javax.swing.*;

import controller.Features;

public class AllPortfolio extends AbstractScreen {

  private final JLabel output;
  public AllPortfolio() {
    super("Show All Portfolios",
            "The following portfolios exist in the application");
    this.output = new JLabel("");
    var mainPanel = new JPanel();
    mainPanel.add(this.output);
    this.add(mainPanel, BorderLayout.CENTER);
    //pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void display(String text) {
    this.output.setText(text);
  }

  @Override
  public void addFeatures(Features features) {
    this.submit.addActionListener(e -> features.getAllPortfolios());
  }

  @Override
  public String getPortfolioName() {
    return null;
  }

  @Override
  public void setVisibility(boolean visible) {
    setVisible(visible);
  }
}
