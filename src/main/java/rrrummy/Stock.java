package rrrummy;
import java.util.Stack;
import java.util.Collections;

public class Stock {
	private Stack<Tile> stock;
	public Stock() {
		stock = new Stack<Tile>();
		initStock();
	}
	public int size() {return stock.size();}
	public Tile draw() {return stock.pop();}
	private void initStock() {
		for (Tile.Color c : Tile.Color.values()) {
			for (int i = 1; i <= 13; i++) {
				if (c == Tile.Color.JOKER) break;
				stock.push(new Tile(c,i));
				stock.push(new Tile(c,i));
			}
		}
		stock.push(new Tile());
		stock.push(new Tile());
		Collections.shuffle(stock);
	}
}
