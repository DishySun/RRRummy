package rrrummy;
import java.util.ArrayList;
import java.util.HashSet;

import rrrummy.Tile.Color;

import java.util.HashMap;

public class Meld {
	private int tileNumber; // 有效牌数（除了Joker的Tile张数）注：只有当有效牌数>=2时，才能判断具体是run或者set
	private ArrayList<Tile> meld;
	private HashMap<String, Integer> tileMap;
	
	public Meld() {
		meld = new ArrayList<Tile>();
		tileNumber = 0;
		tileMap = null;
	}
	
	public int size() {return meld.size();}
	protected boolean isRun() {
		//in case of [jk R1] or [jk jk R2]
		for(int i = 0; i < size(); i ++) {
			if(meld.get(i).isJoker()) continue; // Iterate to first non-joker Tile
			if (i == 0) break; //meld starts with non-joker tile
			if(meld.get(i).getNumber()<=i) return false;
		}
		//in case of [R13 jk] or [R12 jk jk]
		int numJk = 0;
		for(int i = size() - 1; i >= 0; i--) {
			if(meld.get(i).isJoker()) {
				numJk++;
				continue; // Iterate to last non-joker Tile
			}
			if (i == size()-1) break; //meld ends with non-joker tile
			if(meld.get(i).getNumber()>=14-numJk) return false;
		}
		
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
			if(meld.get(index).getNumber() == firstValidNumber || meld.get(index).isJoker()) {
				firstValidNumber++;
				index++;
			}
			else return false;
		}
		return true;
	}
	
	protected boolean isSet() {
		if (tileNumber <= 1) return true;
		if(size() > 4) return false;
		int n = 0;//meld.get(0).getNumber();
		for (Tile t: meld) {
			if (t.isJoker()) continue;
			n = t.getNumber();
			break;
		}
		//if (n == 0) return true;
		for(Tile t : meld) {
			if(t.getNumber() != n && !t.isJoker()) return false;
		}
		HashSet<Tile.Color> c = new HashSet<Tile.Color>();
		for (Tile t : meld) {
			if (t.getColor() == Tile.Color.JOKER) continue;
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
	
	private int getFirstNumber() {
		int numJk = 0;
		for (int i = 0; i < size(); i++) {
			if(meld.get(i).isJoker()) {
				numJk++;
				continue;
			}
			return meld.get(i).getNumber() - numJk;
		}
		return 0;
	}
	private int getLastNumber() {
		int numJk = 0;
		for (int i = size()-1; i >= 0; i--) {
			if(meld.get(i).isJoker()) {
				numJk++;
				continue;
			}
			return meld.get(i).getNumber() + numJk;
		}
		return 0;
	}
	
	private void generateMap() {
		/*Map
		 *Key: String  --Tile in String
		 *Value: int   --where to add 3 for Head & Tail; 2 for Tail only; 1 for Head only*/
		if(tileNumber == 0) {
			tileMap = null;
			return;
		}
		tileMap = new HashMap<String, Integer>();
		if(size()>=13) return;
		if (isRun()) {
			String str = "";
			Tile.Color c = null;
			for (Tile t: meld) {
				if(t.isJoker())continue;
				c = t.getColor();
				break;
			}
			switch (c) {
			case BLUE: 	str = "B";
						break;
			case RED:	str = "R";
						break;
			case ORANGE:str = "O";
						break;
			case GREEN:	str = "G";
						break;
			default:	break;
			}
			int firstNum = this.getFirstNumber();
			int lastNum  = this.getLastNumber();
			int jkHT = 0;
			if (lastNum < 13) {
				tileMap.put(str+Integer.toString(lastNum+1), 2);
				jkHT+=2;
			}
			if (firstNum > 1) {
				tileMap.put(str+Integer.toString(firstNum-1), 1);
				jkHT+=1;
			}
			tileMap.put("JK", jkHT);
		}
		if(size()>=4)return;
		
		//有效颜色
		if(isSet()) {
			tileMap.put("JK", 3);
			HashSet<Color> colorSet = new HashSet<Tile.Color>();
			for (Tile t : meld) {
				if (colorSet.contains(t.getColor()))continue;
				colorSet.add(t.getColor());
			}
			int expectNumber = 0;
			for (Tile t : meld) {
				if(t.isJoker()) continue;
				expectNumber = t.getNumber();
				break;
			}
			for (Tile.Color c: Tile.Color.values()) {
				if (colorSet.contains(c)) continue;
				switch (c) {
				case BLUE: 	tileMap.put("B"+ Integer.toString(expectNumber), 3);
							break;
				case RED:	tileMap.put("R"+ Integer.toString(expectNumber), 3);
							break;
				case ORANGE:tileMap.put("O"+ Integer.toString(expectNumber), 3);
							break;
				case GREEN:	tileMap.put("G"+ Integer.toString(expectNumber), 3);
							break;
				default:	break;
				}
			}
		}
	}
	
	public boolean add(Tile t) throws AbleToAddBothSideException{
		if(size() == 0) {
			meld.add(t);
			if(!t.isJoker()) tileNumber++;
			this.generateMap();
			return true;
		}
		if(tileMap == null) throw new AbleToAddBothSideException(this.toString(), t.toString());
		if(!tileMap.containsKey(t.toString())) return false;
		switch (tileMap.get(t.toString())) {
		case 1:	meld.add(0, t);
				if(!t.isJoker()) tileNumber++;
				this.generateMap();
				return true;
		case 2: meld.add(t);
				if(!t.isJoker()) tileNumber++;
				this.generateMap();
				return true;
		case 3: throw new AbleToAddBothSideException(this.toString(), t.toString());
		default: return false;
		}
	}
	public boolean addHead(Tile t) {
		try {
			return add(t);
		}catch (AbleToAddBothSideException e) {
			meld.add(0,t);
			if(!t.isJoker()) tileNumber++;
			this.generateMap();
			return true;
		}
	}
	public boolean addTail(Tile t) {
		try {
			return add(t);
		}catch (AbleToAddBothSideException e) {
			meld.add(t);
			if(!t.isJoker()) tileNumber++;
			this.generateMap();
			return true;
		}
	}
	public Tile removeHead() {
		Tile t = meld.remove(0);
		if (!t.isJoker()) tileNumber--;
		this.generateMap();
		return t;
	}
	public Tile removeTail() {
		Tile t = meld.remove(size()-1);
		if (!t.isJoker()) tileNumber--;
		this.generateMap();
		return t;
	}
	public ArrayList<Tile> cut(int i){
		if (i >= size()) return null;
		ArrayList<Tile> returnArr = new ArrayList<Tile>();
		while (i >= 0) {
			returnArr.add(meld.remove(0));
			i--;
		}
		return returnArr;
	}
	private boolean haveJoker() {
		for (Tile t: meld) {
			if(t.isJoker()) return true;
		}
		return false;
	}
	public Tile replace(Tile t) {
		//TODO: require implementation
		//return the Tile that has been replaced, or return null if nothing to replace
		if (!haveJoker()) return null;
	}
	
	public String toString() {
		String str = "[";
		for (int i = 0; i < size(); i++) {
			str += meld.get(i).toString();
			if (i == size() - 1) break;
			str += ", ";
		}
		return str+"]";
	}
	public HashMap<String, Integer> getMap(){return tileMap;}
	//Methods for test case:
}