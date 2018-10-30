package rrrummy;

import java.util.ArrayList;

public class Game {
	private Table table;
	private Stock stock;
	
	public Game() throws InvalidTileException{
		this.table = new Table();
		this.stock = new Stock();
	}
	
	public Tile playerDraw() {
		return stock.draw();
	}
	
	public void playerPlays(Tile t) {
		table.add(t);
	}
	
	public void playerPlays(ArrayList<Tile> arr) {
		table.add(arr);
	}
	public boolean playerPlays(Tile t ,int toMeld) throws AbleToAddBothSideException {
		return table.add(t,toMeld);
	}
	
	public boolean move (int fromMeld, int removeHeadOrTail, int toMeld) throws AbleToAddBothSideException{
		//removeHeadOrTail 0 for head, others for tail
		if (removeHeadOrTail == 0) return table.add(table.removeHead(fromMeld), toMeld);
		else return table.add(table.removeTail(fromMeld), toMeld);
	}
	public boolean cut(int meldIndex, int at) {
		return table.cut(meldIndex, at);
	}
	public Tile replace(Tile t, int tableIndex, int meldIndex) {
		return table.replace(t, tableIndex, meldIndex);
	}
	
	public void printTable() {
		System.out.println(table);
	}
}
