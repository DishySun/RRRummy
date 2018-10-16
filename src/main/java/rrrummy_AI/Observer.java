package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.*;

public interface Observer {
	public void update(Table melds, ArrayList<Player> players);
}
