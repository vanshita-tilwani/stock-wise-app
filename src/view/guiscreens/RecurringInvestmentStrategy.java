package view.guiscreens;

import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JLabel;

import controller.Features;

/**
 * Screen to create recurring strategy.
 */
public class RecurringInvestmentStrategy extends OneTimeInvestmentStrategy {

  private boolean isInit = false;
  private final JCheckBox isOngoing;
  private final JPanel endDataPanel;
  private final JDatePickerImpl startDate;
  private final JDatePickerImpl endDate;
  private final JSpinner frequency;

  /**
   * Initializes a screen to create recurring strategy.
   */
  public RecurringInvestmentStrategy() {
    super("Trading Application - Create One Time Strategy");
    this.startDate = this.createDateField("Enter the start date for the investment");
    this.endDate = this.createDateField("Enter the end date for the investment");
    this.frequency = this.createSpinnerField("Enter the frequency of recurring " +
            "investment(in days)");
    this.isOngoing = new JCheckBox();
    this.endDataPanel = new JPanel();
    this.isInit = true;

    this.remove(this.mainPanel);
    this.mainPanel = initMainPanel();
    this.add(mainPanel, BorderLayout.CENTER);

    renderFrame();
  }

  @Override
  protected List<JPanel> getAdditionalComponents() {
    if (!this.isInit) {
      return new ArrayList<>();
    }
    JPanel purchaseData = new JPanel();
    purchaseData.add(this.createLabelField("Enter the start date for the investment : "));
    purchaseData.add(this.startDate);

    JPanel ongoingInfo = new JPanel();
    var frame = this;
    ongoingInfo.add(new JLabel("Check the box if the strategy is ongoing : "));
    ongoingInfo.add(this.isOngoing);
    this.isOngoing.addActionListener(f ->
            frame.endDataPanel.setVisible(!frame.isOngoing.isSelected()));

    endDataPanel.add(new JLabel("Enter the end date for the investment : "));
    endDataPanel.add(this.endDate);

    JPanel frequencyData = new JPanel();
    frequencyData.add(new JLabel("Enter the frequency of recurring investment(in days) : "));
    frequencyData.add(this.frequency);

    return Arrays.asList(purchaseData, ongoingInfo, endDataPanel, frequencyData);
  }

  @Override
  public void display(String text) {
    if (this.frame != null) {
      this.frame.display(text);
    } else {
      super.display(text);
    }
  }

  @Override
  public void error(String text) {
    if (this.frame != null) {
      this.frame.error(text);
    } else {
      super.error(text);
    }
  }

  @Override
  public void disposeScreen() {
    if (frame != null) {
      this.frame.disposeScreen();
    }
    super.disposeScreen();
  }

  @Override
  public void addFeatures(Features features) {
    this.submit.addActionListener(f -> {
      if (isInputsValid()) {
        this.setVisibility(false);
        this.frame = new StockWeightScreen(this.name.getText(),
                toDouble(this.principal.getText()),
                toInt(this.stocks),
                this.getLocalDate(this.startDate),
                this.isOngoing.isSelected() ? null : this.getLocalDate(this.endDate),
                toInt(this.frequency),
                toDouble(this.commission));
        var listeners = this.back.getActionListeners();
        this.frame.bindListener(listeners[0]);
        this.frame.addFeatures(features);
      }

    });
  }

  @Override
  protected boolean isInputsValid() {

    if (!super.validateItems()) {
      return false;
    }
    if (this.getLocalDate(this.startDate).isAfter(LocalDate.now())) {
      this.error("The selected date is in future.\nPlease select " +
              "a new date and try again");
      return false;
    }
    if (!this.isOngoing.isSelected() &&
            this.getLocalDate(this.endDate).isBefore(this.getLocalDate(this.startDate))) {
      this.error("The selected end date is before start date.\nPlease select " +
              "a new date and try again");
      return false;
    }
    if (toInt(this.frequency) <= 0) {
      this.error("Invalid Frequency. Please enter and try again");
      return false;
    }
    return true;
  }
}
