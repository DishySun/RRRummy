package players;

public interface AIStrategy {
	public String name();
	public void setHand(Hand hand);
	public String generateCommand();
}
