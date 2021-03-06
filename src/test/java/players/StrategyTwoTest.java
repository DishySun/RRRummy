package players;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import rrrummy.InvalidTileException;
import rrrummy.Meld;
import rrrummy.Table;
import rrrummy.Tile;

public class StrategyTwoTest {
	private Tile atile1,atile2,atile3,atile4,atile5,atile6,atile7,atile8,atile9,atile10,atile11,atile12,atile13,aJoker,bJoker;
	private ArrayList<Tile>  hand;
	private AI testAI;
	private Table table;
	private AIStrategy aiSTY;
	@Before
	public void setUp() throws Exception {
		testAI = new AI(new StrategyTwo());
		hand = new ArrayList<Tile>();
		table = new Table();
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
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		//other 2 players haven not play initial, thu END
		String command = testAI.getStrategy().generateCommand();
		assertEquals("END", command);
		testAI.getStrategy().update(1, 13);
		//someone have play initial, thu END
		command = testAI.getStrategy().generateCommand();
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
		assertEquals("Play 5 1 6", command);
		hand2.remove(hand.indexOf(atile10));
		hand2.remove(hand.indexOf(atile11));
		hand2.remove(hand.indexOf(bJoker));
//G5, B6, O7, G10
		command = testAI.getStrategy().generateCommand();
		//after initial,nothing to play, end
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
		String command = testAI.getCommandString();
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
		assertEquals("Play 5 1 6", command);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile7));
		hand2.remove(hand.indexOf(atile12));
//JK, G10, O11, B11
		command = testAI.getStrategy().generateCommand();
		// initial no. > 30, no way to play all, END
		assertEquals("END", command);
		
	}

	@Test
	public void test_generateCommand4() {	//17
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
		assertEquals("Play 5 1 6", command);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile7));
		hand2.remove(hand.indexOf(atile12));
//JK, G10, O11, B11
		// initial no. > 30, check playing,	
		command = testAI.getStrategy().generateCommand();		
		assertEquals("END", command);
		ArrayList<Tile> melds = new ArrayList<Tile>();
		melds.add(atile2);
		melds.add(atile3);
		melds.add(atile4);
		Meld meld = new Meld(melds);
		table.add(meld);
		//  check playing,cannot play all, check if tile can match meld on table
		command = testAI.getStrategy().generateCommand();
		//try to play joker, so plus 1
		assertEquals("Play 3 to 0 tail", command);
	}
	
	@Test
	public void test_generateCommand6() { //16
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
		testAI.getStrategy().update(1, 13);
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
//B5, B11, G5, G11, O5, O11, JK
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
		System.out.println("hand2:" +hand2);
		assertEquals("Play 0 1", command);
		Meld meld4 = new Meld(melds);
		hand2.remove(hand.indexOf(atile9));
		hand2.remove(hand.indexOf(atile10));
		meld4.addTail(atile9);
		meld4.addTail(atile10);
		table.add(meld4);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Move 1 tail to 3 tail", command);
		try {
			atile1 = new Tile("O12");
			atile2 = new Tile("G12");
			atile3 = new Tile("R12");
			atile4 = new Tile("B12");
			atile5 = new Tile("G12");
			atile6 = new Tile("B12");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Meld> table2 = new ArrayList<Meld>();
		testAI.getStrategy().update(table2);
		testAI.getStrategy().update(1, 13);
		testAI.getStrategy().update(2, 14);
		Meld meld5 = new Meld();
		meld5.addTail(atile1);
		meld5.addTail(atile2);
		meld5.addTail(atile3);
		meld5.addTail(atile4);
		ArrayList<Tile> handarr = new ArrayList<Tile>();
		Hand hand3 = new Hand(hand);
		hand3.add(atile6);
		hand3.add(atile5);
		table2.add(meld5);
		//System.out.println(table2);
		command = testAI.getStrategy().generateCommand();
		assertEquals("Play 1 0", command);
		
		Meld meld6 = new Meld();
		meld6.addTail(atile5);
		meld6.addTail(atile6);
		table2.add(meld6);
		System.out.println("0: " + table2.get(0));
		System.out.println("1: " + table2.get(1));
		//System.out.println(hand3);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile5));
		command = testAI.getStrategy().generateCommand();
		System.out.println(command);
		assertEquals("Move 0 head to 1", command);
		
	}
	
	@Test
	public void test_generateCommand7() {
		//play init,play rest,play all
		//B6, B8, B8, R9, G1, G2, G3, G11, G13, O2, O9, O12, O13, JK
		try {
			atile1 = new Tile("B6");
			atile2 = new Tile("B8");
			atile3 = new Tile("B8");
			atile4 = new Tile("R9");
			atile5 = new Tile("G1");
			atile6 = new Tile("G2");
			atile7 = new Tile("G3");
			atile8 = new Tile("G11");
			atile9 = new Tile("G13");
			atile10 = new Tile("O2");
			atile11 = new Tile("O9");
			atile12 = new Tile("O12");
			atile13 = new Tile("O13");
			aJoker = new Tile("JK");
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
		hand.add(atile13);
		hand.add(aJoker);
		Hand hand2 = new Hand(hand);
		
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getStrategy().setHand(hand2);
		
		ArrayList<Meld> table2 = new ArrayList<Meld>();
		testAI.getStrategy().update(table2);
		testAI.getStrategy().update(1, 10);
		testAI.getStrategy().update(2, 14);
		
		String command = testAI.getStrategy().generateCommand();
		command = testAI.getStrategy().generateCommand();
		System.out.println(command);
		assertEquals("END", command);
		
	}
}
