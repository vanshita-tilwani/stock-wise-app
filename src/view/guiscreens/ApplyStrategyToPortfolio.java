package view.guiscreens;

import java.awt.*;

import javax.swing.*;

import controller.Features;

public class ApplyStrategyToPortfolio extends AbstractScreen {
  private final JComboBox<String> portfolioName;
  private final JComboBox<String> strategyName;
  private final JLabel output;
  public ApplyStrategyToPortfolio() {
    super("Trading Window - Apply Strategy to Portfolio", "");

    JPanel portfolioDetails = new JPanel();
    this.portfolioName = new JComboBox<String>();
    var nameLabel = new JLabel("Enter the portfolio name : ");
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(nameLabel);
    portfolioDetails.add(this.portfolioName);

    JPanel strategyDetails = new JPanel();
    this.strategyName = new JComboBox<String>();
    var strategyLabel = new JLabel("Enter the strategy name : ");
    this.strategyName.setToolTipText("Enter the strategy name");
    strategyDetails.add(strategyLabel);
    strategyDetails.add(this.strategyName);

    this.output = new JLabel();

    var mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    mainPanel.add(portfolioDetails);
    mainPanel.add(strategyDetails);
    mainPanel.add(this.output);
    this.add(mainPanel, BorderLayout.CENTER);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void display(String text) {
    this.setOutputText(text);
    this.output.setForeground(Color.GREEN);
  }

  @Override
  public void error(String text) {
    this.setOutputText(text);
    this.output.setForeground(Color.RED);
  }

  private void setOutputText(String text) {
    this.output.setText(text);
  }

  @Override
  public void addFeatures(Features features) {
    features.getPortfolios().forEach(e -> this.portfolioName.addItem(e));
    features.getAllStrategy().forEach(e -> this.strategyName.addItem(e));
    this.submit.addActionListener(f -> features.applyStrategy(
            this.get(this.portfolioName),
            this.get(this.strategyName)
    ));
  }

  private String get(JComboBox<String> comboBoxList) {
    int index = comboBoxList.getSelectedIndex();
    return comboBoxList.getItemAt(index);
  }
}
