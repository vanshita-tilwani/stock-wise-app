Assignment 4 (Stocks)

This following features are supported by the application :

1.  Ability to create simulated (inflexible) portfolios with multiple stocks(i.e stock and the number of stock).
2.  Ability to create transactional (flexible) portfolios with multiple stock trades(i.e stock, number of stock, date of transaction, commission fee           associated with the transaction).
3.  Ability to purchase a stock and add it to transactional portfolio
4.  Ability to sell a Stock from the transactional portfolio
5.  Ability to get all the portfolio names that exist in the application.
6.  Ability to get the Final Portfolio Composition i.e the name of the portfolio along with the stock ( stock and its quantity) it is comprised of after       all the transactions has been completed.
7.  Ability to get the Portfolio Composition at a specific date.
8.  Ability to get the total value of the Portfolio at a specific date.
9.  Ability to get cost basis of the transactional portfolio
10. Ability to fetch the portfolio performance over a period of time.
11. Ability to save the Portfolio on disk.
12. Ability to load all the portfolios in the application from the disk.


Description of the functionalities :

1. Ability to create simulated (inflexible) portfolios with multiple stocks(i.e stock and the number of stock)
-- The program starts by displaying the menu options.
-- To create a Simulated Portfolio, user must input 1.
-- The user must then enter the name of the portfolio that it needs to create.
-- Next the user is prompted to enter the number of shares that the portfolio is going to be comprised of
-- Based on the number of shares input by user above the program will ask for the stock symbol and amount/quantity of share of each stock.
-- Once the input is complete, The application will successfully create a Simulated Portfolio.

2. Ability to create transactional (flexible) portfolios with multiple stock trades(i.e stock, number of stock, date of transaction, commission fee           associated with the transaction)
-- The program starts by displaying the menu options.
-- To create a Transactional Portfolio, user must input 2.
-- The user must then enter the name of the portfolio that it needs to create.
-- Next the user is prompted to enter the number of trade purchases that the portfolio is going to be comprised of
-- Based on the number of shares input by user above the program will ask for the stock symbol, amount/quantity of share of each stock, date of purchase and the commission fee for the transaction.
-- Once the input is complete, The application will successfully create a Transactional Portfolio.

3. Ability to purchase a stock and add it to transactional portfolio
-- The program starts by displaying the menu options.
-- To purchase a stock and add it to transactional portfolio, user must input 3.
-- Enter the name of the exisiting transactional portfolio the user wishes to add stock to.
-- Enter the stock that you wish to purchase
-- Enter the number of shares of the stock that you wish to purchase
-- Enter the date of transaction
-- Enter the commission fee associated with the transaction
-- Once the input is completed, the application will successfully add the stock to the portfolio.

4.  Ability to sell a Stock from the transactional portfolio
-- The program starts by displaying the menu options.
-- To sell a stock from the transactional portfolio, user must input 4.
-- Enter the name of the exisiting transactional portfolio the user wishes to sell from.
-- Enter the stock that you wish to sell
-- Enter the number of shares of the stock that you wish to sell
-- Enter the date of transaction
-- Enter the commission fee associated with the transaction
-- Once the input is completed, the application will successfully sell the stock from the portfolio.

5. Ability to get all the portfolio names that exist in the application :
-- The program starts by displaying the menu options.
-- To display all the existing Portfolios in the system, user must input 5.
-- The application displays all the portfolios existing in the application.


6. Ability to get the Portfolio Composition i.e the name of the portfolio along with the stock ( stock and its quantity) it is comprised of :
-- The program starts by displaying the menu options.
-- To display the composition of an existing Portfolios in the system, user must input 6.
-- The program will then prompt the user to enter the portfolio name
-- Once the portfolio name is entered, the application will display the portfolio composition including all transactions.

7.  Ability to get the Portfolio Composition at a specific date.
-- The program starts by displaying the menu options.
-- To display the composition of an existing Portfolios in the system at a specific date, user must input 7.
-- The program will then prompt the user to enter the portfolio name
-- The program will then prompt the user to enter the date
-- Once the portfolio name is entered, the application will display the portfolio composition at the specified date.

8. Ability to get the total value of the Portfolio at a specific date :
-- The program starts by displaying the menu options.
-- To get the total value of an existing Portfolios in the system, user must input 8.
-- The application will then prompt the user to enter the portfolio name for which the evaluation is asked
-- Once the user has input portfolio name, the program prompts the user to input the date at which the evaluation needs to be performed
-- Once the date is input, the application will display the total value of the portfolio at the specified date.

9.  Ability to get cost basis of the transactional portfolio
-- The program starts by displaying the menu options.
-- To get the cost basis an existing transactional Portfolio in the system, user must input 9.
-- The application will then prompt the user to enter the portfolio name for which the evaluation is asked
-- Once the user has input portfolio name, the program prompts the user to input the date at which the cost basis needs to be evaluated
-- Once the date is input, the application will display the cost basis of the portfolio at the specified date.

10. Ability to fetch the portfolio performance over a period of time.
-- The program starts by displaying the menu options.
-- To get the performance chart of Portfolio in the system, user must input 10.
-- The application will then prompt the user to enter the portfolio name for which the performance needs to be evaluated
-- Once the user has input portfolio name, the program prompts the user to input the start date for performance evaluation period
-- The program prompts the user to input the end date for performance evaluation period
-- The application will display the portfolio performance over the specified range.

11. Ability to save the Portolio on disk :
-- The program starts by displaying a menu option.
-- To save an existing Portfolios in the file system, user must input 11.
-- The program then prompts the user to input the portfolio name that needs to be saved in the system.
-- Once the user inputs, the program saves the portfolio in the file system.

6. Ability to load all the portfolios in the application from the disk :
-- The program starts by displaying a menu option.
-- To load Portfolios in the file system, user must input 12.
-- The user will then load the portfolios in the program from /res/portfolio.txt
