package players;

import command.Command;

public interface AIStrategy {
	public String getDifficulty();
	public void setHand(Hand hand);
	public String generateCommand();
}
