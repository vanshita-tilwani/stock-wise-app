import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.*;


/**
 * A class to represent the features.
 */
public class SwingFeaturesFrame extends JFrame implements ActionListener {

  private JPanel mainPanel;
  private JRadioButton[] radioButtons;

  private JDatePicker date;

  /**
   * Constructor to initialise the class variables.
   */
  public SwingFeaturesFrame() {

    super();
    setTitle("Testtt");
    setSize(800, 800);
    setLocationRelativeTo(null);
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
    JPanel radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Radio buttons"));

    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    radioButtons = new JRadioButton[5];

    //buttons groups are used to combine radio buttons. Only one radio
    // button in each group can be selected.
    ButtonGroup rGroup1 = new ButtonGroup();
    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton("Option " + (i + 1));
      //radioButtons[i].setSelected(false);

      radioButtons[i].setActionCommand("RB" + (i + 1));
      radioButtons[i].addActionListener(this);
      //radioButtons[i].addActionListener();
      rGroup1.add(radioButtons[i]);

      radioPanel.add(radioButtons[i]);

    }
    radioButtons[4].doClick();
    UtilDateModel model = new UtilDateModel();
    //model.setDate(20,04,2014);
    // Need this...
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    // Don't know about the formatter, but there it is...
    this.date = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    mainPanel.add(radioPanel);
    mainPanel.add(datePanel);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < radioButtons.length; i++) {
      if (radioButtons[i].isSelected()) {
        System.out.println(radioButtons[i].getText());
      }
    }
  }
}
