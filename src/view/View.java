package view;

import java.util.Map;

/**
 * This interface represents the view in MVC design. It
 * is used to read/display data to the user.
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
  Map<String, Double> readTrade() throws NumberFormatException;

  /**
   * Used to display information to user.
   * @param message display message.
   */
  void display(String message);

}
