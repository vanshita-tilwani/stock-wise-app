import controller.TradeController;
import controller.StockTraderController;
import model.stocktradings.Trade;
import model.stocktradings.StockTrade;
import view.CommandPromptView;
import view.View;

public class ProgramRunner {
  public static void main(String[] args) {
    View view = new CommandPromptView();
    Trade model = new StockTrade();
    TradeController controller = new StockTraderController(view, model);
    controller.execute();
  }

}
