package command;

/*Valid command:
 * Play:
 * 		- Play int(hand index) 
 * 		- Play int(hand index) to int(meld index)
 * 		- Play int(hand index) to int(meld index) head/tail
 * 		- Play ArrayList<Integer>(hand indexs)
 * Move:
 * 		- Move int(meld) head/tail to int(meld)
 * 		- Move int head/tail to int head/tail
 * Cut:
 * 		- Cut int at int
 * Replace:
 * 		- Replace int to int int
 * End:
 * 		- End
 * */

public interface Command {
	public void excute();
	public String toString();
	//public void undo();
}
