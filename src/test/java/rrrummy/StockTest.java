package rrrummy;
import junit.framework.TestCase;

public class StockTest extends TestCase{
	private Stock testStock1;
	private Stock testStock2;
	
	public void testConstructor() {
		testStock1 = new Stock();
		//total tiles = 52 * 2 + 2
		assertEquals(106, testStock1.size());
	}
	
	public void testShuffle() {
		testStock1 = new Stock();
		testStock2 = new Stock();
		Tile tile1;
		Tile tile2;
		int i = 0;
		while  (testStock1.size() > 0 ) {
			tile1 = testStock1.draw();
			tile2 = testStock2.draw();
			if (tile1.getColor() == tile2.getColor() && tile1.getNumber() == tile2.getNumber()) i++;
		}
		assertTrue(i < 20);
	}
}
