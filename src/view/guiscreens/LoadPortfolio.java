package view.guiscreens;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import controller.Features;

public class LoadPortfolio extends AbstractScreen {

  private final JFileChooser file;
  private JLabel output;

  public LoadPortfolio() {
    super("Load Portfolio", "");
    file = new JFileChooser();
    output = new JLabel();
    JPanel mainPanel = new JPanel();
    mainPanel.add(file);
    mainPanel.add(output);
    this.add(mainPanel, BorderLayout.CENTER);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void display(String text) {
    this.output.setText(text);
  }

  @Override
  public void addFeatures(Features features) {
    this.file.addPropertyChangeListener("JFileChooserDialogIsClosingProperty",
            new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {

                System.out.println("Loaded");
                System.out.println(file.getSelectedFile().getAbsolutePath());
                features.loadPortfolio();
              }
            });
    file.showOpenDialog(this);
    file.setDialogTitle("Specify a file to open");

  }

  @Override
  public String getPortfolioName() {
    return null;
  }

  @Override
  public void setVisibility(boolean visible) {

  }
}
