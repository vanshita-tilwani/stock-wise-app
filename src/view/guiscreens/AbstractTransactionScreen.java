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

public abstract class AbstractTransactionScreen extends AbstractScreen {

  private final JComboBox<String> portfolioName;
  private final JDatePickerImpl date;
  private final JTextField stock;
  private final JSpinner shares;
  private final JSpinner commission;

  public AbstractTransactionScreen(String caption) {
    super(caption, "");

    JPanel portfolioDetails = new JPanel();
    this.portfolioName = new JComboBox<String>();
    var nameLabel = new JLabel("Enter the portfolio name : ");
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(nameLabel);
    portfolioDetails.add(this.portfolioName);

    JPanel stockData = new JPanel();
    this.stock = new javax.swing.JTextField(8);
    this.stock.setToolTipText("Enter the name of the stock for the transaction");
    stockData.add(new JLabel("Enter the name of the stock : "));
    stockData.add(this.stock);
    JPanel shareData = new JPanel();

    this.shares = new javax.swing.JSpinner();
    this.shares.setToolTipText("Enter the number of shares involved in transaction");
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
    purchaseData.add(new JLabel("Enter the date of transaction of the stock : "));
    purchaseData.add(this.date);
    JPanel commissionData = new JPanel();

    this.commission = new javax.swing.JSpinner();
    this.commission.setToolTipText("Enter the commission fee for the transaction");
    commissionData.add(new JLabel("Enter the commission fee for the transaction : "));
    commissionData.add(this.commission);

    var mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    mainPanel.add(portfolioDetails);
    mainPanel.add(stockData);
    mainPanel.add(shareData);
    mainPanel.add(purchaseData);
    mainPanel.add(commissionData);
    this.add(mainPanel, BorderLayout.CENTER);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    features.getPortfolios().forEach(e -> this.portfolioName.addItem(e));

    this.submit.addActionListener(e -> {
      LocalDate date = this.getDate();
      String name = this.getTrade();
      if(this.isInputsValid()) {
        this.performTrade(features, name, this.stock.getText(), toDouble(this.shares), date,
                toDouble(this.commission));
      }
    });

  }


  public abstract void performTrade(Features features, String portfolioName, String stock,
                                    Double shares, LocalDate date, Double commission);

  private LocalDate getDate() {
    Date dateFromPicker = (Date) this.date.getModel().getValue();
    LocalDate date = dateFromPicker.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return date;
  }

  private String  getTrade() {
    int index = this.portfolioName.getSelectedIndex();
    return this.portfolioName.getItemAt(index);
  }

  private boolean isInputsValid() {
    boolean valid = true;
    if(this.getTrade() == null) {
      valid = false;
      this.error("Invalid Portfolio Selected. Please select a portfolio and try again");
    }
    if(this.getDate().isAfter(LocalDate.now())) {
      valid = false;
      this.error("The selected evaluation date is in future.\nPlease select " +
              "a new date and try again");
    }
    if(this.stock == null || this.stock.getText().trim().isBlank() ||
            this.stock.getText().trim().isEmpty()) {
      valid = false;
      this.error("The stock ticker entered is invalid. Please enter and try again");
    }
    if(!this.shares.isValid()) {
      System.out.println("HI");
    }
    return valid;
  }


}
