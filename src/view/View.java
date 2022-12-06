package view;

import controller.Features;

/**
 * This interface represents the view in MVC design. It
 * is used to read/display data to the user.
 */
public interface View {

  /**
   * Adds the features to the view
   *
   * @param features the set of api application supports.
   */

  void addFeatures(Features features);

  /**
   * Reads input from the user.
   *
   * @return Input string
   */
  String input();

  /**
   * Used to display information to user.
   *
   * @param message display message.
   */
  void display(String message);

  /**
   * Used to display error message to user.
   *
   * @param message display message.
   */
  void error(String message);

}
