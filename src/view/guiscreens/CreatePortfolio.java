package view.guiscreens;

import java.awt.*;
import javax.swing.*;

import controller.Features;

public class CreatePortfolio extends AbstractScreen {

  private final JTextField portfolioName;
  private final JLabel output;
  public CreatePortfolio() {
    super("Create Portfolio","Please enter the name of the portfolio you wish to create?");
    this.portfolioName = new javax.swing.JTextField(20);
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    this.output = new JLabel("");
    var mainPanel = new JPanel();
    //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(this.portfolioName);
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
    this.submit.addActionListener(e ->
            features.createPortfolio(this.portfolioName.getText()));
  }

}
