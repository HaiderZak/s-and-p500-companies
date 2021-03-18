package src;

//Zakary Haider

public class Stock {
	String symbol, name, peTrailing, peForward, mktcap, price, volume;
	public Stock(String symbol, String name, String price, String peTrailing, String peForward, String mktcap, String volume) {
		this.symbol=symbol;
		this.name = name;
		this.price = price;
		this.peTrailing = peTrailing;
		this.peForward = peForward;
		this.volume = volume;
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
	public String getpeTrailing() {
		return peTrailing;
	}
	public String getpeForward() {
		return peForward;
	}
	public String getMktCap() {
		return mktcap;
	}
	public String getVolume() {
		return volume;
	}
	public String toString() {
		return getSymbol();
	}
	public String toString2() {
		return "Symbol: " + getSymbol() + ", Name: " + getName() + ", Price: "  + getPrice() + ", P/E Trailing: " + getpeTrailing() + ", P/E Forward: " + getpeForward() + ", Market Cap: " + getMktCap() + ", Volume: " + getVolume();
	}
}
