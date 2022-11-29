package view.guiscreens;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public abstract class AbstractScreen extends JFrame implements Screen {
  protected final JLabel message;
  protected final JButton submit;
  protected final JButton back;

  protected AbstractScreen(String caption, String message) {
    super(caption);
    setSize(600, 600);
    this.submit = new javax.swing.JButton("Submit");
    this.back = new javax.swing.JButton("Go Back");

    this.submit.setActionCommand(caption);
    var backPanel = new JPanel();
    backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));
    backPanel.add(this.back);
    backPanel.add(this.submit);
    this.message = new javax.swing.JLabel(message);
    this.message.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    this.message.setFont(new Font(null, Font.PLAIN, 15));
    this.submit.setFont(new Font(null, Font.BOLD, 15));
    this.back.setFont(new Font(null, Font.BOLD, 15));
    var messagePanel = new JPanel();
    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
    messagePanel.add(this.message);
    this.add(messagePanel, BorderLayout.PAGE_START);
    this.add(backPanel, BorderLayout.PAGE_END);
  }


  @Override
  public void bindListener(ActionListener listener) {
    this.back.addActionListener(listener);
  }

  @Override
  public void setVisibility(boolean visible) {
    setVisible(visible);
  }



}
