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

public class RecurringInvestmentStrategy extends AbstractScreen {

  private final JTextField name;
  private final JTextField principal;
  private final JSpinner stocks;
  private final JDatePickerImpl startDate;
  private final JDatePickerImpl endDate;
  private final JPanel endDataPanel;
  private final JSpinner frequency;
  private final JSpinner commission;
  private final JCheckBox isOngoing;

  private Screen frame;

  public RecurringInvestmentStrategy() {
    super("Trading Application - Create One Time Strategy" ,"");

    JPanel strategyName = new JPanel();
    this.name = new JTextField(10);
    var nameLabel = new JLabel("Enter the strategy name : ");
    this.name.setToolTipText("Enter the strategy name");
    strategyName.add(nameLabel);
    strategyName.add(this.name);

    JPanel principalDetails = new JPanel();
    this.principal = new JTextField(10);
    var principalLabel = new JLabel("Enter the principal amount(in dollars) : ");
    this.principal.setToolTipText("Enter the principal amount(in dollars)");
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
    this.startDate = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    this.startDate.setToolTipText("Enter the start date for the investment");
    purchaseData.add(new JLabel("Enter the start date for the investment : "));
    purchaseData.add(this.startDate);

    JPanel ongoingInfo = new JPanel();
    this.isOngoing = new JCheckBox();
    var frame = this;
    this.isOngoing.addActionListener(f -> {
      frame.endDataPanel.setVisible(!frame.isOngoing.isSelected());
    });
    ongoingInfo.add(new JLabel("Check the box if the strategy is ongoing : "));
    ongoingInfo.add(this.isOngoing);

    endDataPanel = new JPanel();
    this.endDate = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    this.endDate.setToolTipText("Enter the end date for the investment");
    endDataPanel.add(new JLabel("Enter the end date for the investment : "));
    endDataPanel.add(this.endDate);

    JPanel frequencyData = new JPanel();
    this.frequency = new JSpinner();
    var frequencyLabel = new JLabel("Enter the frequency of recurring investment(in days) : ");
    this.frequency.setToolTipText("Enter the frequency of recurring investment(in days)");
    frequencyData.add(frequencyLabel);
    frequencyData.add(this.frequency);

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
    mainPanel.add(ongoingInfo);
    mainPanel.add(endDataPanel);
    mainPanel.add(frequencyData);
    mainPanel.add(commissionData);
    this.add(mainPanel, BorderLayout.CENTER);
    this.frame = null;
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }

  @Override
  public void display(String text) {
    if(this.frame != null) {
      this.frame.display(text);
    }
    else {
      super.display(text);
    }
  }

  @Override
  public void error(String text) {
    if(this.frame != null) {
      this.frame.error(text);
    }
    else {
      super.error(text);
    }
  }

  @Override
  public void disposeScreen() {
    if(frame != null) {
      this.frame.disposeScreen();
    }
    super.disposeScreen();
  }

  @Override
  public void addFeatures(Features features) {
    this.submit.addActionListener(f -> {
      if(isInputsValid()) {
        this.setVisibility(false);
        this.frame = new StockWeightScreen(this.name.getText(),
                toDouble(this.principal.getText()),
                toInt(this.stocks),
                this.getDate(this.startDate),
                this.isOngoing.isSelected() ? null : this.getDate(this.endDate),
                toInt(this.frequency),
                toDouble(this.commission));
        var listeners = this.back.getActionListeners();
        this.frame.bindListener(listeners[0]);
        this.frame.addFeatures(features);
      }

    });
  }

  private LocalDate getDate(JDatePickerImpl date) {
    Date dateFromPicker = (Date) date.getModel().getValue();
    return dateFromPicker.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

  }

  private boolean isInputsValid() {

    if(this.name == null  || this.name.getText().trim().isBlank() ||
            this.name.getText().trim().isEmpty()){
      this.error("Invalid Strategy Name. Please enter again and try");
      return false;
    }
    if(!this.isPrincipalAmountValid()) {
      this.error("Invalid Principal Amount. Please enter again and try");
      return false;
    }
    if(toInt(this.stocks) <= 0) {
      this.error("Invalid number of shares. Please enter and try again");
      return false;
    }
    if(this.getDate(this.startDate).isAfter(LocalDate.now())) {
      this.error("The selected date is in future.\nPlease select " +
              "a new date and try again");
      return false;
    }
    if(!this.isOngoing.isSelected() &&
            this.getDate(this.endDate).isBefore(this.getDate(this.startDate))) {
      this.error("The selected end date is before start date.\nPlease select " +
              "a new date and try again");
      return false;
    }
    if(toInt(this.frequency) <= 0) {
      this.error("Invalid Frequency. Please enter and try again");
      return false;
    }
    if(toInt(this.commission) < 0) {
      this.error("Invalid Commission Fee. Please enter and try again");
      return false;
    }
    return true;
  }

  private boolean isPrincipalAmountValid() {
    try {
      var principal = Double.parseDouble(this.principal.getText());
      return principal > 0;
    }
    catch (NumberFormatException e) {
      return false;
    }
  }
}
