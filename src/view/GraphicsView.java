package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Port;
import javax.swing.*;

import controller.Features;
import view.guiscreens.AllPortfolio;
import view.guiscreens.BuyStock;
import view.guiscreens.CreatePortfolio;
import view.guiscreens.GUIView;
import view.guiscreens.LoadPortfolio;
import view.guiscreens.MainScreen;
import view.guiscreens.PortfolioComposition;
import view.guiscreens.PortfolioCostBasis;
import view.guiscreens.PortfolioValue;
import view.guiscreens.SellStock;

public class GraphicsView implements View, ActionListener {

  private final Map<String, Runnable> actionMap;
  private GUIView currentScreen;
  private GUIView mainScreen;

  private Features features;
  public GraphicsView() {
    this.mainScreen = new MainScreen();
    this.mainScreen.addActionListener(this);
    this.actionMap = initializeMap();
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;
  }

  @Override
  public int readMenu() {
    return 0;
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
  public void draw(Map<LocalDate, Double> portfolioData) {

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
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);
      this.currentScreen.setVisibility(true);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Show All Portfolios", () -> {
      this.currentScreen = new AllPortfolio();
      this.currentScreen.addActionListener(this);
      this.features.getAllPortfolios();
      this.mainScreen.setVisibility(false);
    });
    commands.put("Go Back", () -> {
      this.mainScreen.setVisibility(true);
      ((JFrame)this.currentScreen).dispose();
    });
    commands.put("Buy Stock", () -> {
      this.currentScreen = new BuyStock();
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Sell Stock", () -> {
      this.currentScreen = new SellStock();
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Portfolio Composition", () -> {
      this.currentScreen = new PortfolioComposition();
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Evaluate Value", () -> {
      this.currentScreen = new PortfolioValue();
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Evaluate Cost Basis", () -> {
      this.currentScreen = new PortfolioCostBasis();
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);
      this.mainScreen.setVisibility(false);
    });
    commands.put("Load Portfolio", () -> {
      this.mainScreen.setVisibility(false);
      this.currentScreen = new LoadPortfolio();
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);

    });
    //
    /*Map<String, Runnable> commands = new HashMap<>();
    commands.put("Create Portfolio", () -> {
      menuButton.setActionCommand("Submit - "+this.menuGroup.getSelection().getActionCommand());
    });
    commands.put("Menu - Show All Portfolios", () -> {
      menuButton.setActionCommand("Submit - "+this.menuGroup.getSelection().getActionCommand());
    });
    commands.put("Menu - Buy Stock", () -> {
      menuButton.setActionCommand("Submit - "+this.menuGroup.getSelection().getActionCommand());
    });
    commands.put("Menu - Sell Stock", () -> {
      menuButton.setActionCommand("Submit - "+this.menuGroup.getSelection().getActionCommand());
    });
    commands.put("Menu - Evaluate Value", () -> {
      menuButton.setActionCommand("Submit - "+this.menuGroup.getSelection().getActionCommand());
    });
    commands.put("Menu - Evaluate Cost Basis", () -> {
      menuButton.setActionCommand("Submit - "+this.menuGroup.getSelection().getActionCommand());
    });
    commands.put("Submit - Menu - Create Portfolio", () -> {
      this.currentScreen = new CreatePortfolio();
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);
      this.setVisible(false);
    });
    commands.put("Submit - Menu - Show All Portfolios", () -> {
      this.currentScreen = new AllPortfolio();
      this.currentScreen.addActionListener(this);
      this.features.getAllPortfolios();
      this.setVisible(false);
    });
    commands.put("Submit - Menu - Buy Stock", () -> {
      this.currentScreen = new BuyStock();
      this.currentScreen.addActionListener(this);
      this.currentScreen.addFeatures(features);
      this.setVisible(false);
    });
    commands.put("Go Back", () -> {
      this.setVisible(true);
      this.menuGroup.clearSelection();
      ((JFrame)this.currentScreen).dispose();
    });

    return commands;*/
    return commands;
  }
}
