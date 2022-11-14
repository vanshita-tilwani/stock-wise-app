package model.dataparseer;

import model.portfolio.Portfolio;

/**
 * Data Parser Factory which is used to return the parser based on the type of portfolio
 * that needs to be parsed by the application.
 */
public class DataParserFactory {

  /**
   * Returns the parser object responsible for parsing the given portfolio type.
   *
   * @param type the type of portfolio that needs to be parsed.
   * @return the parser object.
   */
  public static DataParser<Portfolio> getParser(String type) {
    if (type == null || type.isEmpty()) {
      return null;
    }
    switch (type) {
      case "MASTER":
        return new MasterPortfolioParser();
      case "TRANSACTIONAL":
        return new TransactionalPortfolioParser();
      default:
        throw new IllegalArgumentException("Unknown type " + type);
    }
  }
}
