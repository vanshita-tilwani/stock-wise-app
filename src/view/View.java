package view;

import java.util.Map;

/**
 * Interface used to display info to user or read data.
 */
public interface View {

  /**
   * Reads input from the user.
   * @return Input string
   */
  String input();

  /**
   * Reads the stock data from user.
   * @return stock data.
   * @throws NumberFormatException if the input is not double.
   */
  Map<String, Double> read() throws NumberFormatException;

  /**
   * Used to display information to user.
   * @param message display message.
   */
  void display(String message);

}
