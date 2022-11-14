package view;

import java.time.LocalDate;
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
   * Used to display information to user.
   * @param message display message.
   */
  void display(String message);

  void draw(Map<LocalDate, Double> portfolioData);

}
