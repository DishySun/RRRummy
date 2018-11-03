package players;

import command.Command;
import observer.Observer;

public interface AIStrategy extends Observer{
	public String getDifficulty();
	public void setHand(Hand hand);
	public void setMyId(int id);
	public String generateCommand();
}
