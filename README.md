# GUI Application to display information about stocks listed on S&P 500 market index
Written in Java (like most of my applications), this app logs data from an outside github source and translates it into a format that is presentable in GUI. 

It takes the top 30 stocks listed on S&P 500 market index using the most recent data from slickcharts.com. This number (30) can be higher, but data retrieval using this technique may take some time.

The app uses Jaunt API to search through HTML code and pull the relevant data so it can be used inside of the application. A better approach would be to host this data on my own server and update the server a specific number of times per day (or whenever) -- however the goal of this project is to give the client-side all of the power without having to rely on an outside source. 

![stockapp](https://user-images.githubusercontent.com/37321974/111355726-068ec600-865e-11eb-905d-86054532f7d6.PNG)
