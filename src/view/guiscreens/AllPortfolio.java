package view.guiscreens;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
    this.submit.setEnabled(false);
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
    var frame = this;
    this.addComponentListener(new ComponentAdapter() {

      @Override
      public void componentShown(ComponentEvent e) {
        var portfolios = features.getPortfolios();
        String result = "The application does not contain any portfolio!";
        if(portfolios.size() > 0) {
          result = "<html>" + String.join("<br>", portfolios);
        }
        frame.display(result);
      }}
    );
  }

}
