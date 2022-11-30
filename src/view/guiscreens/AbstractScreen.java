package view.guiscreens;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public abstract class AbstractScreen extends JFrame implements Screen {
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

  protected Integer toInt(JSpinner value) {
    return Integer.parseInt(value.getValue() + "");
  }

  protected Double toDouble(String value) {
    return Double.parseDouble(value);
  }



}
