1 Overall Vision
Gone are the days when one could put money in a bank account and see it grow with interest. One of the very few ways to grow money these days is to invest in the stock market. Such investments can be found virtually everywhere: retirement accounts, college savings plans, health savings accounts, etc. To be successful, one must understand what stock is and how it is traded.

However it is intimidating for a newbie to invest in the stock market. Unlike a savings account, it is possible to lose money in the stock market, in pursuit of greater growth. In order to invest in the stock market, one must have a basic understanding of how stocks work, how investments work, and how to strategize investment. There is no dearth of books, blogs and articles that give investment advice. This advice ranges from simple invest-and-forget ideas to devoting hours each day looking at data and charts. One could hire professional help (a financial broker), but that costs more money.

We will write an application that helps users who are new to investing to learn about how money could grow, in the style of "virtual gambling". Similar to virtual gambling, our application will not use real money and investment. Instead it will allow the user to create investment portfolios, try out buying and selling of stock, and various investment strategies to see how they can grow (or shrink) their money with time.

2 Introduction to stocks
In its simplest form, a stock of a company is a part of ownership in that company. Ownership is divided into shares, where a share represents a fraction of the total ownership. For example, Apple has about 16.07B shares. So if you own 100 shares, you own about  % of the company (and it would be worth about $15000 today). As a shareholder, you are an investor in the company. An investor sends money to the company to buy some of its stock, and gets part ownership in return. The total money invested in the stock (money spent buying it) is called the cost basis of the purchase. The value of the stock on a particular day is the money the investor would receive if he/she sold the stock on that day.

When the company performs well (e.g. it makes a profit, it expands its business, does business with more money, etc.) the price of the stock goes up because its value in the stock market increases (everybody wants to own that stock). When an investor sells stocks whose prices have risen above their cost bases, they make a profit (they get more money than they had paid to buy the stock earlier). An investor may also benefit in another, unrelated way. Many companies share some of their profits as dividends with their investors. This is not directly related to the price of stock (i.e. the price of the stock may go down, even as the company distributes dividend to its investors). Some companies distribute dividends; others do not. Although this is another way to earn money through investment, we will not consider this type of dividend-based income in this application.

Thousands of companies' stock are available in several stock markets around the world. In addition to individual stocks, an investor can also buy mutual funds and exchange-traded funds. These funds are combinations of several company stocks (called a portfolio of stocks). For example a mutual fund may contain stocks of 500 companies based on some preferences. Investing in a portfolio of stocks reduces risk (if 10 of those companies suffer price declines in the stock market, the overall loss to the investor is less severe because the other 490 companies have remained approximately the same price as the day before). On the other hand, it can also reduce profit (if 10 of those companies soar at the stock market, the overall gain to the investor is averaged out by the gains and losses of the other 490 companies).

3 Stocks and portfolios
Each publicly traded company's stock is given a unique "ticker symbol" which is used to trade it (for example, Apple Inc. is AAPL, Microsoft is MSFT). The price of stock keeps changing all day depending on how many people want to own that stock versus how many people want to sell their shares. The behavior of a US stock during a day can be understood by its opening price (at 8am EST when the New York Stock Exchange opens for business), its closing price (at 4pm EST when the NYSE closes for regular business), its low and high price (during the day). At any point in time, the total value of an investor's holdings of a stock is simply the price of the stock at that time, multiplied by the number of shares of that stock that the investor owns.

A portfolio of stocks is simply a collection of stocks (e.g. 10 shares of company X, 20 shares of company Y, etc.). The total value of a portfolio is then the sum of the values of its individual holdings. Thus the value of a portfolio, much like individual stocks, also changes with time. As an example, we hear about the S&P 500, Dow Jones Industrial and NASDAQ in the news, quoted to gauge the health of the overall stock market. These three "indices" are nothing more than portfolios of stocks of specific companies, which have been found to be reasonable barometers of the health of the overall stock market and economy, or a specific part of them (e.g. technology).

An individual investor may track multiple portfolios: a retirement portfolio, a college-savings portfolio, health savings portfolio, etc.

4 What to do
In this set of assignments, we will build this application in the model-view-controller style. However instead of concentrating on the model in one assignment and the view in the next, we will attempt to build this application end-to-end in each assignment. Future assignments will then add "features" to our program, affecting multiple layers accordingly to deliver an progressively enhanced program.

Your objective in this assignment is to build a program that has the following features:

Allow a user to create one or more portfolios with shares of one or more stock. Once created, shares cannot be added or removed from the portfolio.

Examine the composition of a portfolio.

Determine the total value of a portfolio on a certain date.

Persist a portfolio so that it can be saved and loaded (i.e. save to and retrieve from files)

The files used to save and retrieve portfolios must be human-readable, text files. While you are free to determine the format of these files, using standard formats like XML or JSON has natural advantages. In any case, a user should be able to create such a file outside of the program using a text editor and have your program load it in.
