package rrrummy;
import java.util.ArrayList;
import java.util.HashSet;

import rrrummy.Tile.Color;

import java.util.HashMap;

public class Meld {
	private int tileNumber; 
	private ArrayList<Tile> meld;
	private HashMap<String, Integer> tileMap;
	private int lastModifiedTile;
	private boolean lastMeld;
	
	public Meld() {
		meld = new ArrayList<Tile>();
		tileNumber = 0;
		tileMap = null;
		lastMeld = true;
	}
	public Meld(Tile t) {
		meld = new ArrayList<Tile>();
		tileNumber = 0;
		tileMap = null;
		this.addHead(t);
		lastMeld = false;
	}
	public Meld(ArrayList<Tile> m) {
		meld = new ArrayList<Tile>();
		tileNumber = 0;
		tileMap = null;
		while (m.size()>0) {
			Tile t = m.remove(0);
			try {
				if(!this.add(t)) {
					m.add(0, t);
					break;
				}
			}catch(AbleToAddBothSideException e) {
				if (!this.addTail(t)){
					m.add(0, t);
					break;
				}
			}
		}
		this.lastMeld = true;
	}
	
	public int size() {return meld.size();}
	public boolean isRun() {
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
	
	public boolean isSet() {
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
			this.lastModifiedTile = 0;
			return true;
		}
		if(tileMap == null) {
			int jkNum = 0;
			for (Tile tt : meld) {
				if (tt.isJoker()) jkNum++;
			}
			if(t.getNumber() <= jkNum) {
				meld.add(0,t);
				this.lastModifiedTile = 0;
				if(!t.isJoker()) tileNumber++;
				this.generateMap();
				return true;
			}else if (t.getNumber() >= 13 - jkNum) {
				meld.add(t);
				this.lastModifiedTile = size()-1;
				if(!t.isJoker()) tileNumber++;
				this.generateMap();
				return true;
			}else throw new AbleToAddBothSideException(this.toString(), t.toString());
		}
		if(!tileMap.containsKey(t.toString())) return false;
		switch (tileMap.get(t.toString())) {
		case 1:	meld.add(0, t);
				this.lastModifiedTile = 0;
				if(!t.isJoker()) tileNumber++;
				this.generateMap();
				return true;
		case 2: meld.add(t);
				this.lastModifiedTile = size() - 1;
				if(!t.isJoker()) tileNumber++;
				this.generateMap();
				return true;
		case 3: if (isSet()) {
					if(t.isJoker()&&isRun())throw new AbleToAddBothSideException(this.toString(), t.toString());
					meld.add(t);
					this.lastModifiedTile = size() - 1;
					if(!t.isJoker()) tileNumber++;
					this.generateMap();
					return true;
				}else throw new AbleToAddBothSideException(this.toString(), t.toString());
		default: return false;
		}
	}
	public boolean addHead(Tile t) {
		try {
			return add(t);
		}catch (AbleToAddBothSideException e) {
			meld.add(0,t);
			this.lastModifiedTile = 0;
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
			this.lastModifiedTile = size() - 1;
			if(!t.isJoker()) tileNumber++;
			this.generateMap();
			return true;
		}
	}
	public Tile removeHead() {
		if(size() == 0) return null;
		Tile t = meld.remove(0);
		if (!t.isJoker()) tileNumber--;
		this.generateMap();
		this.lastModifiedTile = -1;
		return t;
	}
	public Tile removeTail() {
		if(size() == 0) return null;
		Tile t = meld.remove(size()-1);
		if (!t.isJoker()) tileNumber--;
		this.generateMap();
		this.lastModifiedTile = -1;
		return t;
	}
	public Meld cut(int i){
		if (i >= size() || i < 0 || size() == 1) return null;
		if (i == size()-1) return new Meld(meld.remove(size() - 1));
		ArrayList<Tile> returnArr = new ArrayList<Tile>();
		while (i >= 0) {
			returnArr.add(meld.remove(0));
			i--;
		}
		return new Meld(returnArr);
	}
	private Tile.Color getRunColor(){
		for(Tile t : meld) {
			if (!t.isJoker()) return t.getColor();
		}
		return Tile.Color.JOKER;
	}
	private int getRunNumberAt(int index) {
		if (!meld.get(index).isJoker()) return meld.get(index).getNumber();
		try {
			return this.getRunNumberAt(index-1)+1;
		}catch (Exception e) {
			
		}
		try {
			return this.getRunNumberAt(index+1)-1;
		}catch (Exception e) {
			
		}
		return 0;
	}
	public Tile replace(Tile t, int index) {
		//return the Tile that has been replaced, or return null if nothing to replace
		if (!meld.get(index).isJoker()) return null;
		if (t.isJoker()) return null;
		if (tileNumber == 0) {
			meld.add(index, t);
			tileNumber++;
			return meld.remove(index+1);
		}
		if(isRun()) {
			if(t.getColor() == getRunColor() && t.getNumber() == this.getRunNumberAt(index)) {
				meld.add(index, t);
				tileNumber++;
				return meld.remove(index+1);
			}
		}
		if (isSet()) {
			HashSet<Tile.Color> c = new HashSet<Tile.Color>();
			for (Tile tt : meld) {
				if (!c.contains(tt.getColor())) c.add(tt.getColor());
			}
			if(t.getNumber() == this.getFirstNumber() && !c.contains(t.getColor())) {
				meld.add(index, t);
				tileNumber++;
				return meld.remove(index+1);
			}
		}
		return null;
	}
	
	public String toString() {
		String str = "";
		str += "[";
		for (int i = 0; i < size(); i++) {
			str += meld.get(i).toString();
			if (i == size() - 1) break;
			str += ", ";
		}
		str += "]";
		return str;
	}
	public String lastModifiedString() {
		String str = "";
		if (lastMeld) str += "*[";
		else str += "[";
		for (int i = 0; i < size(); i++) {
			if (i == lastModifiedTile && !lastMeld) str += ("*"+meld.get(i).toString()+"*");
			else str += meld.get(i).toString();
			if (i == size() - 1) break;
			str += ", ";
		}
		if (lastMeld) {
			str += "]*";
			lastMeld = false;
		}
		else  str += "]";
		return str;
	}
	public HashMap<String, Integer> getMap(){return tileMap;}
	public static Meld newMeld(ArrayList<Tile> arr) {
		Meld m = new Meld();
		for (int i = 0; i < arr.size(); i++) {
			if (!m.addTail(arr.get(i))) return null;
		}
		return m;
	}
	public int getMeldScore() {
		if (tileNumber == 0) return 0;
		int jkNm = 0;
		for (Tile t: meld) {
			if (t.isJoker()) jkNm++;
		}
		int score = 0;
		for (int i = 0; i < meld.size(); i++) {
			Tile t = meld.get(i); 
			if (!t.isJoker()) score += t.getNumber();
			else {
				if(isRun() && isSet()) {
					if (i == 0) score += this.getFirstNumber();
					else score += this.getLastNumber()+jkNm;
				}else if (isSet()) score += this.getFirstNumber();
				else if (isRun()) {
					if (i == 0) score += this.getFirstNumber()-jkNm;
					else score += this.getLastNumber()+jkNm;
				}
			}
			
		}
		return score;
	}
	
	public Tile getTile(int i) {return meld.get(i);}
}
