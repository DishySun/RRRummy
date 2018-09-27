package rrrummy;

public class InvalidTileException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum Type{COLOR,NUMBER, BOTH}
	private Type type;
	private String color;
	private String number;
	
	
	public InvalidTileException(String string, Type t) {
		this.type = t;
		if(t == Type.COLOR) color = string;
		else number = string;
		
	}
	public InvalidTileException(String color, String number) {
		this.color = color;
		this.number = number;
		this.type = Type.BOTH;
	}
	public String getNumber() {return number;}
	public String getColor(){return color;}
	public Type getType() {return type;}
	public String getErrMsg() {
		if (type == Type.BOTH) return (color + " is not a valid color and " + number + " is not a valid number.");
		if (type == Type.COLOR) return (color + " is not a valid color.");
		if (type == Type.NUMBER) return (number + " is not a valid numebr.");
		return null;
	}
}
