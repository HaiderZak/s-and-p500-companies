# GUI Application to display information about stocks listed on S&P 500 market index (Web scraper)
Written in Java (like most of my applications) and some Python, this app logs data from an outside github source and translates it into a format that is presentable in GUI. 

It takes the top 15 stocks listed on S&P 500 market index using the most recent data from slickcharts.com. This number (15) can be higher, but data retrieval using this technique may take some time.

The app uses Jaunt API to search through HTML code and pull the relevant data so it can be used inside of the application. A better approach would be to host this data on my own server and update the server a specific number of times per day (or whenever) -- however the goal of this project is to give the client-side all of the power without having to rely on an outside source. Due to the lack of an efficient server communication, application loading time may take up to 2 minutes.

![Top 15 stocks in SP 500 Market Index 2021-03-18 19-16-29_Trim](https://user-images.githubusercontent.com/37321974/111709889-e1958100-881e-11eb-8e91-b92580658ad2.gif)

How to Download:

git clone https://github.com/HaiderZak/s-and-p500-companies

run Main.java
