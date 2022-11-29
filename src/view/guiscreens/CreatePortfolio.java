package view.guiscreens;

import java.awt.*;
import javax.swing.*;

import controller.Features;

public class CreatePortfolio extends AbstractScreen {

  private final JTextField portfolioName;
  public CreatePortfolio() {
    super("Create Portfolio",
            "Please enter the name of the portfolio you wish to create?");
    JPanel portfolioDetails = new JPanel();
    this.portfolioName = new javax.swing.JTextField(20);
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(this.portfolioName);


    var mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(portfolioDetails);
    this.add(mainPanel, BorderLayout.CENTER);

    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }



  @Override
  public void addFeatures(Features features) {
    this.submit.addActionListener(e ->
            features.createFlexiblePortfolio(this.portfolioName.getText()));
  }

}
