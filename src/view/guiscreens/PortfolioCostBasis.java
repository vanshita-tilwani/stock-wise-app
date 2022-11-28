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

public class PortfolioCostBasis extends AbstractScreen{
  private final JComboBox<String> portfolioName;
  private final JDatePickerImpl date;
  private final JLabel output;
  public PortfolioCostBasis() {
    super("Trading Application - Portfolio Cost Basis Window","");
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
    this.output.setText(text);
  }

  @Override
  public void addFeatures(Features features) {
    features.getAllPortfolios().forEach(e -> this.portfolioName.addItem(e));
    this.submit.addActionListener(e -> {
      Date f = (Date) this.date.getModel().getValue();
      features.evaluateCostBasis(
              this.portfolioName.getItemAt(this.portfolioName.getSelectedIndex()),
              f.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    });
  }
}
