package rrrummy;
import java.util.ArrayList;

import game.View;
import  junit.framework.TestCase;
import players.Player;

public class GameTest extends TestCase{
	private final int PLAYERINDEX = 0;
	private ArrayList<Player> players;//= new ArrayList<Player>();
	private Game game;
	private Tile b5;
	
	private Tile r1;
	private Tile r2;
	private Tile r3;
	private Tile r4;
	private Tile r5;
	private Tile r6;
	private Tile r7;
	
	private Tile g5;
	private Tile o5;
	
	private Tile jk;
	
	protected void setUp() {
		players = new ArrayList<Player>();
		Player p = new Player ("Test Player");
		players.add(p);
		jk = new Tile();
		try {
			b5 = new Tile("B5");
			r1 = new Tile("R1");
			r2 = new Tile("R2");
			r3 = new Tile("R3");
			r4 = new Tile("R4");
			r5 = new Tile("R5");
			r6 = new Tile("R6");
			r7 = new Tile("R7");
			g5 = new Tile("G5");
			o5 = new Tile("O5");
		}catch (InvalidTileException e) {
			fail("Tile init error");
		}
		ArrayList<Tile> testHand = new ArrayList<Tile>();
		testHand.add(r4);
		testHand.add(r5);
		testHand.add(r6);
		testHand.add(r7);
		testHand.add(g5);
		testHand.add(o5);
		testHand.add(b5);
		testHand.add(jk);
		p.initHand(testHand);
		ArrayList<Tile> testStock = new ArrayList<Tile>();
		testStock.add(r1);
		testStock.add(r2);
		testStock.add(r3);
		game = new Game(players, testStock, null);
	}
	
	public void test_playerDraw() {
		assertEquals(8, players.get(PLAYERINDEX).handSize());
		assertEquals(3, game.stockSize());
		assertTrue(game.playerDraw(PLAYERINDEX));
		assertTrue(game.playerDraw(PLAYERINDEX));
		assertTrue(game.playerDraw(PLAYERINDEX));
		//run out of stock
		assertFalse(game.playerDraw(PLAYERINDEX));
	}
	
	//order B5, R4, R5, R6, R7, G5, O5, JK
	public void test_playerPlays1() {
		assertTrue(game.playerPlays(PLAYERINDEX, 0));// b5
		assertEquals(7, players.get(PLAYERINDEX).handSize());
		assertFalse(players.get(PLAYERINDEX).handContains(b5));
		assertTrue(game.playerPlays(PLAYERINDEX, 6));// jk
		assertEquals(6, players.get(PLAYERINDEX).handSize());
		assertFalse(players.get(PLAYERINDEX).handContains(jk));
		assertTrue(game.playerPlays(PLAYERINDEX, 1));// r5
		assertEquals(5, players.get(PLAYERINDEX).handSize());
		assertFalse(players.get(PLAYERINDEX).handContains(r5));
	}
	
	//hand order B5, R4, R5, R6, R7, G5, O5, JK
	public void test_playerPlays2() {
		assertEquals(0, game.tableSize());
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(1); // r4
		arr.add(2); // r5
		arr.add(3); // r6
		assertTrue(game.playerPlays(PLAYERINDEX, arr));
		assertEquals(1, game.tableSize());
		//order B5, R7, G5, O5, JK
		assertEquals(5, players.get(PLAYERINDEX).handSize());
		arr.clear();
		arr.add(0);
		arr.add(1);
		arr.add(2);
		assertFalse(game.playerPlays(PLAYERINDEX, arr));
		assertEquals(1, game.tableSize());
		assertEquals(5, players.get(PLAYERINDEX).handSize());
		arr.clear();
		arr.add(0);
		arr.add(2);
		arr.add(3);
		arr.add(4);
		assertTrue(game.playerPlays(PLAYERINDEX, arr));
		assertEquals(2, game.tableSize());
		assertEquals(1, players.get(PLAYERINDEX).handSize());
	}
}
