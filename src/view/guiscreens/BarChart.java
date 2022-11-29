package view.guiscreens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.Features;

public class BarChart extends AbstractScreen {

  private final JLabel output;

  private final JComboBox<String> portfolios;
  private final JDatePickerImpl from;
  private final JDatePickerImpl to;

  private BarChartPanel chart;
  private JPanel mainPanel = new JPanel();

  Box box = new Box(BoxLayout.Y_AXIS);

  public BarChart() {

    super("Show Bar Chart", "Portfolio Performance");
    super.setSize(1000, 800);
    this.chart = new BarChartPanel(new HashMap<>());
    this.chart.setPreferredSize(new Dimension(1000, 800));
    this.portfolios = new JComboBox<String>();

    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    UtilDateModel fromModel = new UtilDateModel();
    UtilDateModel toModel = new UtilDateModel();

    fromModel.setSelected(true);
    toModel.setSelected(true);

    JDatePanelImpl fromDatePanel = new JDatePanelImpl(fromModel, p);
    JDatePanelImpl toDatePanel = new JDatePanelImpl(toModel, p);

    this.from = new JDatePickerImpl(fromDatePanel, new DateComponentFormatter());
    this.to = new JDatePickerImpl(toDatePanel, new DateComponentFormatter());
    this.output = new JLabel("");

    mainPanel.add(this.portfolios);
    mainPanel.add(this.from);
    mainPanel.add(this.to);

    box.add(mainPanel);
    box.add(this.chart);
    box.add(this.output);

    this.add(box, BorderLayout.CENTER);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void display(String text) {
    this.setOutputText(text);
    this.output.setForeground(Color.GREEN);
  }

  @Override
  public void error(String text) {
    this.setOutputText(text);
    this.output.setForeground(Color.RED);
  }

  private void setOutputText(String text) {
    this.output.setText(text);
  }

  @Override
  public void addFeatures(Features features) {
    features.getPortfolios().forEach(e -> this.portfolios.addItem(e));
    this.submit.addActionListener(e -> validateAndRenderChart(e, features));
  }

  private void validateAndRenderChart(ActionEvent e, Features features) {
    Date f = (Date) this.from.getModel().getValue();
    LocalDate ff = f.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    Date g = (Date) this.to.getModel().getValue();
    LocalDate gg = g.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    String portfolioName = String.valueOf(this.portfolios.getSelectedItem());

    // TODO: Validate the above properties
    // TODO: Rename the above properties.

    var data = features.values(portfolioName, ff, gg);

    // TODO: Implement the bar drawing method.
    draw(data);
  }

  private void draw(Map<LocalDate, Double> data) {
    this.chart.removeAll();
    this.chart.setData(data);
    this.chart.revalidate();
    this.chart.repaint();
  }

  // TODO: Move to another class?
  class BarChartPanel extends JPanel {
    private static final long serialVersionUID = 8242113760993687819L;
    private final int margin = 20;
    private final Color background = Color.WHITE;
    private final List<Color> barColors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.GRAY,
            Color.MAGENTA, Color.ORANGE);

    private int width, height, y;
    private Map<LocalDate, Double> data;

    public BarChartPanel(Map<LocalDate, Double> data) {
      this.data = data;
    }

    public void setData(Map<LocalDate, Double> data2) {
      this.data = data2;
    }

    @Override
    protected void paintComponent(final Graphics g) {
      super.paintComponent(g);

      this.width = this.getWidth() - (2 * margin);
      this.height = this.getHeight() - (4 * margin);
      this.y = this.height + margin;
      draw((Graphics2D) g);
    }

    private void draw(Graphics2D g) {
      g.setColor(background);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      if(this.data == null || this.data.size() == 0) {
        return;
      }

      double max = Collections.max(this.data.values());
      int barWidth = (int) (this.width / this.data.size());
      int gap = (int) Math.floor(0.1 * barWidth);

      int count = 0;
      for (var element : data.entrySet()) {
        int eachbarHeight = (int)(element.getValue() * this.height/max);
        int eachBarWidth = barWidth - gap;
        int xCoord = margin + barWidth * count;
        int yCoord = this.y - eachbarHeight;

        g.setColor(this.barColors.get(count % this.barColors.size()));
        g.fillRect(xCoord, yCoord, eachBarWidth, eachbarHeight);

        g.setFont(getFont(this.data.size() < 15 ? 0 : -28));
        g.drawString(String.format("%.2f", element.getValue()), xCoord + (eachBarWidth / 4), yCoord - (margin / 8));

        g.setFont(getFont(25));
        g.drawString(element.getKey().toString(), xCoord, this.y + (margin/2));

        count += 1;
      }
    }

    private Font getFont(double angle) {
      Font font = new Font(null, Font.PLAIN, 10);
      AffineTransform affineTransform = new AffineTransform();
      affineTransform.rotate(Math.toRadians(angle), 0, 0);
      return font.deriveFont(affineTransform);
    }

  }
}