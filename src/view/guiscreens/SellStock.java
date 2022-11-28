package view.guiscreens;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;

import controller.Features;

public class SellStock extends AbstractScreen {
  private final JTextField portfolioName;

  private final JTextField date;
  private final JLabel output;
  private final JTextField stock;
  private final JSpinner shares;
  private final JSpinner commission;
  public SellStock() {
    super("Sell Stock", "Enter the data for stock to be sold");
    JPanel portfolioDetails = new JPanel();


    this.portfolioName = new javax.swing.JTextField(20);
    var nameLabel = new JLabel("Enter the portfolio name : ");
    this.portfolioName.setToolTipText("Enter Portfolio Name");
    portfolioDetails.add(nameLabel);
    portfolioDetails.add(this.portfolioName);
    JPanel stockData = new JPanel();
    this.stock = new javax.swing.JTextField(8);
    this.stock.setToolTipText("Enter the name of the stock you wish to sell");
    stockData.add(new JLabel("Enter the name of the stock : "));
    stockData.add(this.stock);
    JPanel shareData = new JPanel();

    this.shares = new javax.swing.JSpinner();
    this.shares.setToolTipText("Enter the number of shares you wish to sell");
    shareData.add(new JLabel("Enter the number of shares of the stock : "));
    shareData.add(this.shares);
    JPanel purchaseData = new JPanel();

    this.date = new javax.swing.JTextField(8);
    this.date.setToolTipText("Enter the date of sale");
    purchaseData.add(new JLabel("Enter the date of sale of the stock : "));
    purchaseData.add(this.date);
    JPanel commissionData = new JPanel();

    this.commission = new javax.swing.JSpinner();
    this.commission.setToolTipText("Enter the commission fee for the sale");
    commissionData.add(new JLabel("Enter the commission fee for the sale : "));
    commissionData.add(this.commission);
    this.output = new JLabel("");
    //this.datePicker = new JDatePickerImpl();
    var mainPanel = new JPanel();
    mainPanel.add(new JScrollPane());

    mainPanel.add(portfolioDetails);
    mainPanel.add(stockData);
    mainPanel.add(shareData);
    mainPanel.add(purchaseData);
    mainPanel.add(commissionData);
    mainPanel.add(this.output);
    this.add(mainPanel, BorderLayout.CENTER);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void display(String text) {
    this.output.setText(text);
  }

  @Override
  public void addFeatures(Features features) {
    this.submit.addActionListener(e ->
            features.sellFromPortfolio(this.portfolioName.getText(),
                    this.stock.getText(),
                    Double.parseDouble(this.shares.getValue()+""),
                    LocalDate.parse(this.date.getText()),
                    Double.parseDouble(this.shares.getValue() + "")));
  }

}
