package model.portfolio;

import java.time.LocalDate;

/**
 * Represents a collection of Stock trades.
 */
public interface Portfolio {

  /**
   * Returns the name of the Portfolio.
   * @return name
   */
  String name();

  /**
   * Returns the value of a Portfolio on a given date.
   * @param date the date on which the value needs to be determined.
   * @return the value.
   */
  double value(LocalDate date);

  /**
   * Saves the Portfolio to the database/file so that it can
   * be retrieved later.
   */
  void save();


}
