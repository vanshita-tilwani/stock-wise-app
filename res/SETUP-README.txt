Setup:
Unzip the zipfile and change directory to res.
Input "java -jar pdpassignment4.jar" in terminal to run jar file.

Requirement:
Java 1.8 jdk must be installed correctly.

Steps to create a portfolio with 3 different stocks:
1. Enter 1. 
2. Enter name of the portfolio-> sample1
3. You will be prompted to input the number of stocks for the portfolio, Enter->3.
4. Next Enter the stock symbol you wish to buy-> AAPL
5. After step 4 you will Enter the number of shares you wish to buy->100
6. Repeat steps 4 and 5.
   6.1 NFLX->35
   6.2 IMB->40
7. If there are no problems encountered then the output message will be "Portfolio created successfully."  
[1->sample1->3->AAPL->100->NFLX->35->IMB->40]

Steps to create a portfolio with 2 different stocks and query their value on a specific date.
1. Enter->1. 
2. Enter name of the portfolio-> sample2
3. You will be prompted to input the number of stocks for the portfolio, Enter->3.
4. Next Enter the stock symbol you wish to buy-> MCD
5. After step 4 you will Enter the number of shares you wish to buy->100
6. Repeat steps 4 and 5.
   6.1 NFLX->35
7. If there are no problems encountered then the output message will be "Portfolio created successfully."  
8. You will see the menu again, now Enter->4.
9. Enter name of the portfolio for which you want the evaluation. Here ->sample2
10. Enter the date in YYYY-MM-DD format-> 2022-11-01. 
11. This will retrieve the value of the portfolio on the current date. OUTPUT:6475.0 
[1->sample2->2->MCD->100->NFLX->35->4->sample2->2022-11-01]


Valid Ticker symbols permitted by the program:
"AAPL","MSFT","GOOG","GOOGL", "AMZN","TSLA","BRK/A","BRK/B","UNH","XOM","JNJ","V",
"WMT","JPM","CVX", "NVDA", "LLY","PG","TSM","MA","HD","BAC","PFE","NVO","ABBV","KO",
"MRK","PEP","META","COST","ORCL","MCD","TMO","SHEL","AVGO","DIS","TMUS","AZN","TM",
"CSCO","ASML","DHR","WFC","ACN","ABT","NVS","BABA","BMY","COP","VZ","NEE","CRM",
"SCHW","TXN","LIN","AMGN","UPS","NKE","MS","ADBE","PM", "RTX","TTE","HON","CMCSA",
"T","QCOM","RY","ELV","CVS","LMT","NFLX","BHP","IBM","UNP","GS","LOW","DE","INTC",
"EQNR","TD","MDT","HDB","CAT","UL","INTU","SAP","AXP","SNY","HSBC","SPGI","BP","ADP",
"BUD","PLD","SBUX","GILD","AMD","CI","BLK","AMT","PYPL","DEO","BA","C","SONY","CB",
"RIO","MDLZ"
