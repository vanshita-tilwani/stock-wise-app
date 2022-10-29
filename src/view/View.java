package view;

import java.util.HashMap;
import java.util.Map;

public interface View {

  String input();

  Map<String, Double> read();

  void display(String message);

}
