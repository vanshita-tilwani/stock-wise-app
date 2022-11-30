package view.guiscreens;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

public class SavePortfolio extends AbstractScreen {

  private final JComboBox<String> portfolioName;

  public SavePortfolio() {
    super("Trading Application - Portfolio Save Window","");

    this.portfolioName = this.createComboBoxField("Enter Portfolio Name");

    var self = this;
    List<Map.Entry<String, JComponent>> components = new ArrayList<>();
    components.add(new AbstractMap.SimpleEntry<>("Enter the portfolio name : "
            , self.portfolioName));
    var mainPanel = this.createMainPanel(components);
    this.add(mainPanel, BorderLayout.CENTER);
    renderFrame();
  }

  @Override
  public void addFeatures(Features features) {
    features.getPortfolios().forEach(e -> this.portfolioName.addItem(e));

    this.submit.addActionListener(e -> {
      if(this.getComboBoxValue(this.portfolioName) != null) {
        JFileChooser file = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES"
                , "txt");
        file.setFileFilter(filter);
        file.setDialogTitle("Specify a file to save");
        var frame = this;
        file.addPropertyChangeListener("JFileChooserDialogIsClosingProperty",
                new PropertyChangeListener() {
                  @Override
                  public void propertyChange(PropertyChangeEvent evt) {
                    File selectedFile= file.getSelectedFile();
                    if(selectedFile != null) {
                      String path = selectedFile.getAbsolutePath() + ".txt";
                      features.save(path, frame.getComboBoxValue(frame.portfolioName));
                    }
                  }
                });
        file.setAcceptAllFileFilterUsed(false);
        file.showSaveDialog(this);
      }
      else {
        this.error("Invalid Portfolio Selected. Please select a portfolio and try again\n");
      }

    });


  }

}
