Design Changes :

Controller Changes :
1. Segregated the controller logic by using Command Design Pattern. Created a new interface called Executor which works as the interface for command pattern.
2. Created Multiple Command Classes like CreatePortfolio, BuyStock, SellStock, etc.

Model Changes :
1. Added methods to support composition at a date, cost basis in the Portfolio Interface.
2. Created Two Different Implementations of Portfolio to segregate the two different type of portfolio offered by the application.
3. Created Abstract Class - AbstractPortfolio to abstract the common code between the two implementation.
4. Created two different implementation of Trade<T> interface one for supporting simulated Portfolio and another to support transactional
5. Created a Abstract class called AbstractStockTrade to abstract out the common code between the two implementations.
6. Created a new implementation of Stock Data Provider called WebAPIStockDataProvider to support Alpha-Vantage API
7. Created a Coniguration interface extended by CacheProvider to store the cofig settings for the applications
8. Created a implementation called InMemory Configuration class for Configuration which can be used to store key,value pair of application config and uses
cache for faster lookup
9. The StockDataProviderFactory can now the inmemory configuration to lookup the API provider that needs to be used for stock data ( as of now have added WebApi as the provider in configuration cache) but this can be extended to let the user choose which Stock Provider he/she wishes to use.
10. Created A interface for data parser like DataParser<T>
11. Created two implementation of the DataParser, one for TransactionalPortfolio and another for SimulatedPortfolio to have each implementation take care
of its own parsing from string to Portoflio while loading from file or saving to the file.

View Changes :
1. Added a new method in View called "draw"
2. This method accepts map as argument and is responsible for plotting bar chart.
3. Implemented the method in TextualView class.
