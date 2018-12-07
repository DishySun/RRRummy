package rrrummy;


/* Joker counts as 0 for number!!!!!!
 * Sorted by Blue < Red < Green < Orange < Joker order in increased number order 
 * ie b2 < b10 < r2 < r5 < g3 < g7 < o2 < jk
 * 
 * 
 * */


public class Tile {
	public enum Color{BLUE, RED, GREEN, ORANGE,JOKER}
	private Color color;
	private int number;
	private static Color BLUE = Color.BLUE;
	private static Color RED = Color.RED;
	private static Color GREEN = Color.GREEN;
	private static Color ORANGE = Color.ORANGE;
	private static Color JOKER = Color.JOKER;
	
	public Tile() {
		color = JOKER;
		number = 0;
	}
	
	//copy constructor
	public Tile(Tile t) {
		color = t.color;
		number = t.number;
	}
	
	public Tile(String str) throws InvalidTileException{
		String c = str.substring(0, 1);
		switch(c) {
		case "B": 	color = BLUE;
					break;
		case "R":	color = RED;
					break;
		case "G":	color = GREEN;
					break;
		case "O":	color = ORANGE;
					break;
		case "J":	color = JOKER;
					number = 0;
					return;
		default:	color = null;
		}
		String n = str.substring(1);
		try {
			number = Integer.parseInt(n);
		}catch(NumberFormatException e) {
			if (color == null) throw new InvalidTileException(c,n);
			else throw new InvalidTileException(n, InvalidTileException.Type.NUMBER);
		}
		if (color == null && !isNumberValid())throw new InvalidTileException(c,n);
		if (color == null) throw new InvalidTileException(c, InvalidTileException.Type.COLOR);
		if (!isNumberValid()) throw new InvalidTileException(n, InvalidTileException.Type.NUMBER);
	}
	
	public Tile(Color color, int number) throws InvalidTileException{
		if (!isNumberValid()) throw new InvalidTileException(Integer.toString(number), InvalidTileException.Type.NUMBER);
		this.color = color;
		this.number = number;
	}
	
	private boolean isNumberValid() {
		if (number >= 0 && number <= 13) return true;
		return false;
	}
	public Color getColor() {return color;}
	public int getNumber() {return number;}
	public boolean isGreaterThan(Tile t) {
		if (this.color.ordinal() > t.color.ordinal()) return true;
		if (this.color.ordinal() < t.color.ordinal()) return false;
		if (this.number > t.number) return true;
		else return false;
	}
	public boolean isJoker() {
		if(this.color == JOKER)return true;
		else return false;
	}
	public boolean compareTo(String s) {
		return s.equals(this.toString());
	}
	public boolean isEqualThan(Tile t) {
		if (this.number > t.number) return true;
		else return false;
	}
	public String toString() {
		String str = "";
		switch(color) {
		case BLUE: 	str += "B";
					break;
		case RED: 	str += "R";
					break;
		case GREEN:	str += "G";
					break;
		case ORANGE: str+= "O";
					break;
		default: return "JK";
		}
		return str + number;
	}
}
