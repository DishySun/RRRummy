package rrrummy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private Player testPlayer;	
	private Tile tile1;
	@Before
	public void setUp() throws Exception {
		testPlayer = new Player("Hunter");
		try {
			tile1 = new Tile("R3");
		}catch(InvalidTileException e) {
			fail();
		}
	}

	@Test
	public void test_play() {
		testPlayer.draw(tile1);
		testPlayer.play(0);
		assertEquals(0, testPlayer.handSize());
	}

	@Test
	public void test_draw() {
		testPlayer.draw(tile1);
		assertEquals(1, testPlayer.handSize());
		assertEquals(tile1, testPlayer.getHand(0));
	}
}
