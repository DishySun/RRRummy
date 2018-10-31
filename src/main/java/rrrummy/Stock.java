package rrrummy;
import java.util.Stack;
import java.util.Collections;

public class Stock {
	private Stack<Tile> stock;
	public Stock() throws InvalidTileException{
		stock = new Stack<Tile>();
		initStock();
	}
	public int size() {return stock.size();}
	public Tile draw() {
		if (size() == 0) return null;
		return stock.pop();
	}
	private void initStock() throws InvalidTileException{
		for (Tile.Color c : Tile.Color.values()) {
			if (c == Tile.Color.JOKER) break;
			for (int i = 1; i <= 13; i++) {
				stock.push(new Tile(c,i));
				stock.push(new Tile(c,i));
			}
		}
		stock.push(new Tile());
		stock.push(new Tile());
		Collections.shuffle(stock);
	}
}
