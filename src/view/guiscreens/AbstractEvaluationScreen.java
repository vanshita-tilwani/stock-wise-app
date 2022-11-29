package view.guiscreens;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;

import controller.Features;

public abstract class AbstractEvaluationScreen extends AbstractScreen {
  private final JComboBox<String> portfolioName;
  private final JDatePickerImpl date;
  private final JLabel output;
  public AbstractEvaluationScreen(String caption) {
    super(caption, "");
    JPanel portfolioDetails = new JPanel();


    this.portfolioName = new JComboBox<String>();
    var nameLabel = new JLabel("Enter the portfolio name : ");
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(nameLabel);
    portfolioDetails.add(this.portfolioName);
    JPanel evaluationData = new JPanel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    UtilDateModel dateModel = new UtilDateModel();

    dateModel.setSelected(true);

    JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
    this.date = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    this.date.setToolTipText("Enter the date of evaluation");
    evaluationData.add(new JLabel("Enter the date of evaluation of the portfolio : "));
    evaluationData.add(this.date);
    this.output = new JLabel("");

    var mainPanel = new JPanel();
    mainPanel.add(portfolioDetails);
    mainPanel.add(evaluationData);
    mainPanel.add(this.output);
    this.add(mainPanel, BorderLayout.CENTER);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }
  @Override
  public void display(String text) {
    this.setOutputText(text);
    this.output.setForeground(Color.GREEN);
  }

  @Override
  public void error(String text) {
    this.setOutputText(text);
    this.output.setForeground(Color.RED);
  }

  private void setOutputText(String text) {
    this.output.setText(text);
  }

  @Override
  public void addFeatures(Features features) {
    features.getPortfolios().forEach(e -> this.portfolioName.addItem(e));
    this.submit.addActionListener(e -> {
      String name = this.getTrade();
      LocalDate date = this.getDate();
      this.evaluateTrade(features, name, date);

    });
  }

  public abstract void evaluateTrade(Features features, String portfolioName, LocalDate date);

  private LocalDate getDate() {
    Date dateFromPicker = (Date) this.date.getModel().getValue();
    LocalDate date = dateFromPicker.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return date;
  }

  private String  getTrade() {
    int index = this.portfolioName.getSelectedIndex();
    return this.portfolioName.getItemAt(index);
  }
}
