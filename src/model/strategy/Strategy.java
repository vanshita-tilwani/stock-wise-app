package model.strategy;

import model.portfolio.Portfolio;


/**
 * An interface to Implement the strategies.
 */
public interface Strategy {

  /**
   * Returns the name of the strategy.
   *
   * @return strategy name.
   */
  String name();

  /**
   * To apply the strategy on the Portfolio
   *
   * @param portfolio portfolio to be applied strategy on
   */
  void apply(Portfolio portfolio);
}
