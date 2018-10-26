package rrrummy;

public class AbleToAddBothSideException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String meld;
	private String tile;
	public AbleToAddBothSideException(String m, String t){
		meld = m;
		tile = t;
	}
	public String toString() {
		return "Tile "+ tile+ " can be added either head or tail to meld "+ meld+".";
	}
}
