package command;

/*Valid command:
 * Play:
 * 		- Play int(hand index) 
 * 		- Play int(hand index) to int(meld index)
 * 		- Play int(hand index) to int(meld index) head/tail
 * 		- Play ArrayList<Integer>(hand indexs)
 * Move:
 * 		- Move int(fromMeldIndex) head/tail to int(toMeldIndex)
 * 		- Move int(fromMeldIndex) head/tail to int(toMeldIndex) head/tail
 * Cut:
 * 		- Cut int(tableIndex) at int(meldIndex)
 * Replace:
 * 		- Replace int(handIndex) to int(tableIndex) int(meldIndex)
 * End:
 * 		- End
 * */

public interface Command {
	public boolean excute();
	public String toString();
	//public void undo();
}
