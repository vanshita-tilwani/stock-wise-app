package view.guiscreens;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.Features;

public class MainScreen extends JFrame implements Screen {

  private final JButton submit;
  private final ButtonGroup menuGroup;
  private final List<JRadioButton> menuOptions;
  public MainScreen() {
    super();
    setTitle("Welcome to Trading Application");
    setSize(600, 600);
    menuOptions = new ArrayList<>();
    var panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    this.menuGroup = new ButtonGroup();
    menuOptions.add(new JRadioButton("Create Portfolio"));
    menuOptions.add(new JRadioButton("Show All Portfolios"));
    menuOptions.add(new JRadioButton("Buy Stock"));
    menuOptions.add( new JRadioButton("Sell Stock"));
    menuOptions.add(new JRadioButton("Portfolio Composition"));
    menuOptions.add(new JRadioButton("Evaluate Value"));
    menuOptions.add(new JRadioButton("Evaluate Cost Basis"));
    menuOptions.add(new JRadioButton("Save Portfolio"));
    menuOptions.add(new JRadioButton("Load Portfolio"));
    menuOptions.add(new JRadioButton("Show Bar Chart"));
    this.submit = new JButton("Submit");


    this.menuOptions.forEach(e -> {
      this.menuGroup.add(e);
      panel.add(e);
    });

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
    menuOptions.forEach(menuOption -> {
      menuOption.addActionListener((e) -> {
        this.submit.setActionCommand(e.getActionCommand());
      });
    });
    this.submit.addActionListener(listener);
  }

  @Override
  public void addFeatures(Features features) {

  }


  @Override
  public void setVisibility(boolean visible) {
    setVisible(visible);
  }
}