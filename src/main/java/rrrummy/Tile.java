package rrrummy;

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
	
	public Tile(Color color, int number) {
		this.color = color;
		this.number = number;
	}
	
	private boolean isNumberValid() {
		if (number > 0 && number <= 13) return true;
		return false;
	}
	public Color getColor() {return color;}
	public int getNumber() {return number;}
}
