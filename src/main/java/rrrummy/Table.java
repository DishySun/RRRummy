package rrrummy;
import java.util.ArrayList;

public class Table {
	private ArrayList<Meld> table;
	public Table() {
		table = new ArrayList<Meld>();
	}
	
	public int size() {return table.size();}
	public void add(Tile t) {
			table.add(new Meld(t));
	}
	public void add(ArrayList<Tile> arr) {
		table.add(new Meld(arr));
	}
	public boolean add(Tile t, int i) throws AbleToAddBothSideException{
		if (i >= this.size()) return false;
		return table.get(i).add(t);
	}
	
	public Tile removeHead(int i) {
		return table.get(i).removeHead();
	}
	
	public Tile removeTail(int i) {
		return table.get(i).removeTail();
	}
	
	public Tile replace(Tile t, int tableIndex, int meldIndex) {
		return table.get(tableIndex).replace(t, meldIndex);
	}
	
	public boolean cut(int meldIndex, int tileIndex) {
		if ()
		table.add(table.get(meldIndex).cut(tileIndex));
	}
	
	public String toString() {
		String result = "Table:\n";
		for (int i = 0; i < table.size(); i++) {
			result += i +". "+ table.get(i).toString();
			if (i < size() - 1) result+="\n";
		}
		return result;
	}
	//protected Meld getMeld(int i) {return table.get(i);}
}
