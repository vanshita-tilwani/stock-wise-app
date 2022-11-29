package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import controller.Features;
import view.guiscreens.AllPortfolio;
import view.guiscreens.ApplyStrategyToPortfolio;
import view.guiscreens.BarChart;
import view.guiscreens.BuyStock;
import view.guiscreens.CreatePortfolio;
import view.guiscreens.OneTimeInvestmentStrategy;
import view.guiscreens.RecurringInvestmentStrategy;
import view.guiscreens.SavePortfolio;
import view.guiscreens.Screen;
import view.guiscreens.LoadPortfolio;
import view.guiscreens.MainScreen;
import view.guiscreens.PortfolioComposition;
import view.guiscreens.PortfolioCostBasis;
import view.guiscreens.PortfolioValue;
import view.guiscreens.SellStock;

public class GraphicsView implements View, ActionListener {

  private final Map<String, Runnable> actionMap;
  private Screen currentScreen;
  private Screen mainScreen;

  private Features features;
  public GraphicsView() {
    setDefaultUI();
    this.mainScreen = new MainScreen();
    this.mainScreen.bindListener(this);
    this.actionMap = initializeMap();
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;
  }

  @Override
  public String input() {
    return null;
  }

  @Override
  public void display(String text) {
    this.currentScreen.display(text);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    var actionCommand = e.getActionCommand();
    this.actionMap.get(actionCommand).run();
  }

  private Map<String, Runnable> initializeMap() {
    Map<String, Runnable> commands  = new HashMap<>();
    commands.put("Create Portfolio", () -> {
      System.out.println("Create");
      this.currentScreen = new CreatePortfolio();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
      this.currentScreen.setVisibility(true);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Show All Portfolios", () -> {
      this.currentScreen = new AllPortfolio();
      this.currentScreen.addFeatures(features);
      this.currentScreen.bindListener(this);
      this.features.getPortfolios();
      this.mainScreen.setVisibility(false);
    });
    commands.put("Go Back", () -> {
      this.mainScreen.setVisibility(true);
      ((JFrame)this.currentScreen).dispose();
    });
    commands.put("Buy Stock", () -> {
      this.currentScreen = new BuyStock();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Sell Stock", () -> {
      this.currentScreen = new SellStock();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Portfolio Composition", () -> {
      this.currentScreen = new PortfolioComposition();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Evaluate Value", () -> {
      this.currentScreen = new PortfolioValue();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Evaluate Cost Basis", () -> {
      this.currentScreen = new PortfolioCostBasis();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Load Portfolio", () -> {
      this.mainScreen.setVisibility(false);
      this.currentScreen = new LoadPortfolio();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);

    });
    commands.put("Save Portfolio", () -> {
      this.mainScreen.setVisibility(false);
      this.currentScreen = new SavePortfolio();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);

    });
    commands.put("Show Bar Chart", () -> {
      this.mainScreen.setVisibility(false);
      this.currentScreen = new BarChart();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
    });
    commands.put("Create a one time investment strategy", () -> {
      this.mainScreen.setVisibility(false);
      this.currentScreen = new OneTimeInvestmentStrategy();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
    });
    commands.put("Create a recurring investment strategy", () -> {
      this.mainScreen.setVisibility(false);
      this.currentScreen = new RecurringInvestmentStrategy();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
    });
    commands.put("Apply a strategy to a portfolio", () -> {
      this.mainScreen.setVisibility(false);
      this.currentScreen = new ApplyStrategyToPortfolio();
      this.currentScreen.bindListener(this);
      this.currentScreen.addFeatures(features);
    });
    //
    return commands;
  }

  private void setDefaultUI() {
    Font f = new Font(null, Font.PLAIN, 15);
    UIManager.put("Button.font", f.deriveFont(1));
    UIManager.put("RadioButton.font", f.deriveFont(14.0F));
    UIManager.put("Label.font", f);
    UIManager.put("TextField.font", f);
  }
}
