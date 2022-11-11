package model.dataparseer;

import java.util.List;

/**
 * Parses the data in the needed format.
 */
public interface DataParser<T> {

  /**
   * Parses the data in the required format.
   * @param data the string format of data.
   * @return parsed data
   */
  List<T> parse(String data);
}
