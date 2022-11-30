package view.guiscreens;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import controller.Features;

/**
 * A class to apply strategies to the portfolio.
 */
public class ApplyStrategyToPortfolio extends AbstractScreen {
  private final JComboBox<String> portfolioName;
  private final JComboBox<String> strategyName;

  /**
   * Constructor to initialise the variables.
   */
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

    var mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    mainPanel.add(portfolioDetails);
    mainPanel.add(strategyDetails);
    this.add(mainPanel, BorderLayout.CENTER);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    features.getPortfolios().forEach(e -> this.portfolioName.addItem(e));
    features.getAllStrategy().forEach(e -> this.strategyName.addItem(e));
    this.submit.addActionListener(f -> {
      if (this.isInputsValid()) {
      features.applyStrategy(
            this.get(this.portfolioName),
            this.get(this.strategyName)
    ); } } );
  }

  private String get(JComboBox<String> comboBoxList) {
    int index = comboBoxList.getSelectedIndex();
    return comboBoxList.getItemAt(index);
  }

  private boolean isInputsValid() {
    if (this.get(this.portfolioName) == null) {
        this.error("Invalid Portfolio Selected. Please select a portfolio and try again");
        return false;
    }
    if (this.get(this.strategyName) == null) {
      this.error("Invalid Strategy Selected. Please select a strategy and try again");
      return false;
    }
    return true;
  }
}
