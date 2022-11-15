import java.time.LocalDate;
import java.util.Map;

import view.View;

public class MockView implements View {

  private StringBuilder log;

  public MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public String input() {
    return log.toString();
  }

  @Override
  public void display(String message) {
    log.append(message);
  }

  @Override
  public void draw(Map<LocalDate, Double> portfolioData) {
    log.append(portfolioData.toString());
  }
}
