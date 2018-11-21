package players;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import rrrummy.AbleToAddBothSideException;
import rrrummy.InvalidTileException;
import rrrummy.Meld;
import rrrummy.Table;
import rrrummy.Tile;

public class StrategyFourTest {
	private Tile atile1,atile2,atile3,atile4,atile5,atile6,atile7,atile8,atile9,atile10,atile11,atile12,atile13,aJoker,bJoker;
	private ArrayList<Tile>  hand;
	private AI testAI;
	private Player A;
	private Player B;
	private Table table;
	private AIStrategy aiSTY;
	@Before
	public void setUp() throws Exception {
		testAI = new AI(new StrategyFour());
		hand = new ArrayList<Tile>();
		table = new Table();
		table.register(testAI.getStrategy());
	}

	@Test
	public void test_generateCommand1() {
		//play init
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("O11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 5);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(bJoker);
		hand.add(aJoker);
		
		Hand hand2 = new Hand(hand);
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		hand2.sort();
		//System.out.println(hand2);
		//testAI.printHand();
		String command = testAI.getStrategy().generateCommand();
		//first play run
		assertEquals("Play 2 3 5 6", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getStrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//play second run
		assertEquals("Play 2 3 8", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play 5 1 6", command);
		hand2.remove(hand.indexOf(atile10));
		hand2.remove(hand.indexOf(atile11));
		hand2.remove(hand.indexOf(bJoker));
//G5, B6, O7, G10
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//after initial,2 player, no one has less 3 tile than ai, nothing to play, end
		
		try {
			atile1 = new Tile("B5");
			atile2 = new Tile("R5");
			atile3 = new Tile("G2");
			atile4 = new Tile("G3");
			atile5 = new Tile("G6");
			atile6 = new Tile("O1");
			atile7 = new Tile("O2");
			atile8 = new Tile("O5");
			atile9 = new Tile("O9");
			atile10 = new Tile("O10");
			
			atile11 = new Tile("R2");
			atile12 = new Tile("R3");
			atile13 = new Tile("R3");
			aJoker = new Tile("R4");
		}catch(InvalidTileException e) {
			fail();
		}
		table = new ArrayList<Meld>();
		Meld meld = new Meld();
		meld.addTail(atile11);
		meld.addTail(atile12);
		meld.addTail(atile13);
		meld.addTail(aJoker);
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 11);
		testAI.getStrategy().update(2, 1);
		hand = new ArrayList<Tile>();
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand2 = new Hand(hand);
		testAI.initHand(hand);
		testAI.getStrategy().setHand(hand2);
		testAI.printHand();
		command = testAI.getStrategy().generateCommand();
		//
		assertEquals("Play 7 1 0", command);
	}
	
	@Test
	public void test_generateCommand() {
		//play init
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("O11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 3);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(bJoker);
		hand.add(aJoker);
		
		Hand hand2 = new Hand(hand);
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		hand2.sort();
		String command = testAI.getStrategy().generateCommand();
		//first play run
		assertEquals("Play 2 3 5 6", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getStrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//play second run
		assertEquals("Play 2 3 8", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play 5 1 6", command);
		hand2.remove(hand.indexOf(atile10));
		hand2.remove(hand.indexOf(atile11));
		hand2.remove(hand.indexOf(bJoker));
//G5, B6, O7, G10
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//after initial,2 player, no one has less 3 tile than ai, nothing to play, end
		assertEquals("END", command);
		hand2.add(atile1);
		hand2.add(atile2);
		hand2.add(atile3);
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//2 player, one has less 3 tile than ai, play, or end
		assertEquals("Play 1 2 3", command);
		hand2.remove(hand.indexOf(atile1));
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.add(atile1);
//B6, R1, G5, G10, O7
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//2 player, one has less 3 tile than ai, tried play, but END
		assertEquals("END", command);
		ArrayList<Tile> melds = new ArrayList<Tile>();
		melds.add(atile2);
		melds.add(atile3);
		melds.add(atile4);
		Meld meld = new Meld(melds);
		table.add(meld);
//[[R2, R3, R4]]
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//2 player, one has less 3 tile than ai, play 2 table
		assertEquals("Play 1 to 0", command);
		hand2.remove(hand.indexOf(atile1));
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//no one has less 3 tile than ai, nothing to play, end
		assertEquals("END", command);
	}
	
	@Test
	public void test_generateCommand2() {
		//END
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R1");
			atile5 = new Tile("R3");
			atile6 = new Tile("B2");
			atile7 = new Tile("O1");
			atile8 = new Tile("R4");
			atile9 = new Tile("G1");
			atile10 = new Tile("B1");
			atile11 = new Tile("O2");
			atile12 = new Tile("G2");
			atile13 = new Tile("B2");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 5);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(bJoker);
		hand.add(aJoker);
		
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
		testAI.getStrategy().setHand(hand2);
		String command = testAI.getStrategy().generateCommand();
		//total no. < 30, END
		assertEquals("END", command);
	}
	
	@Test
	public void test_generateCommand3() {
		//play init,play rest
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B5");
			atile7 = new Tile("O5");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("O11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(bJoker);
		hand.add(aJoker);
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 5);
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		hand2.sort();
		String command = testAI.getStrategy().generateCommand();
		//first play run
		assertEquals("Play 2 3 5 6", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getStrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//play second run
		assertEquals("Play 2 3 8", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play 5 1 6", command);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile7));
		hand2.remove(hand.indexOf(atile12));
//JK, G10, O11, B11
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		// initial no. > 30, no way to play all, END
		assertEquals("END", command);
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		// initial no. > 30, no way to play all, END
		assertEquals("END", command);
	}

	@Test
	public void test_generateCommand4() {
		//play init,play rest
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B5");
			atile7 = new Tile("O5");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("O11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 5);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(bJoker);
		hand.add(aJoker);
		
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		hand2.sort();
		String command = testAI.getStrategy().generateCommand();
		//first play run
		assertEquals("Play 2 3 5 6", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getStrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		command = testAI.getStrategy().generateCommand();
		//play second run
		assertEquals("Play 2 3 8", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		command = testAI.getStrategy().generateCommand();
		//no run to play , play first group
		hand2.sort();
		assertEquals("Play 5 1 6", command);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile7));
		hand2.remove(hand.indexOf(atile12));
//JK, G10, O11, B11
		// initial no. > 30, check playing
		command = testAI.getStrategy().generateCommand();
		assertEquals("END", command);
		command = testAI.getStrategy().generateCommand();
		assertEquals("END", command);
	}
	
	@Test
	public void test_generateCommand5() {
		//play init,play rest,play all
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B5");
			atile7 = new Tile("O5");
			atile8 = new Tile("R4");
			atile9 = new Tile("G11");
			atile10 = new Tile("B11");
			atile11 = new Tile("O11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 14);
		testAI.getStrategy().update(2, 1);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(bJoker);
		hand.add(aJoker);
		
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		String command = testAI.getStrategy().generateCommand();
		//first play run
		assertEquals("Play 2 3 5 6", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getStrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		command = testAI.getStrategy().generateCommand();
		//play second run
		assertEquals("Play 2 3 8", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		command = testAI.getStrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play 5 3 1 6", command);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile7));
		hand2.remove(hand.indexOf(atile12));
//JK, G10, O11, B11
		// initial no. > 30, check playing
		hand2.add(atile4);
		command = testAI.getStrategy().generateCommand();
		//can not play all tile
		assertEquals("Play 3 2 0 4", command);
		ArrayList<Tile> melds = new ArrayList<Tile>();
		melds.add(atile1);
		melds.add(atile2);
		melds.add(atile3);
		Meld meld = new Meld(melds);
		table.add(meld);
//[[R1, R2, R3]]
//B11, R4, G11, O11, JK
		command = testAI.getStrategy().generateCommand();
		// can play all tile
		assertEquals("Play 3 2 0 4", command);
		hand2.remove(hand.indexOf(atile9));
		hand2.remove(hand.indexOf(atile10));
		hand2.remove(hand.indexOf(atile11));
		hand2.remove(hand.indexOf(bJoker));
		command = testAI.getStrategy().generateCommand();
		// can play all tile
		assertEquals("Play 0 to 0", command);
	}
	
	@Test
	public void test_generateCommand6() {
		//play init,play rest,play all
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B5");
			atile7 = new Tile("O5");
			atile8 = new Tile("R4");
			atile9 = new Tile("G11");
			atile10 = new Tile("B11");
			atile11 = new Tile("O11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 14);
		testAI.getStrategy().update(2, 14);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(bJoker);
		hand.add(aJoker);
		
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		String command = testAI.getStrategy().generateCommand();
		//first play run
		assertEquals("Play 2 3 5 6", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getStrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		command = testAI.getStrategy().generateCommand();
		//play second run
		assertEquals("Play 2 3 8", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		command = testAI.getStrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play 5 3 1 6", command);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile7));
		hand2.remove(hand.indexOf(atile12));
//JK, G10, O11, B11
		// initial no. > 30, check playing
		hand2.add(atile4);
		command = testAI.getStrategy().generateCommand();
		//can not play all tile
		assertEquals("END", command);
		ArrayList<Tile> melds = new ArrayList<Tile>();
		melds.add(atile1);
		melds.add(atile2);
		melds.add(atile3);
		Meld meld = new Meld(melds);
		table.add(meld);
//[[R1, R2, R3]]
//B11, R4, G11, O11, JK
		command = testAI.getStrategy().generateCommand();
		// can play all tile
		assertEquals("Play 3 2 0 4", command);
		hand2.remove(hand.indexOf(atile9));
		hand2.remove(hand.indexOf(atile10));
		hand2.remove(hand.indexOf(atile11));
		hand2.remove(hand.indexOf(bJoker));
		command = testAI.getStrategy().generateCommand();
		// can play all tile
		assertEquals("Play 0 to 0", command);
		hand2.remove(hand.indexOf(atile4));
		
		//-------
		ArrayList<Tile> melds2 = new ArrayList<Tile>();
		melds.add(atile9);
		melds.add(atile10);
		melds.add(atile11);
		melds.add(bJoker);
		Meld meld2 = new Meld(melds);
		table.add(meld2);
		meld.addTail(atile4);
		hand2.add(atile2);
		hand2.add(atile3);
		hand2.add(atile9);
		hand2.add(atile10);
		testAI.getStrategy().update(3, -10);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Play 1 2", command);
		Meld meld3 = new Meld(melds);
		meld3.addTail(atile2);
		meld3.addTail(atile3);
		table.add(meld3);
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		command = testAI.getStrategy().generateCommand();
		assertEquals("Move 0 tail to 2", command);
		table.get(0).removeTail();
		meld3.addTail(atile4);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Play 0 1", command);
		Meld meld4 = new Meld(melds);
		hand2.remove(hand.indexOf(atile9));
		hand2.remove(hand.indexOf(atile10));
		meld4.addTail(atile9);
		meld4.addTail(atile10);
		table.add(meld4);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Move 1 tail to 3 tail", command);
	}
	
	
	@Test
	public void test_11a() {
		//play init
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("B10");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 5);
		hand.add(atile1);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile4);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(aJoker);
		testAI.initHand(hand);
		Hand hand2 = new Hand(hand);
		testAI.getStrategy().setHand(hand2);
		String command = testAI.getStrategy().generateCommand();
		//first play run
		testAI.printHand();
		System.out.println(command);
		assertEquals("Play 0 1 12", command);	
	}
	
	@Test
	public void test_11b() {
		//play init
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("B10");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 5);
		hand.add(atile1);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile4);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(atile13);
		testAI.initHand(hand);
		Hand hand2 = new Hand(hand);
		testAI.getStrategy().setHand(hand2);
		String command = testAI.getStrategy().generateCommand();
		//first play run
		testAI.printHand();
		System.out.println(command);
		assertEquals("END", command);	
		hand.add(aJoker);
		command = testAI.getStrategy().generateCommand();
		testAI.printHand();
		System.out.println(command);
		assertEquals("Play 0 1 13 2", command);	
	}
	
	@Test
	public void test_12() {
		try {
			atile13 = new Tile("B11");
			atile12 = new Tile("B12");
			atile11 = new Tile("B13");
		
		}catch(InvalidTileException e) {
			fail();
		}
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		Hand hand2 = new Hand(hand);
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 14);
		testAI.getStrategy().update(2, 0);
		hand.add(atile13);
		hand.add(atile12);
		hand.add(atile11);
		testAI.initHand(hand);
		
		testAI.getStrategy().setHand(hand2);
		testAI.printHand();
		String command = testAI.getStrategy().generateCommand();
		System.out.println(command);
		assertEquals("Play 0 1 2", command);	
		hand.remove(0);
		hand.remove(0);
		hand.remove(0);
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("B10");
			atile6 = new Tile("B11");
			atile7 = new Tile("B12");
			atile8 = new Tile("R4");
			atile9 = new Tile("B9");
			atile10 = new Tile("B11");
			atile11 = new Tile("B10");
			atile12 = new Tile("B8");
			atile13 = new Tile("B12");
			bJoker = new Tile("J");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld meld = new Meld();
		meld.addTail(atile5);
		meld.addTail(atile6);
		meld.addTail(atile7);
		table.add(meld);
		
		//hand.add(atile1);
		hand.add(atile1);
		//hand.add(atile2);
		hand.add(atile2);
		//hand.add(atile3);
		hand.add(atile3);
		//hand.add(atile4);
		hand.add(atile4);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile13);
		command = testAI.getStrategy().generateCommand();
		//first play run
		testAI.printHand();
		System.out.println(command);
		assertEquals("Play 0 1 2 3", command);	
		hand.remove(3);
		hand.remove(2);
		hand.remove(1);
		hand.remove(0);
		command = testAI.getStrategy().generateCommand();
		testAI.printHand();
		System.out.println(command);
		System.out.println(table);
		assertEquals("Play 0 1 2 3", command);
		hand.remove(3);
		hand.remove(2);
		hand.remove(1);
		hand.remove(0);
		testAI.printHand();

	}
	
	
	
	@Test
	public void test_13() {
		//play init
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R3");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("O11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 5);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(atile7);
		hand.add(atile8);
		hand.add(atile9);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(bJoker);
		hand.add(aJoker);
		
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		hand2.sort();
		String command = testAI.getStrategy().generateCommand();
		//first play run
		assertEquals("Play 2 3 5 6", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getStrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//play second run
		assertEquals("Play 2 3 8", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play 5 1 6", command);
		hand2.remove(hand.indexOf(atile10));
		hand2.remove(hand.indexOf(atile11));
		hand2.remove(hand.indexOf(bJoker));
//G5, B6, O7, G10
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//after initial,2 player, no one has less 3 tile than ai, nothing to play, end
		assertEquals("END", command);
		hand2.add(atile1);
		hand2.add(atile2);
		hand2.add(atile3);
		hand2.sort();
		command = testAI.getStrategy().generateCommand();
		//2 player, one has less 3 tile than ai, play, or end
		assertEquals("Play 1 2 3", command);
		hand2.remove(hand.indexOf(atile1));
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.add(atile1);
//B6, R1, G5, G10, O7
		command = testAI.getStrategy().generateCommand();
		//2 player, one has less 3 tile than ai, tried play, but END
		assertEquals("END", command);
		ArrayList<Tile> melds = new ArrayList<Tile>();
		melds.add(atile2);
		melds.add(atile3);
		melds.add(atile4);
		Meld meld = new Meld(melds);
		table.add(meld);
//[[R2, R3, R4]]
		hand2.sort();
		testAI.printHand();
		//System.out.println(table);
		command = testAI.getStrategy().generateCommand();
		//2 player, one has less 3 tile than ai, play 2 table
		System.out.println(command);
		assertEquals("Play 1 to 0", command);
	}
	
	@Test
	public void test_cut() throws AbleToAddBothSideException {
		//END
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("R6");
			atile7 = new Tile("R7");
			atile8 = new Tile("R8");
			atile9 = new Tile("R9");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 1);
		testAI.getStrategy().update(2, 5);
		hand.add(atile9);
		hand.add(aJoker);
		hand.add(bJoker);
		
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
		testAI.getStrategy().setHand(hand2);
		String command = testAI.getStrategy().generateCommand();
		//play initial
		assertEquals("Play 0 1 2", command);
		hand.removeAll(hand);
		hand2 = new Hand(hand);
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		melda.add(atile4);
		melda.add(atile5);
		melda.add(atile6);
		table.add(melda);
		hand2.add(atile3);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Cut 0 at 1", command);
		melda.removeTail();
		melda.removeTail();
		melda.removeTail();
		melda.removeTail();
		meldb.add(atile3);
		meldb.add(atile4);
		meldb.add(atile5);
		meldb.add(atile6);
		table.add(meldb);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Play 0 to 0", command);
	}
	
	@Test
	public void test_move() throws AbleToAddBothSideException {
		//B12, R6, G2, G2, G8, G10, G12, G12, O4, O5, O6
		try {
			atile1 = new Tile("R6");
			atile2 = new Tile("O4");
			atile3 = new Tile("O5");
			atile4 = new Tile("O6");
			atile8 = new Tile("B12");
			atile9 = new Tile("G2");
			atile10 = new Tile("G12");
			atile11 = new Tile("G12");
			
			atile5 = new Tile("O10");
			atile6 = new Tile("O11");
			atile7 = new Tile("O12");
			bJoker = new Tile ("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 5);
		testAI.getStrategy().update(2, 5);
		hand.add(atile5);
		hand.add(atile6);
		hand.add(bJoker);
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
		testAI.getStrategy().setHand(hand2);
		String command = testAI.getStrategy().generateCommand();
		//play initial
		assertEquals("Play 0 1 2", command);
		hand.removeAll(hand);
		hand2 = new Hand(hand);
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile5);
		melda.add(atile6);
		melda.add(atile7);
		melda.addTail(bJoker);
		table.add(melda);
		hand2.add(atile1);
		hand2.add(atile2);
		hand2.add(atile3);
		hand2.add(atile4);
		hand2.add(atile8);
		hand2.add(atile9);
		hand2.add(atile10);
		hand2.add(atile11);
		testAI.printHand();
		System.out.println(table);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Play 7 1", command);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Move 0 tail to 0 tail", command);
	}
	
	@Test
	public void test_replace() throws AbleToAddBothSideException {
		//B12, R6, G2, G2, G8, G10, G12, G12, O4, O5, O6
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			bJoker = new Tile ("Joker");
			atile5 = new Tile("R5");
			atile10 = new Tile("R10");
			atile11 = new Tile("R11");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Meld> table = new ArrayList<Meld>();
		testAI.getStrategy().update(table);
		testAI.getStrategy().update(1, 5);
		testAI.getStrategy().update(2, 5);
		hand.add(atile10);
		hand.add(atile11);
		hand.add(bJoker);
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
		testAI.getStrategy().setHand(hand2);
		String command = testAI.getStrategy().generateCommand();
		//play initial
		assertEquals("Play 0 1 2", command);
		hand.removeAll(hand);
		hand2 = new Hand(hand);
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		melda.add(atile4);
		melda.addTail(bJoker);
		table.add(melda);
		hand2.add(atile5);
		testAI.printHand();
		System.out.println(table);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Replace 0 to 0 4", command);
	}
}
