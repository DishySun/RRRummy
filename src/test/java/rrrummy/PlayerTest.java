package rrrummy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private Player testPlayer;	
	private Tile tile1;
	private Tile tile2;
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
		assertEquals(1, testPlayer.handSize());
		testPlayer.play(0);
		assertEquals(0, testPlayer.handSize());
	}
	
	public void test_play2() {
		testPlayer.draw(tile1);
		assertEquals(1, testPlayer.handSize());
		testPlayer.draw(tile2);
		assertEquals(2, testPlayer.handSize());
		testPlayer.play(1);
		assertEquals(1, testPlayer.handSize());
	}
	
	

	@Test
	public void test_draw() {
		testPlayer.draw(tile1);
		assertEquals(1, testPlayer.handSize());
		assertEquals(tile1, testPlayer.getHand(0));
		testPlayer.draw(tile2);
		assertEquals(2, testPlayer.handSize());
		assertEquals(tile2, testPlayer.getHand(1));
	}
	
	@Test
	public void test_getName() {
		testPlayer = new Player("Tony");
		assertEquals("Tony", testPlayer.getName());
	}
}
