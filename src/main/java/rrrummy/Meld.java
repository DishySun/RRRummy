package rrrummy;
import java.util.ArrayList;
import java.util.HashSet;

public class Meld {
	private int tileNumber; // 有效牌数（除了Joker的Tile张数）注：只有当有效牌数>=2时，才能判断具体是run或者set
	private boolean isRun;
	private boolean isSet;
	private ArrayList<Tile> meld;
	
	public Meld() {
		meld = new ArrayList<Tile>();
		tileNumber = 0;
		isRun = true;
		isSet = true;
	}
	
	public int size() {return meld.size();}
	private boolean run() {
		if (tileNumber <= 1) return true;
		//Consider meld starts with joker(s)
		Tile.Color c = null;//meld.get(0).getColor();
		int firstValidNumber = 0;
		int index = -1;
		for (Tile t: meld) {
			index++;
			if(t.getColor() == Tile.Color.JOKER) continue;
			c = t.getColor();
			firstValidNumber = t.getNumber();
			break;
		}
		//if (c == null) return true;
		for(Tile t : meld) {
			if (t.getColor() != c && t.getColor()!= Tile.Color.JOKER) return false;
		}
		while(index < size()) {
			index++;
			if(meld.get(index).getNumber() == firstValidNumber || meld.get(index).getNumber() == 0) firstValidNumber++;
			else return false;
		}
		return true;
	}
	private void setRun() {isRun = run();}
	public boolean isRun() {return isRun;}
	
	private boolean set() {
		if (tileNumber <= 1) return true;
		if(size() > 4) return false;
		int n = 0;//meld.get(0).getNumber();
		for (Tile t: meld) {
			if (t.getNumber() == 0) continue;
			n = t.getNumber();
			break;
		}
		//if (n == 0) return true;
		for(Tile t : meld) {
			if(t.getNumber() != n && t.getNumber() != 0) return false;
		}
		HashSet<Tile.Color> c = new HashSet<Tile.Color>();
		for (Tile t : meld) {
			if (t.getColor() == Tile.Color.JOKER) continue;
			if(c.contains(t.getColor())) return false;
			else c.add(t.getColor());
		}
		return true;
	}
	private void setSet() {isSet = set();}
	public boolean isSet() {return isSet;}
	public boolean isValid() {
		if(size() < 3) return false;
		if (isRun()||isSet()) return true;
		return false;
	}
	
	public boolean add(Tile t) throws AbleToAddBothSideException{
		/*TODO: add Tile t to the collection
		 * return true if successfully added, otherwise return false
		 * use addHead() and addTail() to  determine where to add
		 * Caution: to add a joker in a run meld must use methods below
		 * 			if joker can be added both head or tail 
		 * 			return false*/
		return false;
	}
	public boolean addHead(Tile t) {
		//only use for jokers
		if(t.getNumber() != 0) return false;
		if (size() >= 13) return false;
		if(tileNumber == 0) {
			meld.add(0,t);
			if(t.getNumber() != 0) tileNumber++;
			return true;
		}
		if (isRun()) {
			//if(meld.get(0).getNumber() == 1) return false;
			//while meld starts with joker(s) or not
			int i = 0; // number of jokers
			while (meld.get(i).getNumber() == 0) {
				i++;
			}
			if (meld.get(i).getNumber() == i+1) return false; //ie: [jk 2] or [jk jk 3], where first number is 1, so cannot add to head
			if(t.getNumber() == meld.get(i).getNumber() - i - 1 || t.getNumber() == 0) {
				meld.add(0, t);
				if(t.getNumber() != 0) tileNumber++;
				return true;
			}
		}
		if (isSet()) {
			//TODO: implementation required
			
		}
		//throw notRunNorSetException
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
	
	//Methods for test case:
}
