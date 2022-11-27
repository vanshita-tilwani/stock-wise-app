package view.guiscreens;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.AbstractList;
import java.util.Map;

import javax.swing.*;

import controller.Features;

public class MainScreen extends JFrame implements GUIView {

  private final JButton submit;
  private final ButtonGroup menuGroup;
  private final JRadioButton[] menuOptions;
  public MainScreen() {
    super();
    setTitle("Welcome to Trading Application");
    setSize(600, 600);
    menuOptions = new JRadioButton[9];
    var panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    this.menuGroup = new javax.swing.ButtonGroup();
    menuOptions[0] = new javax.swing.JRadioButton("Create Portfolio");
    menuOptions[1] = new javax.swing.JRadioButton("Show All Portfolios");
    menuOptions[2] = new javax.swing.JRadioButton("Buy Stock");
    menuOptions[3] = new javax.swing.JRadioButton("Sell Stock");
    menuOptions[4] = new JRadioButton("Portfolio Composition");
    menuOptions[5] = new javax.swing.JRadioButton("Evaluate Value");
    menuOptions[6] = new javax.swing.JRadioButton("Evaluate Cost Basis");
    menuOptions[7] = new JRadioButton("Save Portfolio");
    menuOptions[8] = new JRadioButton("Load Portfolio");
    this.submit = new javax.swing.JButton("Submit");

    this.menuGroup.add(menuOptions[0]);
    this.menuGroup.add(menuOptions[1]);
    this.menuGroup.add(menuOptions[2]);
    this.menuGroup.add(menuOptions[3]);
    this.menuGroup.add(menuOptions[4]);
    this.menuGroup.add(menuOptions[5]);
    this.menuGroup.add(menuOptions[6]);
    this.menuGroup.add(menuOptions[7]);
    this.menuGroup.add(menuOptions[8]);

    panel.add(menuOptions[0]);
    panel.add(menuOptions[1]);
    panel.add(menuOptions[2]);
    panel.add(menuOptions[3]);
    panel.add(menuOptions[4]);
    panel.add(menuOptions[5]);
    panel.add(menuOptions[6]);
    panel.add(menuOptions[7]);
    panel.add(menuOptions[8]);

    JPanel actions = new JPanel();
    actions.add(submit);

    this.add(panel, BorderLayout.CENTER);
    this.add(actions, BorderLayout.PAGE_END);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  @Override
  public void display(String text) {

  }

  @Override
  public void addActionListener(ActionListener listener) {
    for(int i = 0; i < menuOptions.length; i ++) {
      menuOptions[i].addActionListener((e) -> {
        this.submit.setActionCommand(e.getActionCommand());
      });
    }
    this.submit.addActionListener(listener);
  }

  @Override
  public void addFeatures(Features features) {

  }

  @Override
  public String getPortfolioName() {
    return null;
  }

  @Override
  public void setVisibility(boolean visible) {
    setVisible(visible);
  }
}
