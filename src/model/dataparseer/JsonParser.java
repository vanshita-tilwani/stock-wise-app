package model.dataparseer;

import java.util.Map;

public interface JsonParser<K,V> {

  Map<K, V> toMap(String data);
}
