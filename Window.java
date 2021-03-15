//Zakary Haider
import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Window extends JFrame implements MouseListener {
	JLabel L1, L2, L4, L5;
	JFrame frame = new JFrame("Market Index: S&P 500 (2018)");
	JList<StockItem> list = new JList<>();
	JSplitPane splitPane = new JSplitPane();
	DefaultListModel<StockItem> listModel = new DefaultListModel<>();
	Font roboto = new Font("Roboto", Font.BOLD, 14);
	JLabel label = new JLabel();
	JPanel panel = new JPanel();
	ArrayList<ArrayList<String>> result;

	public Window() {

		
		list.setModel(listModel);
		JScrollPane scroll = new JScrollPane(list);
		
		try {
			getMarketData();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String symbol = "";
		String name = "";
		String price = "";
		String pe = "";
		String mktcap = "";
		
		for(int i=1; i<result.size(); i++) {
			//System.out.println(result.get(1).get(0));
			symbol = result.get(i).get(0);
			name = result.get(i).get(1);
			price = result.get(i).get(3);
			pe = result.get(i).get(4);
			mktcap = result.get(i).get(9);
			
			listModel.addElement(new StockItem(symbol,name,price,pe,mktcap));
		}

		list.getSelectionModel().addListSelectionListener(e -> {
			StockItem p = list.getSelectedValue();
			label.setText(p.toString2());
		});
		splitPane.setLeftComponent(scroll);


		scroll.setPreferredSize(new Dimension(150,50));



		panel.add(label);

		splitPane.setRightComponent(panel);
		panel.setPreferredSize(new Dimension(1000,700));

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(splitPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public void getMarketData() throws UnsupportedEncodingException, IOException{
		URL url = new URL("https://raw.githubusercontent.com/datasets/s-and-p-500-companies-financials/master/data/constituents-financials.csv");
		result = new ArrayList<ArrayList<String>>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			int i = 0;
			for (String line; (line = reader.readLine()) != null;) {
				result.add(new ArrayList<String>());
				for(int j=0; j<14; j++) {
					String[] arr = line.split(",");
					result.get(i).add(arr[j]);
				}
				i++;
			}
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		new Window();
	}

	public void paint(Graphics d) {
		d.setColor(Color.red);        //default color for drawing
		super.paint(d);
		this.setVisible(true);
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