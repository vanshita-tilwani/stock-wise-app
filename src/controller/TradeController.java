package controller;

/**
 * Controller interface responsible for driving the application flow. Responsible for
 * interacting the view for user inputs and to display the information to the user.
 * Interacts with the model for executing business logic of the feature requested
 * by the user.
 */
public interface TradeController {

  /**
   * The method responsible for the application flow. Supports displaying the available
   * features to the user and taking input from the user to execute a set of trade operations.
   * Contains features useful for providing information on the user trade history (the total
   * money invested in the trading till specified date) as well as current composition of the
   * trades (i.e. total value of the portfolio today).
   */
  void execute();


}
