package view.guiscreens;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;

import controller.Features;

public class PortfolioCostBasis extends AbstractScreen{
  private final JTextField portfolioName;
  private final JTextField date;
  private final JLabel output;
  public PortfolioCostBasis() {
    super("Portfolio Value", "Enter the data for the portfolio cost basis evaluation");
    JPanel portfolioDetails = new JPanel();
    this.portfolioName = new javax.swing.JTextField(20);
    var nameLabel = new JLabel("Enter the portfolio name : ");
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(nameLabel);
    portfolioDetails.add(this.portfolioName);
    JPanel evaluationData = new JPanel();
    this.date = new javax.swing.JTextField(8);
    this.date.setToolTipText("Enter the date of evaluation");
    evaluationData.add(new JLabel("Enter the date of evaluation of the portfolio : "));
    evaluationData.add(this.date);
    this.output = new JLabel("");

    var mainPanel = new JPanel();
    mainPanel.add(new JScrollPane());

    mainPanel.add(portfolioDetails);
    mainPanel.add(evaluationData);
    mainPanel.add(this.output);
    this.add(mainPanel, BorderLayout.CENTER);
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
    this.submit.addActionListener(e -> features.evaluateCostBasis(this.portfolioName.getText(),
            LocalDate.parse(this.date.getText())));
  }
}
