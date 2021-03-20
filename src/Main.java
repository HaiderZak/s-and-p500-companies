package src;

//Zakary Haider

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.Node;
import com.jaunt.UserAgent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JFrame implements MouseListener {
	private static final long serialVersionUID = 619845908650249193L;
	final int numStocks = 25;
	JFrame frame = new JFrame("Top " + numStocks + " stocks in S&P 500 Market Index");
	JList<Stock> list = new JList<>();
	JSplitPane splitPane = new JSplitPane();
	DefaultListModel<Stock> listModel = new DefaultListModel<>();
	Font roboto = new Font("Roboto", Font.BOLD, 13);
	JLabel label = new JLabel();
	JPanel panel = new JPanel();
	ArrayList<ArrayList<String>> result;
	ArrayList<String> listOfSymbols;
	Timer timer;
	final int DELAY = 10;
	int x = 0;
	String[][] data;
	ArrayList<String> originalList;
	ArrayList<String> vol;
	ArrayList<String> mktcp;
	ArrayList<String> peTList;
	ArrayList<String> peFList;
	ArrayList<String> finalList;

	public Main() throws IOException {
		list.setModel(listModel);
		
		String symbol = "";
		String name = "";
		String price = "";
		String peTrailing = "";
		String peForward = "";
		String mktcap = "";
		String volume = "";
		try {
			getSymbols();
			getMarketPrice();
			getRestOfMarketData();
			getStockName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  
		for(int i=0; i<data.length; i++) {
			symbol = data[i][0];
			name = data[i][1];
			price = data[i][2];
			peTrailing = data[i][3];
			peForward = data[i][4];
			mktcap = data[i][5];
			volume = data[i][6];
			listModel.addElement(new Stock(symbol,name,price,peTrailing,peForward,mktcap,volume));
		}
		
		JLabel label1 = new JLabel();

		splitPane.setLeftComponent(list);
		list.getSelectionModel().addListSelectionListener(e -> {
			if(list.getSelectedIndex() < numStocks) {
				Stock p = list.getSelectedValue();
				try {
					String s = "resources/" + p.getSymbol() + ".png";
					ImageIcon wPic = new ImageIcon(this.getClass().getResource(s));
					label1.setIcon(wPic);	
				}
				catch(Exception ex) {
					ImageIcon wPic = new ImageIcon(this.getClass().getResource("error.png"));
					label1.setIcon(wPic);	
				}
				label.setText(p.toString2());
				label.setFont(roboto);
				label.setForeground(Color.blue);
				label.setLocation(label.getLocation().x, label.getLocation().y);
			}
			else {
				list.setSelectedIndex(numStocks-1);
			}
		});
		
		list.setPreferredSize(new Dimension(150,50));
		panel.add(label);
		panel.add(label1);
		panel.setBackground(Color.WHITE);
		splitPane.setRightComponent(panel);
		panel.setPreferredSize(new Dimension(1000,700));

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(splitPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public void getStockName() throws UnsupportedEncodingException, IOException{
		try {
			UserAgent userAgent = new UserAgent(); 
			for(int i=0; i<numStocks; i++) {
				userAgent.visit("https://www.slickcharts.com/symbol/" + listOfSymbols.get(i));
				Element meta = userAgent.doc.findFirst("<h3");
				data[i][1] = meta.getChildText();
			}
		} catch (JauntException e) {
			e.printStackTrace();
		}
	}
	
	public void getRestOfMarketData() throws UnsupportedEncodingException, IOException{
		try {
			UserAgent userAgent = new UserAgent();
			originalList = new ArrayList<String>();
			for(int i=0; i<numStocks; i++) {
				userAgent.visit("https://www.slickcharts.com/symbol/" + listOfSymbols.get(i));
				Element meta = userAgent.doc.findFirst("<tbody>");
				ArrayList<Node> childNodes = (ArrayList<Node>) meta.getChildNodes();
				for(Node node : childNodes) {
					if(node.getType() == Node.ELEMENT_TYPE) {
						originalList.add(((Element)node).outerHTML());
					}
				}
			}
			char[] charArr = originalList.toString().toCharArray();
			String bufferString = new String(charArr).replaceAll(" ", "");
			Scanner scanner = new Scanner(bufferString);
			ArrayList<String> finalArr = new ArrayList<String>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				finalArr.add(line);
			}
			scanner.close();
			String volume = "";
			String peTrailing = "";
			String peForward = "";
			String mktcap = "";
			vol = new ArrayList<String>();
			mktcp = new ArrayList<String>();
			peTList = new ArrayList<String>();
			peFList = new ArrayList<String>();
			for(int i=0; i<finalArr.size()-1; i++) {
				if(finalArr.get(i).contains("Volume")) {
					volume = finalArr.get(i+1).replaceAll("[^0-9.]", "");
					vol.add(volume);
				}
				if(finalArr.get(i).contains("Market")) {
					mktcap = finalArr.get(i+1).substring(4,finalArr.get(i+1).length()-5);
					mktcp.add(mktcap);
				}
				if(finalArr.get(i).contains("Trailing")) {
					peTrailing = finalArr.get(i+1).replaceAll("[^0-9.]", "");
					peTList.add(peTrailing);
				}
				if(finalArr.get(i).contains("Forward")) {
					peForward = finalArr.get(i+1).replaceAll("[^0-9.]", "");
					peFList.add(peForward);
				}
			}
			for(int i=0; i<mktcp.size(); i++) {
				data[i][5] = mktcp.get(i);
			}
			for(int i=0; i<vol.size(); i++) {
				data[i][6] = vol.get(i);
			}
			for(int i=0; i<peTList.size(); i++) {
				data[i][3] = peTList.get(i);
			}
			for(int i=0; i<peFList.size(); i++) {
				data[i][4] = peFList.get(i);
			}
		}
		catch (JauntException e) {
			e.printStackTrace();
		}
	}

	//get and set symbols and price data
	public void getMarketPrice() throws UnsupportedEncodingException, IOException{
		try {
			UserAgent userAgent = new UserAgent();                      
			data = new String[listOfSymbols.size()][7];
			for(int i=0; i<numStocks; i++) {
				userAgent.visit("https://www.slickcharts.com/symbol/" + listOfSymbols.get(i));
				Element meta = userAgent.doc.findEvery("<tr>").findFirst("<td>");
				data[i][0] = listOfSymbols.get(i);
				data[i][2] = meta.getChildText();
			}
		} catch (JauntException e) {
			e.printStackTrace();
		}
	}

	//get symbols
	public void getSymbols() throws UnsupportedEncodingException, IOException{
		try {
			UserAgent userAgent = new UserAgent();                       //create new userAgent (headless browser).
			userAgent.visit("https://www.slickcharts.com/sp500");
			Elements meta = userAgent.doc.findEvery("<tbody>").findEvery("<tr>").findEvery("<td>").findEvery("<a href=");	
			int i = 0;
			listOfSymbols = new ArrayList<String>();
			for(Element link : meta) {
				if(i%2 != 0) {
					listOfSymbols.add(link.getChildText());
				}
				i++;
			}
		    @SuppressWarnings("resource")
			FileWriter myWriter = new FileWriter("symbols.txt");
		    int j = 0;
			for(j=0; j<numStocks-1; j++){
				myWriter.write(listOfSymbols.get(j).replace('.', '-') + ",");
			}
			myWriter.write(listOfSymbols.get(j+1));
			finalList = new ArrayList<String>();
			for(int l=0; l<numStocks; l++) {
				finalList.add(listOfSymbols.get(l));
			}
			myWriter.close();
		}
		catch (JauntException e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		EventQueue.invokeLater( new Runnable () {

			@Override
			public void run() {
				try {
					new Main();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}