package view.guiscreens;

import java.awt.event.ActionListener;

import controller.Features;

public interface Screen {

  void display(String text);

  void addActionListener(ActionListener listener);

  void addFeatures(Features features);

  String getPortfolioName();

  void setVisibility(boolean visible);
}
