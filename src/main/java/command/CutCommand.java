package command; 
 
import rrrummy.Game; 


public class CutCommand implements Command { 
	/* Correct Form: 
	 * 		keyword			meldIndex		tileindex 
	 * 1. 	Cut 			meldIndex		tileindex				        cut(int meldIndex, int tileindex) { 
	*/ 
	 

	private Game game; 
	private int meldIndex;
	private int cutPosition;
 
	public CutCommand(int m, int c, Game g) { 
		this.game = g; 
		meldIndex = m;
		cutPosition = c;;
	} 
 
	@Override 
	public boolean excute() { 
		// TODO Auto-generated method stub 
		return game.cut(meldIndex, cutPosition);
	}
 
} 