package view.guiscreens;

import java.awt.*;
import javax.swing.*;

import controller.Features;

public class CreatePortfolio extends AbstractScreen {

  private final JTextField portfolioName;
  private final JLabel output;
  public CreatePortfolio() {
    super("Create Portfolio",
            "Please enter the name of the portfolio you wish to create?");
    JPanel portfolioDetails = new JPanel();
    this.portfolioName = new javax.swing.JTextField(20);
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(this.portfolioName);

    this.output = new JLabel("");
    JPanel outputPanel = new JPanel();
    outputPanel.add(this.output);

    var mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(portfolioDetails);
    mainPanel.add(outputPanel);
    this.add(mainPanel, BorderLayout.CENTER);
    //pack();
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
    this.submit.addActionListener(e ->
            features.createFlexiblePortfolio(this.portfolioName.getText()));
  }

}
