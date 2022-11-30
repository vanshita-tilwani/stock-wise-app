package view.guiscreens;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

public class LoadPortfolio extends AbstractScreen {

  private final JFileChooser file;

  public LoadPortfolio() {
    super("Load Portfolio", "");
    file = new JFileChooser();
    JPanel mainPanel = new JPanel();
    mainPanel.add(file);
    this.submit.setEnabled(false);
    this.add(mainPanel, BorderLayout.CENTER);

    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void addFeatures(Features features) {
    var frame = this;
    this.file.addPropertyChangeListener("JFileChooserDialogIsClosingProperty",
            new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {
                File selectedFile = file.getSelectedFile();
                if(selectedFile != null) {
                  frame.setVisibility(true);
                  features.load(selectedFile.getAbsolutePath());
                }
              }
            });
    file.setDialogTitle("Specify a file to open");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
    file.setAcceptAllFileFilterUsed(false);
    file.addChoosableFileFilter(filter);
    file.showOpenDialog(this);


  }
}
