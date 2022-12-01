package view.guiscreens;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Features;

public class DollarCostAveragingPortfolio extends RecurringInvestmentStrategy {

  private JTextField portfolioName;
  private boolean isInit = false;

  public DollarCostAveragingPortfolio(){
    super();
    this.portfolioName = this.createTextField("Enter the portfolio name");
    this.isInit = true;
    this.remove(this.mainPanel);
    //this.mainPanel = initMainPanel();
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
