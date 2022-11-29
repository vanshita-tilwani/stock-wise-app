package view.guiscreens;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import controller.Features;

public class StockWeightScreen extends AbstractScreen {

  private final String name;
  private final double principal;
  private final int stocks;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final int frequency;
  private final double commission;

  private final List<JTextField> stockTickers;
  private final List<JTextField> percentage;

  private final JLabel output;
  public StockWeightScreen(String name, Double principal, int stocks, LocalDate startDate,
                           LocalDate endDate, int frequency, double commission) {
    super("Trading Application - Enter Stock Weightage", "");
    this.name = name;
    this.principal = principal;
    this.stocks = stocks;
    this.startDate = startDate;
    this.endDate = endDate;
    this.frequency = frequency;
    this.commission = commission;
    this.stockTickers = new ArrayList<>();
    this.percentage = new ArrayList<>();
    JPanel mainPanel = new JPanel();
    for(int i = 0; i < this.stocks; i ++) {
      JPanel stockPanel = new JPanel();
      var stockLabel = new JLabel("Enter the stock symbol : ");
      this.stockTickers.add(new JTextField(10));
      stockPanel.add(stockLabel);
      stockPanel.add(stockTickers.get(i));

      JPanel percentagePanel = new JPanel();
      var percentageLabel = new JLabel("Enter the percentage : ");
      this.percentage.add(new JTextField(10));
      percentagePanel.add(percentageLabel);
      percentagePanel.add(percentage.get(i));
      mainPanel.add(stockPanel);
      mainPanel.add(percentagePanel);
    }
    this.output = new JLabel();
    mainPanel.add(this.output);
    this.add(mainPanel, BorderLayout.CENTER);
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
    this.submit.addActionListener(f ->
            features.createStrategy(this.name,
            this.principal,
            this.toStockData(),
            this.startDate,
            this.endDate,
            this.frequency,
            this.commission
            ));
  }

  private Map<String, Double> toStockData() {
    var map = new HashMap<String, Double>();
    for(int i = 0; i < stocks; i ++){
      map.put(stockTickers.get(i).getText(), map.getOrDefault(stockTickers.get(i).getText(),0.0)+
              Double.parseDouble(percentage.get(i).getText()));
    }
    return map;
  }
}
