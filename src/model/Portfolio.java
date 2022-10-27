package model;

import java.util.Date;

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
   * Returns the Portfolio Composition.
   * @return portfolio details.
   */
  String composition();

  /**
   * Returns the value of a Portfolio on a given date.
   * @param date the date on which the value needs to be determined.
   * @return the value.
   */
  double value(Date date);


}
