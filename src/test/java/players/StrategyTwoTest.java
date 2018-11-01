package players;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import observer.GameData;
import rrrummy.InvalidTileException;
import rrrummy.Meld;
import rrrummy.Table;
import rrrummy.Tile;

public class StrategyTwoTest {
	private Tile atile1,atile2,atile3,atile4,atile5,atile6,atile7,atile8,atile9,atile10,atile11,atile12,atile13,aJoker,bJoker;
	private ArrayList<Tile>  hand;
	private AI testAI;
	private Table table;
	private GameData data;
	private AIStrategy aiSTY;
	@Before
	public void setUp() throws Exception {
		data = new GameData();
		testAI = new AI(new StrategyTwo(data));
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
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		handSizes.put(1,14);
		handSizes.put(2,14);
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
		testAI.getSrategy().setHand(hand2);
		data.setValue(table, handSizes);
		//other 2 players haven not play initial, thu END
		String command = testAI.getSrategy().generateCommand();
		assertEquals("END", command);
		handSizes.put(1,13);
		//someone have play initial, thu END
		command = testAI.getSrategy().generateCommand();
		//first play run
		assertEquals("Play[R1, R2, R3, R4]", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getSrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		command = testAI.getSrategy().generateCommand();
		//play second run
		assertEquals("Play[R3, R4, JK]", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		command = testAI.getSrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play[O11, B11, JK]", command);
		hand2.remove(hand.indexOf(atile10));
		hand2.remove(hand.indexOf(atile11));
		hand2.remove(hand.indexOf(bJoker));
//G5, B6, O7, G10
		command = testAI.getSrategy().generateCommand();
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
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		handSizes.put(1,1);
		handSizes.put(2,5);
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
		testAI.getSrategy().setHand(hand2);
		data.setValue(table, handSizes);
		String command = testAI.getSrategy().generateCommand();
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
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		handSizes.put(1,1);
		handSizes.put(2,5);
		Hand hand2 = new Hand(hand);
		testAI.initHand(hand);
//B6, B11, R1, R2, R3, R3, R4, R4, G5, G10, O7, O11, JK, JK
		testAI.getSrategy().setHand(hand2);
		data.setValue(table, handSizes);
		String command = testAI.getSrategy().generateCommand();
		//first play run
		assertEquals("Play[R1, R2, R3, R4]", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getSrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		command = testAI.getSrategy().generateCommand();
		//play second run
		assertEquals("Play[R3, R4, JK]", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		command = testAI.getSrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play[O5, G5, B5]", command);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile7));
		hand2.remove(hand.indexOf(atile12));
//JK, G10, O11, B11
		command = testAI.getSrategy().generateCommand();
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
		HashMap<Integer, Integer> handSizes = new HashMap<Integer, Integer>();
		ArrayList<Meld> table = new ArrayList<Meld>();
		handSizes.put(1,1);
		handSizes.put(2,5);
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
		testAI.getSrategy().setHand(hand2);
		data.setValue(table, handSizes);
		String command = testAI.getSrategy().generateCommand();
		//first play run
		assertEquals("Play[R1, R2, R3, R4]", command);
		//remove hand R1 R2 R3 R4
		hand2.remove(hand.indexOf(atile2));
		hand2.remove(hand.indexOf(atile3));
		hand2.remove(hand.indexOf(atile4));
		hand2.remove(hand.indexOf(atile1));
		testAI.getSrategy().setHand(hand2);
//B6, B11, R3, R4, G5, G10, O7, O11, JK, JK
		command = testAI.getSrategy().generateCommand();
		//play second run
		assertEquals("Play[R3, R4, JK]", command);
		hand2.remove(hand.indexOf(atile5));
		hand2.remove(hand.indexOf(atile8));
		hand2.remove(hand.indexOf(aJoker));
//B6, B11, G5, G10, O7, O11, JK
		command = testAI.getSrategy().generateCommand();
		//no run to play , play first group
		assertEquals("Play[O5, G5, B5]", command);
		hand2.remove(hand.indexOf(atile6));
		hand2.remove(hand.indexOf(atile7));
		hand2.remove(hand.indexOf(atile12));
//JK, G10, O11, B11
		// initial no. > 30, check playing,
		command = testAI.getSrategy().generateCommand();
		assertEquals("END", command);
		System.out.println(hand);
		ArrayList<Tile> melds = new ArrayList<Tile>();
		melds.add(atile2);
		melds.add(atile3);
		melds.add(atile4);
		Meld meld = new Meld(melds);
		table.add(meld);
		//  check playing,cannot play all, check if tile can match meld on table
		command = testAI.getSrategy().generateCommand();
		assertEquals("PlayJKto[R2, R3, R4]", command);
	}
	
}