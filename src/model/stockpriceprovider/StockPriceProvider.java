package model.stockpriceprovider;

import java.time.LocalDate;
import model.stock.Stock;

public interface StockPriceProvider {

  double price(Stock stock, LocalDate date);
}
