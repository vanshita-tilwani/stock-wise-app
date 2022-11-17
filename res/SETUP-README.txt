Setup:
Run "java -jar pdpassignment5.jar" in terminal to run jar file from any location.

Requirement:
Java jdk must be installed correctly.

Step 1 : Steps to create a transactional portfolio with 3 different stock trades
1. Enter 2. 
2. Enter name of the portfolio-> sample1
3. You will be prompted to input the number of stocks for the portfolio, Enter->3.
4. Enter the stock symbol you wish to buy-> AAPL
5. Enter the number of shares you wish to buy->1
6. Enter the date of the transaction -> 2022-10-24
7. Enter the commission fee associated with the transaction -> 10
6. Repeat steps 4 to 7.
   6.1 GOOG->1->2022-10-28->10
   6.2 NOW->2->2022-11-04->10
7. A message saying portfolio has successfully created will be displayed on your screen.
[2->sample1->3->AAPL->1->2022-10-24->10->GOOG->1->2022-10-28->10->NOW->2->2022-11-04->10]

Step 2 : Steps to add more stocks in the portfolio
1. Enter->3. 
2. Enter name of the portfolio you created in Step 1 -> sample1
3. You will be prompted to input the stock you wish to buy, Enter->IBM.
4. Next enter the number of shares you wish to buy-> 5
5. Enter the date of the transaction -> 2022-11-09
6. Enter the commission fee associated with the transaction -> 10
7.A message saying the purchase was successful will be displayed on your screen.
[3->sample1->IBM->5->2022-11-09->10]

Step 3 : Steps to query value of Portfolio created in Step1(i.e. sample1) on a date
1. You will see the menu again, now Enter->8.
2. Enter name of the portfolio for which you want the evaluation. Here ->sample1
3. Enter the date in YYYY-MM-DD format-> 2022-11-02. 
4. This will retrieve the value of the portfolio on the asked date. 
[8->sample1->2022-11-02]

Step 4 : Steps to query value of Portfolio created in Step1(i.e. sample1) on a different date
1. You will see the menu again, now Enter->8.
2. Enter name of the portfolio for which you want the evaluation. Here ->sample1
3. Enter the date in YYYY-MM-DD format-> 2022-11-15. 
4. This will retrieve the value of the portfolio on the asked date. 
[8->sample1->2022-11-15]


Step 5 : Steps to query cost basis of Portfolio created in Step1(i.e. sample1) on a date
1. You will see the menu again, now Enter->9.
2. Enter name of the portfolio for which you want the evaluation. Here ->sample1
3. Enter the date in YYYY-MM-DD format-> 2022-11-02. 
4. This will retrieve the cost basis of the portfolio on the asked date. 
[9->sample1->2022-11-02]

Step 4 : Steps to query cost basis of Portfolio created in Step1(i.e. sample1) on a different date
1. You will see the menu again, now Enter->9.
2. Enter name of the portfolio for which you want the evaluation. Here ->sample1
3. Enter the date in YYYY-MM-DD format-> 2022-11-15. 
4. This will retrieve the cost basis of the portfolio on 2022-11-15. 
[9->sample1->2022-11-15]

Step 5 : Exit the application
Enter any key other than menu option and press enter to exit.

NOTE :
The program supports all the stocks supported by Alpha-Vantage API
The program supports all the dates for portfolio value evaluation
The program does not support purchase/sale transactions to be made on days when Stock Market is closed.

References Used :
1. Nicenum algorithm to understand the algorithm for the scale of bar chart

Library Used :
1. https://mvnrepository.com/artifact/org.json/json - Using org.json for Parsing string to JSON

Setting up in IntelliJ
1. Click setting on the right side in IntelliJ
2. Click on Project Structure in settings
3. From the left hand, select the Library module
4. You will see a + sign
5. Click on the + sign and select "From Maven" option
6. You will see a dialog box with search
7. Enter "org.json:json:20220924" in search and select it.
8. Click on Save, the library will get added to the project.
9. Click Apply and close the settings.
