package model.jsonparser;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses a specific type of JSON object.
 */
public class JSONParser {
  /**
   * Static method to parse string to JSON.
   * @param jsonText string that needs to be parsed.
   * @return parsed JSON.
   */
  public static Map<LocalDate, Double> parse(String jsonText) {

    HashMap<LocalDate, Double> map = new HashMap<>();
    StringBuilder builder = new StringBuilder(jsonText);

    for (String objects : builder.toString().split("},")) {
      String[] objectValue = objects.split(
              String.valueOf(":"), 2);

      if (objectValue.length == 2) {
        String key = objectValue[0].substring(objectValue[0].indexOf('"')+1, objectValue[0].lastIndexOf('"'));
        // TODO : do later
        Double value = 30.12;
        map.put(LocalDate.parse(key), value);
      }
    }
    return map;
  }

}
