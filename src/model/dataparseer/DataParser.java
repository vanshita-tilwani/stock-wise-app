package model.dataparseer;

/**
 * Parses the data in the needed format.
 */
public interface DataParser<T> {

  /**
   * Parses the data in the required format.
   * @param data the string format of data.
   * @return parsed data
   */
  T parse(String data);
}
