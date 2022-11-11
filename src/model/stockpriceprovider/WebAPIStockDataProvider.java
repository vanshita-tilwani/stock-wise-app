package model.stockpriceprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.*;
import model.cache.CacheProvider;
import model.cache.InMemoryCacheProvider;
import model.dataparseer.JsonParser;
import model.dataparseer.StockDataParser;

public class WebAPIStockDataProvider implements StockDataProvider {

  private static StockDataProvider instance = new WebAPIStockDataProvider();
  private final String API_KEY;
  private final CacheProvider<String, Map<LocalDate, Double>> stockData;

  private final JsonParser<LocalDate,Double> jsonParser;



  private WebAPIStockDataProvider() {
    this.API_KEY = "ZTKFVCAPQ9L1VWRP";
    this.stockData = new InMemoryCacheProvider<>();
    this.jsonParser = new StockDataParser();
  }

  public static StockDataProvider getInstance(){
    return instance;
  }


  @Override
  public double price(String stock, LocalDate date) throws IllegalArgumentException {
    return this.stockData.get(stock).get(date);
  }

  @Override
  public boolean isValid(String stock) {
    try {
      if(this.stockData.contains(stock)) {
        return true;
      }
      Map<LocalDate, Double> response = this.getAPIResponse(stock);
      this.stockData.put(stock, response);
    }
    catch(IOException e) {
      return false;
    }
    return true;
  }

  private Map<LocalDate, Double> getAPIResponse(String stock) throws IOException {
    String URL = "https://www.alphavantage.co/query" +
            "?function=TIME_SERIES_DAILY&outputsize=full" +
            "&symbol=" + stock + "&apikey="+this.API_KEY + "&datatype=json";
    String data = get(URL);
    Map<LocalDate, Double> map = jsonParser.toMap(data);
    return map;
  }


  private String get(String url) {
    String jsonText;
    try
    {
      URL url1 = new URL(url);
      InputStream is = url1.openStream();
      try
      {
        BufferedReader rd = new BufferedReader(new InputStreamReader(is,
                Charset.forName("UTF-8")));
        jsonText = readAll(rd);

      }
      catch (JSONException e) {
        throw new RuntimeException("Data unavailable");
      } finally {
        is.close();
      }
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for stock");
    }
    return jsonText;
  }

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }
}
