Design: The program is designed in MVC pattern. Basic operation is handled by model.

Model includes: 
   
    Stock: It includes a stock symbol, and the price of this stock.

    Portfolio: It includes a portfolio name and a map of stock data. 
    
    TradeOperation: It includes operations such as buying trade, getting the trade evaluation
    on a specific date and saving the data.
    
    StockDataProvider: Contains the price of stocks.
    
    Trade: Represents a Trade of stocks i.e. has properties with stock and the number of stocks 
    contributing in the trade.
  

Controller includes:
   TradeController: Controls the entire program. Performs taks like asking view to provide user input, 
   passing the input to model to get the result and printing the result. The process continues until 
   the program ends.

View include:
   TextualView: This is a text based interface and it reads user input and output message from
   command line. TextualView includes an option to read data from the file as well.
