package rrrummy;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

public class Stock {
	private Stack<Tile> stock;
	public Stock(){
		stock = new Stack<Tile>();
		initStock();
	}
	public Stock(ArrayList<Tile> arr) {
		stock = new Stack<Tile>();
		stock.addAll(arr);
	}
	
	public int size() {return stock.size();}
	public Tile draw() {
		if (size() == 0) return null;
		return stock.pop();
	}
	private void initStock(){
		for (Tile.Color c : Tile.Color.values()) {
			if (c == Tile.Color.JOKER) break;
			for (int i = 1; i <= 13; i++) {
				try {
					stock.push(new Tile(c,i));
					stock.push(new Tile(c,i));
				}catch (InvalidTileException e) {
					System.out.println("Stock Intialaztion error");
					System.exit(-1);
				}
			}
		}
		stock.push(new Tile());
		stock.push(new Tile());
		Collections.shuffle(stock);
	}
	public String replay() {
		String str = "";
		for (Tile t: stock) {
			str += t.toString();
			str += " ";
		}
		return str;
	}
}
