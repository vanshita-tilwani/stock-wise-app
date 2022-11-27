package view.guiscreens;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import controller.Features;

public class LoadPortfolio extends AbstractScreen {

  private final JFileChooser file;
  private int userSelection;

  public LoadPortfolio() {
    super("Load Portfolio", "");
    file = new JFileChooser();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void display(String text) {

  }

  @Override
  public void addFeatures(Features features) {
    this.file.addPropertyChangeListener("JFileChooserDialogIsClosingProperty",
            new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Loaded");
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
