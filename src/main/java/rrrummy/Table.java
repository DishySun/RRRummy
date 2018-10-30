package rrrummy;
import java.util.ArrayList;

public class Table {
	private ArrayList<Meld> table;
	public Table() {
		table = new ArrayList<Meld>();
	}
	
	public int size() {return table.size();}
	public boolean add(Tile t) {
		//TODO: add Tile t to a new meld
		try{
			table.add(new Meld(t));
			return true;
		}catch(AbleToAddBothSideException e) {
			return false;
		}
		
	}
	public boolean add(Tile t, int i) throws AbleToAddBothSideException{
		if (i >= this.size()) return false;
		return table.get(i).add(t);
	}
	public void add(Meld m) {
		table.add(m);
	}
	public Tile removeHead(int i) {
		return table.get(i).removeHead();
	}
	
	public Tile removeTail(int i) {
		return table.get(i).removeTail();
	}
	
	public void remove(int i,Tile t) {
		table.get(i).removeTile(t);
	}
	
	public Tile replace(Tile t, int tableIndex, int meldIndex) {
		return table.get(tableIndex).replace(t, meldIndex);
	}
	
	public void cut(int meldIndex, int tileIndex) {
		table.add(table.get(meldIndex).cut(tileIndex));
	}
	
	public Meld getMeld(int i) {return table.get(i);}
	public ArrayList<Meld> getMeld() {return table;}
}
