package view.guiscreens;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JFrame;

import controller.Features;

/**
 * A class to create portfolio in the gui.
 */
public class CreatePortfolio extends AbstractScreen {

  private final JTextField portfolioName;

  /**
   * Constructor to initialise the variables.
   */
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
    this.submit.addActionListener(e -> {
      if (isInputsValid()) {
          features.createFlexiblePortfolio(this.portfolioName.getText());
      }
      else {
        this.error("The portfolio name is invalid. Please input again and press submit.");
      }
    });
  }

  private boolean isInputsValid() {
    return this.portfolioName != null && !this.portfolioName.getText().trim().isBlank()
            && !this.portfolioName.getText().trim().isEmpty();
  }



}
