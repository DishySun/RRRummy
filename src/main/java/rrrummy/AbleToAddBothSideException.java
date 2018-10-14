package rrrummy;

public class AbleToAddBothSideException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errMsg;
	public AbleToAddBothSideException() {
		errMsg = "Joker can be add to both side of the meld";
	}
	public String getErrMsg() {return errMsg;}
}
