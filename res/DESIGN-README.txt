Design Changes :

Controller Changes :
1. Created a new Interface called "Features" which has all the API supported by the application.
2. The Controller class now implements "Features" interface and implements all the API, calls the model
to get specific information required in the API and returns the result back to View.
3. Removed all the command executors which were used previously since now View controls the application and
not Controller.

Model Changes (not user facing) :
1. Created a new interface for Strategy.
2. Implemented the Strategy Interface called InvestmentStrategy which support Fixed One time Investment
as well as Recurring Investments.
3. Created a StrategyBuilder interface which can be used to build strategy objects.
4. Implemented StrategyBuilder interface which supports setting name, start date, end date, commission fee,
frequency of the strategy. Also support method to add stock and its weight to the strategy. Builder pattern
used to simplify building a strategy.
5. Created a new Implementation for StrategyOperation which implements TradeOperation<T> interface where T stands for Strategy.
6. StrategyOperation supports creating strategy, getting a specific strategy, getting all the strategies
from the application.
7. Added a new method in Portfolio interface to apply Strategy on the portfolio.
8. Implemented the above method in both Simulated and Transactional Portfolio.

View Changes :
1. Added a new method in View called "setFeatures" which supports all the API by the view.
2. Created a new implementation for GUI for the view.
3. Created a Screen interface which is used by GUIView to render different screens based on the selected option.
4. Created Multiple implementation of the Screen interface to support Create Portfolio, Get ALL Portfolio etc in
the GUI.
