package view.guiscreens;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JFileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * GUI for save portfolio.
 */
public class SavePortfolio extends AbstractScreen {

  private final JComboBox<String> portfolioName;

  /**
   * Constructor to initialise the variables.
   */
  public SavePortfolio() {
    super("Trading Application - Portfolio Save Window","");
    JPanel portfolioDetails = new JPanel();
    this.portfolioName = new JComboBox<String>();
    var nameLabel = new JLabel("Enter the portfolio name : ");
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(nameLabel);
    portfolioDetails.add(this.portfolioName);
    JPanel mainPanel = new JPanel();
    mainPanel.add(portfolioDetails);
    this.add(mainPanel, BorderLayout.CENTER);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    features.getPortfolios().forEach(e -> this.portfolioName.addItem(e));

    this.submit.addActionListener(e -> {
      if (this.getTradeName() != null) {
        JFileChooser file = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt");
        file.setFileFilter(filter);
        file.setDialogTitle("Specify a file to save");
        var frame = this;
        file.addPropertyChangeListener("JFileChooserDialogIsClosingProperty",
                new PropertyChangeListener() {
                  @Override
                  public void propertyChange(PropertyChangeEvent evt) {
                    File selectedFile = file.getSelectedFile();
                    if(selectedFile != null) {
                      String path = selectedFile.getAbsolutePath() + ".txt";
                      features.save(path,
                              frame.portfolioName.getItemAt(frame.portfolioName.getSelectedIndex()));
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

  private String getTradeName() {
    int index = this.portfolioName.getSelectedIndex();
    return this.portfolioName.getItemAt(index);
  }
}
