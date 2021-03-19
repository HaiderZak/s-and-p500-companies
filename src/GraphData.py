import matplotlib.pyplot as plt
import yfinance as yf   

class GraphData:
    ticker = ""
    def __init__(self, symbol):
        self.ticker = symbol
    def getData(self):
        yf.download(self.ticker)
        newtime = yf.download(self.ticker, start = "2014-01-01")
        newtime['Adj Close'].plot()
        plt.xlabel("Date")
        plt.ylabel("Price")
        label = yf.Ticker(self.ticker)
        company_name = label.info['longName']
        plt.title(company_name + " Price Chart")
        plt.savefig('src/resources/' + self.ticker + '.png')
        plt.clf()  


f = open("symbols.txt")
str = ""
for x in f:
  str += x
f.close()

finalList = str.split(',')

for l in finalList:
    newInst = GraphData(l)
    newInst.getData()