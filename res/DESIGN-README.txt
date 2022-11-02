Design: The program is designed in MVC pattern.

Model includes: 

    CacheProvider : This interface is used to implement a cache. It has methods such as get to get an item
    from the cache(for faster read), add an item to cache ( to reduce repository/database calls), contains
    to check if an item already exists in cache.
    InMemoryCacheProvider : This class implements the above CacheProvider interface and maintains an in-memory
    object which can be used to make retreivals faster.

    StockDataProvider : This interface acts as the data provider for a particular stock i.e. it checks
    if the stock is valid and also has methods to get the price of a stock at any date. (Acts as a repository
    layer)
    MockStockDataProvider : This class implements StockDataProvider. It has a set of stocks which are 
    treated as Valid by the implementation and treats any other stock as invalid. This class uses 
    InMemoryCacheProvider for faster retrieval of stock data and to avoid repository/database calls.
    If in future the data source for Stock Data changes, the design will not have to change since another
    data provider can just as easily be implemented using the StockDataProvider interface.
   
    Stock: This interface is used to get the name of the stock and price at any date for the stock.
    StockImpl : This class implements Stock interface. It has a property of type StockDataProvider
    which acts as a repository to fetch the data for the stock.
    
    Trade: This interface represents a trade operation.
    StockTradeImpl : Implements the interface Trade. It has Stock interface as property and additional
    property to keep track of number of shares of the stock.
    
    Portfolio: Interface to represent a Portfolio.
    PortfolioImpl : Implements portfolio interface which is merely a collection of Trade i.e.(StockTradeImpl).
    
    DataRepository: Interface responsible for saving and reading any trade information in any data-source.
    FileRepository: This class implements DataRepository and is responsible for saving any(generic) trade
    information to the File System.
   
    TradeOperation : This interface is responsible for conducting trade operations such as buy, get all available
    trades, save trades and load trades in system.
    PortfolioTradeOperation : This class is a implementation of TradeOperation interface for Portfolio and supports
    the trade of portfolios in the application. Makes use of DataRepository to support save and load of portfolios.
    
Controller includes:

   TradeController: Interface responsible for executing the application.
   PortfolioTradeController : Delegates all the trade operations for Portfolio to respective model.

View include:
   View : Responsible for input values and display to the user.
   TextualView: This is a text based interface and it reads user input from any input stream and is capable
   of displaying data in any Output Stream.
