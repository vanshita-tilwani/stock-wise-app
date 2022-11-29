package view.guiscreens;

import java.awt.event.ActionListener;

import controller.Features;

public interface Screen {

  void display(String text);

  void error(String text);

  void bindListener(ActionListener listener);

  void addFeatures(Features features);

  void setVisibility(boolean visible);
}
