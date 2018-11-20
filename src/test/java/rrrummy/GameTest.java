package rrrummy;
import java.util.ArrayList;

import game.Game;
import game.View;

import  junit.framework.TestCase;
import players.Player;

public class GameTest extends TestCase{
	private Player player;
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
	private Tile g7, o7;
	
	private Tile jk;
	
	protected void setUp() {
		
		ArrayList<Player> players = new ArrayList<Player>();
		player = new Player ("Test Player");
		players.add(player);
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
			g7 = new Tile("G7");
			o7 = new Tile("O7");
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
		player.initHand(testHand);
		ArrayList<Tile> testStock = new ArrayList<Tile>();
		testStock.add(g7);
		testStock.add(o7);
		testStock.add(r1);
		testStock.add(r2);
		testStock.add(r3);
		game = new Game(players, testStock, new View());
	}
	
	public void test_playerDraw() {
		assertEquals(8, player.handSize());
		assertEquals(5, game.stockSize());
		assertTrue(game.playerDraw());
		assertTrue(game.playerDraw());
		assertTrue(game.playerDraw());
		assertTrue(game.playerDraw());
		assertTrue(game.playerDraw());
		//run out of stock
		assertFalse(game.playerDraw());
	}
	
	//order B5, R4, R5, R6, R7, G5, O5, JK
	public void test_playerPlays1() {
		assertTrue(game.playerPlays(0));// b5
		assertEquals(7, player.handSize());
		assertFalse(player.handContains(b5));
		assertTrue(game.playerPlays(6));// jk
		assertEquals(6, player.handSize());
		assertFalse(player.handContains(jk));
		assertTrue(game.playerPlays(1));// r5
		assertEquals(5, player.handSize());
		assertFalse(player.handContains(r5));
	}
	
	//hand order B5, R4, R5, R6, R7, G5, O5, JK
	public void test_playerPlays2() {
		assertEquals(0, game.tableSize());
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(1); // r4
		arr.add(2); // r5
		arr.add(3); // r6
		assertTrue(game.playerPlays(arr));
		assertEquals(1, game.tableSize());
		//order B5, R7, G5, O5, JK
		assertEquals(5, player.handSize());
		arr.clear();
		arr.add(0);
		arr.add(1);
		arr.add(2);
		assertFalse(game.playerPlays(arr));
		assertEquals(1, game.tableSize());
		assertEquals(5, player.handSize());
		arr.clear();
		arr.add(0);
		arr.add(2);
		arr.add(3);
		arr.add(4);
		assertTrue(game.playerPlays(arr));
		assertEquals(2, game.tableSize());
		assertEquals(1, player.handSize());
	}
	
	//hand order B5, R4, R5, R6, R7, G5, O5, JK
	public void test_playerPlays3() {
		//B5, R4, R5, R6, R7, G5, O5, JK
		assertTrue(game.playerPlays(0));
		//R4, R5, R6, R7, G5, O5, JK
		assertTrue(game.playerPlays(0));
		//R5, R6, R7, G5, O5, JK
		//meld 0 : B5
		//meld 1 : R4
		try {
			//play r5 to m1 with add() method
			assertTrue(game.playerPlays(0, 1));
		}catch (AbleToAddBothSideException e) {
			fail();
		}
		//R6, R7, G5, O5, JK
		//meld 0 : B5
		//meld 1 : R4 R5
		assertEquals(5, player.handSize());
		try {
			//play jk to m1 with add() method : should throw both side exception
			game.playerPlays(4, 1);
			fail();
		}catch (AbleToAddBothSideException e) {	
			assertTrue(game.playerPlays(4, 1 , false));
		}
		//R6, R7, G5, O5
		//meld 0 : B5
		//meld 1 : R4 R5 JK
		assertEquals(4, player.handSize());
		assertEquals(2,game.tableSize());
		assertTrue(game.playerPlays(1, 1 , true)); // r7 can be added with either 3 of add functions
		//R6, G5, O5
		//meld 0 : B5
		//meld 1 : R4 R5 JK R7
		try {
			//play a tile to a set, the tile should be added to the tail by default
			//unless user point where to add
			assertTrue(game.playerPlays(2, 0));// O5
		}catch (AbleToAddBothSideException e) {
			e.getStackTrace();
			fail();
		}
	}
	
	public void test_move() {
		//hand order B5, R4, R5, R6, R7, G5, O5, JK
		assertTrue(game.playerPlays(0));
		//R4, R5, R6, R7, G5, O5, JK
		assertTrue(game.playerPlays(1));
		//R4, R6, R7, G5, O5, JK
		//m0: B5
		//m1: R5
		assertTrue(game.playerPlays(1, 1, true));
		//R4, R7, G5, O5, JK
		//m0: B5
		//m1: R5 R6
		assertTrue(game.playerPlays(1, 1, true));
		//R4, G5, O5, JK
		//m0: B5
		//m1: R5 R6 R7
		try {
			//try to move R7 in m2 to m1
			//result false and no change
			assertFalse(game.move(1, false, 0));
		}catch (AbleToAddBothSideException e) {
			e.getStackTrace();
			fail();
		}
		assertEquals(4, player.handSize());
		assertEquals(2,game.tableSize());
		try {
			//try to move r5 in m2 to m1
			assertTrue(game.move(1, true, 0));
		}catch (AbleToAddBothSideException e) {
			e.getStackTrace();
			fail();
		}
		//R4, G5, O5, JK
		//m0: B5 R5
		//m1: R6 R7
		assertTrue(game.playerPlays(3, 1, false));
		//R4, G5, O5
		//m0: B5 R5
		//m1: R6 R7 JK
		try {
			//JK should be able to add to the tail of a set
			assertTrue(game.move(1, false, 0));
		}catch (AbleToAddBothSideException e) {
			fail();
		}
		//R4, G5, O5
		//m0: B5 R5 JK
		//m1: R6 R7
		try {
			assertTrue(game.move(0, false, 1));
			fail();
		}catch (AbleToAddBothSideException e) {
			assertTrue(game.move(0, false, 1, true));
		}
		//R4, G5, O5
		//m0: B5 R5
		//m1: JK R6 R7
		assertTrue(game.move(1, true, 1, false));
	}
	
	public void test_cut() {
		//hand order B5, R4, R5, R6, R7, G5, O5, JK
		assertTrue(game.playerPlays(0));
		//R4, R5, R6, R7, G5, O5, JK
		assertTrue(game.playerPlays(1));
		//R4, R6, R7, G5, O5, JK
		//m0: B5
		//m1: R5
		assertTrue(game.playerPlays(1, 1, true));
		//R4, R7, G5, O5, JK
		//m0: B5
		//m1: R5 R6
		assertTrue(game.playerPlays(1, 1, true));
		//R4, G5, O5, JK
		//m0: B5
		//m1: R5 R6 R7
		assertTrue(game.cut(1, 0));
		//0. [B5]
		//1. [R6, R7]
		//2. [R5]
		assertEquals(3, game.tableSize());
		assertFalse(game.cut(1, 2));
		assertEquals(3, game.tableSize());
		assertTrue(game.cut(1, 1));
		assertEquals(4, game.tableSize());
		assertFalse(game.cut(1, 0)); // can not cut when meld.size() == 1
		assertEquals(4, game.tableSize());
	}
	
	public void test_replace() {
		//B5, R4, R5, R6, R7, G5, O5, JK
		assertTrue(game.playerPlays(0));
		//R4, R5, R6, R7, G5, O5, JK
		assertTrue(game.playerPlays(0));
		//R5, R6, R7, G5, O5, JK
		//meld 0 : B5
		//meld 1 : R4
		try {
			//play r5 to m1 with add() method
			assertTrue(game.playerPlays(0, 1));
		}catch (AbleToAddBothSideException e) {
			fail();
		}
		//R6, R7, G5, O5, JK
		//meld 0 : B5
		//meld 1 : R4 R5
		assertEquals(5, player.handSize());
		assertTrue(game.playerPlays(4, 1 , false));
		assertTrue(game.replace(0, 1, 2));
	}
	
	public void test_playAllAndWin() {
		//hand order B5, R4, R5, R6, R7, G5, O5, JK
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(1);
		arr.add(2);
		arr.add(3);
		arr.add(4);
		arr.add(7);
		assertTrue(game.playerPlays(arr));
		player.printHand();
		arr.remove(arr.size() -1);
		arr.remove(arr.size() -1);
		arr.set(arr.size()-1, 0);
		assertTrue(game.playerPlays(arr));
		game.endTurn();
		assertEquals(player, game.getWinner());
	}
	
	public void test_playSeveralRunAndSet() {
		game.playerDraw();
		game.playerDraw();
		game.playerDraw();
		game.playerDraw();
		game.playerDraw();
		System.out.println("here!");
		player.printHand();
		//B5, R1, R2, R3, R4, R5, R6, R7, G5, G7, O5, O7, JK
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(1);
		arr.add(2);
		arr.add(3);
		assertTrue(game.playerPlays(arr));
		//B5, R4, R5, R6, R7, G5, G7, O5, O7, JK
		//playing 2nd run in the same turn
		arr.clear();
		arr.add(1);
		arr.add(9);
		arr.add(3);
		assertTrue(game.playerPlays(arr));
		//B5, R5, R7, G5, G7, O5, O7
		arr.clear();
		arr.add(0);
		arr.add(1);
		arr.add(5);
		assertTrue(game.playerPlays(arr));
		// R7, G5, G7, O7
		//playing 2nd set in the same turn
		arr.clear();
		arr.add(0);
		arr.add(2);
		arr.add(3);
		assertTrue(game.playerPlays(arr));
	}
}
