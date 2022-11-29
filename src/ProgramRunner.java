import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.Features;
import controller.PortfolioTradeController;
import model.datarepo.DataRepository;
import model.datarepo.FileRepository;
import model.stocktradings.PortfolioTradeOperation;

import view.GraphicsView;
import view.TextualView;
import view.View;

/**
 * Client code that runs the program.
 */
public class ProgramRunner {

  /**
   * A main method to run this program.
   *
   * @param args arguments to run the method.
   */

  public static void main(String[] args)  {
    Map<Integer, Runnable> menuOptions = getCommands();
    System.out.println("Choose the type of view you wish to proceed with?");
    System.out.println("1. Console Based View");
    System.out.println("2. GUI Based View");
    int menuOption = new Scanner(System.in).nextInt();
    while(!menuOptions.containsKey(menuOption)) {
      System.out.println("Invalid Option");
      System.out.println("Choose the type of view you wish to proceed with?");
      System.out.println("1. Console Based View");
      System.out.println("2. GUI Based View");
      menuOption = new Scanner(System.in).nextInt();
    }
    menuOptions.get(menuOption).run();
  }

  public static Map<Integer, Runnable> getCommands() {
    DataRepository fileRepo = null;
    try {
      fileRepo = FileRepository.getInstance();
      fileRepo.setDataSource("res/portfolio.txt");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    Map<Integer, Runnable> menuOptions = new HashMap<>();
    DataRepository finalFileRepo = fileRepo;

    menuOptions.put(1, () -> {
      View view = new TextualView(System.in, System.out);
      PortfolioTradeOperation model = new PortfolioTradeOperation(finalFileRepo);
      Features controller = new PortfolioTradeController(view, model);
    });
    menuOptions.put(2, () -> {
      View view = new GraphicsView();
      PortfolioTradeOperation model = new PortfolioTradeOperation(finalFileRepo);
      Features controller = new PortfolioTradeController(view, model);
    });
    return menuOptions;
  }

}
