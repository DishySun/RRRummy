package players;

import java.util.ArrayList;

import junit.framework.TestCase;
import rrrummy.InvalidTileException;
import rrrummy.Tile;

public class PlayerTest extends TestCase {
	private Player testPlayer = new Player("Hunter");

	public void test_draw() {
		try {
			testPlayer.initHand(new ArrayList<Tile>());
			testPlayer.draw(new Tile("R2"));
			assertEquals(1, testPlayer.hand.size());
			assertEquals("R2", testPlayer.getTile(0).toString());
			testPlayer.draw(new Tile("O9"));
			assertEquals(2, testPlayer.hand.size());
			assertEquals("O9", testPlayer.getTile(1).toString());
			// auto sort, O1 should in position 1
			testPlayer.draw(new Tile("O1"));
			assertEquals(3, testPlayer.hand.size());
			assertEquals("O1", testPlayer.getTile(1).toString());
		} catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}
	}

	public void test_play() {
		try {
			testPlayer.initHand(new ArrayList<Tile>());
			testPlayer.draw(new Tile("B2"));
			assertEquals(1, testPlayer.hand.size());
			assertEquals("B2", testPlayer.getTile(0).toString());
			testPlayer.draw(new Tile("G5"));
			assertEquals(2, testPlayer.hand.size());
			assertEquals("G5", testPlayer.getTile(1).toString());
			testPlayer.draw(new Tile("G7"));
			assertEquals(3, testPlayer.hand.size());
			assertEquals("G7", testPlayer.getTile(2).toString());
			testPlayer.draw(new Tile("O3"));
			assertEquals(4, testPlayer.hand.size());
			assertEquals("O3", testPlayer.getTile(3).toString());
			testPlayer.play(2);
			assertEquals(3, testPlayer.hand.size());
			assertEquals("O3", testPlayer.getTile(2).toString());
			testPlayer.play(0);
			assertEquals(2, testPlayer.hand.size());
			assertEquals("G5", testPlayer.getTile(0).toString());
		} catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}
	}

	public void test_getName() {
		testPlayer = new Player("Tony");
		assertEquals("Tony", testPlayer.getName());
	}
}
