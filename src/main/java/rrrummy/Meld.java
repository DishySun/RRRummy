package rrrummy;
import java.util.ArrayList;

public class Meld {
	private ArrayList<Tile> meld;
	
	public Meld() {
		meld = new ArrayList<Tile>();
	}
	
	public int size() {return meld.size();}
	private boolean isRun() {
		//TODO: require implementation
	}
	private boolean isSet() {
		//TODO: require implementation
	}
	public boolean isValid() {
		//TODO: require implementation
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
		//TODO: require implementation
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
