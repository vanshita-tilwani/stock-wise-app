package view.guiscreens;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

abstract class AbstractScreen extends JFrame implements Screen {
  protected final JLabel message;
  protected final JButton submit;
  protected final JButton back;
  private final JLabel output;

  protected AbstractScreen(String caption, String message) {
    super(caption);
    setSize(600, 600);
    this.message = new javax.swing.JLabel(message);
    this.message.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    var header = new JPanel();
    header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
    header.add(this.message);

    var outputPanel = new JPanel();
    this.output = new JLabel();
    outputPanel.add(this.output);

    this.submit = new javax.swing.JButton("Submit");
    this.back = new javax.swing.JButton("Home");
    this.submit.setActionCommand(caption);
    var actionsPanel = new JPanel();
    actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.X_AXIS));
    actionsPanel.add(this.back);
    actionsPanel.add(this.submit);

    var footer = new JPanel();
    footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
    footer.add(outputPanel);
    footer.add(actionsPanel);
    this.add(header, BorderLayout.PAGE_START);
    this.add(footer, BorderLayout.PAGE_END);
  }


  @Override
  public void bindListener(ActionListener listener) {
    this.back.addActionListener(listener);
  }

  @Override
  public void setVisibility(boolean visible) {
    setVisible(visible);
  }

  @Override
  public void display(String text) {
    this.setOutputText(text);
    this.output.setForeground(Color.GREEN.darker());
  }

  @Override
  public void error(String text) {
    this.setOutputText(text);
    this.output.setForeground(Color.RED);
  }

  @Override
  public void disposeScreen() {
    this.dispose();
  }

  private void setOutputText(String text) {
    this.output.setText(text);
  }

  protected Double toDouble(JSpinner value) {
    return Double.parseDouble(value.getValue() + "");
  }

  protected Double toDouble(String value) {
    return Double.parseDouble(value);
  }

  protected Integer toInt(JSpinner value) {
    return Integer.parseInt(value.getValue() + "");
  }

  protected JDatePickerImpl createDateField(String tooltip) {
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    UtilDateModel dateModel = new UtilDateModel();
    dateModel.setSelected(true);
    JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
    var datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    datePicker.setToolTipText(tooltip);
    return datePicker;
  }

  protected LocalDate getLocalDate(JDatePickerImpl date) {
    Date dateFromPicker = (Date) date.getModel().getValue();
    LocalDate localDate = dateFromPicker.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return localDate;
  }

  protected JComboBox<String> createComboBoxField(String tooltip) {
    var jComboBox = new JComboBox<String>();
    jComboBox.setToolTipText("Enter Portfolio Name");
    return jComboBox;
  }

  protected String getComboBoxValue(JComboBox<String> comboBoxList) {
    int index = comboBoxList.getSelectedIndex();
    return comboBoxList.getItemAt(index);
  }

  protected JSpinner createSpinnerField(String tooltip) {
    var jSpinner = new JSpinner();
    jSpinner.setToolTipText(tooltip);
    return jSpinner;
  }

  protected JTextField createTextField(String tooltip) {
    var jTextField = new JTextField(10);
    jTextField.setToolTipText(tooltip);
    return jTextField;
  }

  protected JLabel createLabelField(String label) {
    var jLabel = new JLabel(label);
    jLabel.setToolTipText(label);
    return jLabel;
  }

  protected JPanel createMainPanel(List<Map.Entry<String, JComponent>> panels) {
    var mainPanel = new JPanel();

    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    for (var each : panels) {
      JPanel panel = new JPanel();
      panel.add(new JLabel(each.getKey()));
      panel.add(each.getValue());
      mainPanel.add(panel);
    }
    return mainPanel;
  }

  protected void renderFrame() {
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

  }

}
