package rrrummy;
import java.util.ArrayList;
import java.util.HashSet;

public class Meld {
	private ArrayList<Tile> meld;
	
	public Meld() {
		meld = new ArrayList<Tile>();
	}
	
	public int size() {return meld.size();}
	private boolean isRun() {
		if (size() <= 1) return true;
		//Consider meld starts with joker(s)
		Tile.Color c = null;//meld.get(0).getColor();
		for (Tile t: meld) {
			if(t.getColor() == Tile.Color.JOKER) continue;
			c = t.getColor();
			break;
		}
		if (c == null) return true;
		for(Tile t : meld) {
			if (t.getColor() != c && t.getColor()!= Tile.Color.JOKER) return false;
		}
		int i = meld.get(0).getNumber();
		for (Tile tile : meld) {
			if (!(tile.getNumber() == i++ || tile.getNumber() == 0)) return false;
		}
		return true;
	}
	private boolean isSet() {
		if (size() <= 1) return true;
		if(size() > 4) return false;
		int n = 0;//meld.get(0).getNumber();
		for (Tile t: meld) {
			if (t.getNumber() == 0) continue;
			n = t.getNumber();
			break;
		}
		if (n == 0) return true;
		for(Tile t : meld) {
			if(t.getNumber() != n)return false;
		}
		HashSet<Tile.Color> c = new HashSet<Tile.Color>();
		for (Tile t : meld) {
			if(c.contains(t.getColor())) return false;
			else c.add(t.getColor());
		}
		return true;
		
	}
	public boolean isValid() {
		if(size() < 3) return false;
		if (isRun()||isSet()) return true;
		return false;
	}
	public boolean add(Tile t) {
		/*TODO: add Tile t to the collection
		 * return true if successfully added, otherwise return false
		 * use addHead() and addTail() to  determine where to add
		 * Caution: to add a joker in a run meld must use methods below
		 * 			if joker can be added both head or tail 
		 * 			return false*/
		
	}
	public boolean addHead(Tile t) {
		if(size() == 0) {
			meld.add(t);
			return true;
		}
		if (isRun()) {
			//if(meld.get(0).getNumber() == 1) return false;
			int i = 0;
			//while meld starts with joker(s)
			while (meld.get(i).getNumber() == 0) {
				i++;
			}
			if (meld.get(i).getNumber() == i+1) return false;
			meld.add(0, t);
			return true;
		}
		if (isSet()) {
			//TODO: implementation required
		}
		//throw InvalidMeldException
		return false;
	}
	public boolean addTail(Tile t) {
		//TODO: 
	}
	public Tile removeHead() {
		//TODO: require implementation
	}
	public Tile removeTail() {
		//TODO: require implementation
	}
	public ArrayList<Tile> cut(int i){
		//TODO: require implementation
	}
	public Tile replace(Tile t) {
		//TODO: require implementation
		//return the Tile that has been replaced, or return null if nothing to replace
	}
}
