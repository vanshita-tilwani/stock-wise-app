package model.stockpriceprovider;

import java.time.LocalDate;
import model.stock.IStock;

public interface IStockPriceProvider {

  double price(IStock stock, LocalDate date);
}
