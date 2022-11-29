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

public class OneTimeInvestmentStrategy extends AbstractScreen {

  private final JTextField name;
  private final JTextField principal;
  private final JSpinner stocks;
  private final JDatePickerImpl date;
  private final JSpinner commission;

  private Screen frame;
  public OneTimeInvestmentStrategy() {
    super("Trading Application - Create One Time Strategy" ,"");

    JPanel strategyName = new JPanel();
    this.name = new JTextField(10);
    var nameLabel = new JLabel("Enter the strategy name : ");
    this.name.setToolTipText("Enter the strategy name");
    strategyName.add(nameLabel);
    strategyName.add(this.name);

    JPanel principalDetails = new JPanel();
    this.principal = new JTextField(10);
    var principalLabel = new JLabel("Enter the principal amount : ");
    this.principal.setToolTipText("Enter the principal amount");
    principalDetails.add(principalLabel);
    principalDetails.add(this.principal);

    JPanel stockData = new JPanel();
    this.stocks = new JSpinner();
    var stockLabel = new JLabel("Enter the number of stocks : ");
    this.stocks.setToolTipText("Enter the number of stocks");
    stockData.add(stockLabel);
    stockData.add(this.stocks);

    JPanel purchaseData = new JPanel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    UtilDateModel dateModel = new UtilDateModel();
    dateModel.setSelected(true);
    JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
    this.date = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    this.date.setToolTipText("Enter the date for one time investment");
    purchaseData.add(new JLabel("Enter the date for one time investment : "));
    purchaseData.add(this.date);

    JPanel commissionData = new JPanel();
    this.commission = new JSpinner();
    var commissionLabel = new JLabel("Enter the commission fee for the strategy : ");
    this.stocks.setToolTipText("Enter the commission fee for the strategy");
    commissionData.add(commissionLabel);
    commissionData.add(this.commission);

    var mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    mainPanel.add(strategyName);
    mainPanel.add(principalDetails);
    mainPanel.add(stockData);
    mainPanel.add(purchaseData);
    mainPanel.add(commissionData);
    this.add(mainPanel, BorderLayout.CENTER);
    this.frame = null;
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }

  @Override
  public void display(String text) {
    this.frame.display(text);
  }

  @Override
  public void error(String text) {
    this.frame.error(text);
  }

  @Override
  public void addFeatures(Features features) {
    this.submit.addActionListener(f -> {
      this.setVisibility(false);
      this.frame = new StockWeightScreen(this.name.getText(),
            toDouble(this.principal.getText()),
            toInt(this.stocks),
            this.getDate(),
            this.getDate(),
            1,
            toDouble(this.commission));
      var listeners = this.back.getActionListeners();
      this.frame.bindListener(listeners[0]);
      this.frame.addFeatures(features);

    });
  }


  private LocalDate getDate() {
    Date dateFromPicker = (Date) this.date.getModel().getValue();
    LocalDate date = dateFromPicker.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return date;
  }
}
