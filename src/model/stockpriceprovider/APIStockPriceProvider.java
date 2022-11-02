package model.stockpriceprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.cache.CacheProvider;
import model.cache.InMemoryCacheProvider;
import model.jsonparser.JSONParser;
import model.stock.Stock;

public class APIStockPriceProvider implements StockPriceProvider {
  private CacheProvider<String, Map<LocalDate, Double>> stockData;
  private static final String API_KEY = "B3JNGZS5X8SWJPHX";
  private static final String URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full&symbol=";

  public APIStockPriceProvider() {
    stockData = new InMemoryCacheProvider<>();
  }

  @Override
  public double price(String stock, LocalDate date) throws IllegalArgumentException{
    if(!stockData.get(stock).containsKey(date)) {
      throw new IllegalArgumentException("The data for the "+date+ " does not exist since it" +
              " was a holiday. Please try a different date.\n");
    }
    return stockData.get(stock).get(date);
  }

  @Override
  public boolean isValid(String stock) {
    try {
      this.prepareStockData(stock);
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }


  private void prepareStockData(String stock) {
    try {
      URL url = new URL(URL + stock + "&apikey=" + API_KEY);
      InputStream stream = url.openStream();
      String response = this.read(new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8"))));
      Map<LocalDate, Double> json = JSONParser.parse(response);
      stockData.put(stock, json);
    } catch (Exception e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
  }

  private static String read(BufferedReader in) throws IOException {
    StringBuilder output = new StringBuilder();
    String cp = "";
    while(cp != null) {
      output.append(cp);
      cp = in.readLine();
    }
    Pattern r = Pattern.compile("(?<=Time Series \\(Daily\\)\\\":)(.*)(?=})");
    Matcher m = r.matcher(output.toString());
    m.find();
    return m.group(0);
  }
}
