package rrrummy_AI;

import java.util.ArrayList;

import rrrummy.*;

public interface AIstrategy {
	public void playInitial(Table table);
	public void playRest(Table table, ArrayList<Player> players);
}
