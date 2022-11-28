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

public class SellStock extends AbstractScreen {
  private final JComboBox<String> portfolioName;

  private final JDatePickerImpl date;
  private final JLabel output;
  private final JTextField stock;
  private final JSpinner shares;
  private final JSpinner commission;

  public SellStock() {
    super("Trading Application - Sale Window","");
    JPanel portfolioDetails = new JPanel();


    this.portfolioName = new JComboBox<String>();
    var nameLabel = new JLabel("Enter the portfolio name : ");
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(nameLabel);
    portfolioDetails.add(this.portfolioName);
    JPanel stockData = new JPanel();
    this.stock = new javax.swing.JTextField(8);
    this.stock.setToolTipText("Enter the name of the stock you wish to sell");
    stockData.add(new JLabel("Enter the name of the stock : "));
    stockData.add(this.stock);
    JPanel shareData = new JPanel();

    this.shares = new javax.swing.JSpinner();
    this.shares.setToolTipText("Enter the number of shares you wish to sell");
    shareData.add(new JLabel("Enter the number of shares of the stock : "));
    shareData.add(this.shares);

    JPanel purchaseData = new JPanel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    UtilDateModel dateModel = new UtilDateModel();
    dateModel.setSelected(true);
    JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
    this.date = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    this.date.setToolTipText("Enter the date of sale");
    purchaseData.add(new JLabel("Enter the date of sale of the stock : "));
    purchaseData.add(this.date);
    JPanel commissionData = new JPanel();

    this.commission = new javax.swing.JSpinner();
    this.commission.setToolTipText("Enter the commission fee for the sale");
    commissionData.add(new JLabel("Enter the commission fee for the sale : "));
    commissionData.add(this.commission);
    this.output = new JLabel("");

    var mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    mainPanel.add(portfolioDetails);
    mainPanel.add(stockData);
    mainPanel.add(shareData);
    mainPanel.add(purchaseData);
    mainPanel.add(commissionData);
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
      features.sellFromPortfolio(
              this.portfolioName.getItemAt(this.portfolioName.getSelectedIndex()),
              this.stock.getText(),
              Double.parseDouble(this.shares.getValue() + ""),
              f.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
              Double.parseDouble(this.shares.getValue() + ""));
    });

  }
}
