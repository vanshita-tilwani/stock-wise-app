package view.guiscreens;


import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import controller.Features;

/**
 * Class for all the portfolios extending the screen.
 */
public class AllPortfolio extends AbstractScreen {

  /**
   * Constructor to initialise th variables.
   */
  public AllPortfolio() {
    super("Show All Portfolios",
            "The following portfolios exist in the application");

    this.submit.setEnabled(false);

    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    var frame = this;
    this.addComponentListener(new ComponentAdapter() {

      @Override
      public void componentShown(ComponentEvent e) {
        var portfolios = features.getPortfolios();
        String result = "The application does not contain any portfolio!";
        if (portfolios.size() > 0) {
          result = "<html>" + String.join("<br>", portfolios);
        }
        frame.display(result);
        frame.pack();
      }}
    );
  }

}
