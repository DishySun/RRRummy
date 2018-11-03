package observer;

import java.util.ArrayList;
import java.util.HashMap;

import rrrummy.Meld;


public interface Observer {
	public void update(ArrayList<Meld> table);
	public void update(int playerId, int handSize);//<playerId, handSize>
}
