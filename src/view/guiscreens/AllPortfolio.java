package view.guiscreens;


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
    renderFrame();
  }

  @Override
  public void addFeatures(Features features) {
    var portfolios = features.getPortfolios();
    String result = "The application does not contain any portfolio!";
    if (portfolios.size() > 0) {
      result = "<html>" + String.join("<br>", portfolios);
    }
    this.display(result);
  }


  @Override
  protected boolean isInputsValid() {
    this.error("");
    return true;
  }
}
