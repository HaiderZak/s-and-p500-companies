//Zakary Haider

public class StockItem {
	String symbol, name, pe, mktcap, price;
	public StockItem(String symbol, String name, String price, String pe, String mktcap) {
		this.symbol=symbol;
		this.name = name;
		this.price = price;
		this.pe = pe;
		this.mktcap = mktcap;
	}
	public String getSymbol() {
		return symbol;
	}
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}
	public String getPE() {
		return pe;
	}
	public String getMktCap() {
		return mktcap;
	}
	public String toString() {
		return getSymbol();
	}
	public String toString2() {
		return "Symbol: " + getSymbol() + ", Name: " + getName() + ", Price: "  + getPrice() + ", P/E Ratio: " + getPE() + ", Market Cap: " + getMktCap();
	}
}
