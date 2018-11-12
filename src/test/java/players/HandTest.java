package players;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import rrrummy.*;

public class HandTest {
	private Tile atile1,atile2,atile3,atile4,atile5,atile6,atile7,atile8,atile9,atile10,atile11,atile12,atile13,atile14,atile15,atile16,aJoker,bJoker;
	private Tile btile1,btile2,btile3,btile4,btile5,btile6,btile7,btile8,btile9,btile10,btile11,btile12,btile13;
	private Hand hand;
	private Table table;
	private ArrayList<Tile> tileArray;
	@Before
	public void setUp() throws Exception {
		hand = new Hand(new ArrayList<Tile>());
		table = new Table();
		tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("B6");
			atile7 = new Tile("O6");
			atile8 = new Tile("R6");
			atile9 = new Tile("G9");
			atile10 = new Tile("B10");
			atile11 = new Tile("B11");
			atile12 = new Tile("B12");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		try {
			btile1 = new Tile("R1");
			btile2 = new Tile("O2");
			btile3 = new Tile("G3");
			btile4 = new Tile("B4");
			btile5 = new Tile("R5");
			btile6 = new Tile("B6");
			btile7 = new Tile("O7");
			btile8 = new Tile("R8");
			btile9 = new Tile("G9");
			btile10 = new Tile("O10");
			btile11 = new Tile("G11");
			btile12 = new Tile("B12");
			btile13 = new Tile("R13");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
	}

	@Test
	public void testConstructor() {
		Hand testHand = new Hand(new ArrayList<Tile>());
		assertEquals(0, testHand.size());
		
	}
	@Test
	public void testAddRemove() {
		Hand testHand = new Hand(new ArrayList<Tile>());
		//R5 B3 O6 J B7 R11 G1 G10
		try {
			//add
			testHand.add(new Tile("R5"));
			assertEquals(1, testHand.size());
			testHand.add(new Tile("B3"));
			assertEquals(2, testHand.size());
			testHand.add(new Tile("O6"));
			assertEquals(3, testHand.size());
			testHand.add(new Tile("J"));
			assertEquals(4, testHand.size());
			testHand.add(new Tile("B7"));
			assertEquals(5, testHand.size());
			testHand.add(new Tile("R11"));
			assertEquals(6, testHand.size());
			testHand.add(new Tile("G1"));
			assertEquals(7, testHand.size());
			testHand.add(new Tile("G10"));
			assertEquals(8, testHand.size());
			//B3, B7, R5, R11, G1, G10, O6, JK
			assertEquals(Tile.Color.BLUE, testHand.getTile(0).getColor());
			assertEquals(3, testHand.getTile(0).getNumber());
			assertEquals(Tile.Color.BLUE, testHand.getTile(1).getColor());
			assertEquals(7, testHand.getTile(1).getNumber());
			assertEquals(Tile.Color.RED, testHand.getTile(2).getColor());
			assertEquals(5, testHand.getTile(2).getNumber());
			assertEquals(Tile.Color.RED, testHand.getTile(3).getColor());
			assertEquals(11, testHand.getTile(3).getNumber());
			assertEquals(Tile.Color.GREEN, testHand.getTile(4).getColor());
			assertEquals(1, testHand.getTile(4).getNumber());
			assertEquals(Tile.Color.GREEN, testHand.getTile(5).getColor());
			assertEquals(10, testHand.getTile(5).getNumber());
			assertEquals(Tile.Color.ORANGE, testHand.getTile(6).getColor());
			assertEquals(6, testHand.getTile(6).getNumber());
			assertEquals(Tile.Color.JOKER, testHand.getTile(7).getColor());
			assertTrue(testHand.getTile(7).isJoker());
			
			//remove
			Tile t = testHand.remove();
			assertEquals(7, testHand.size());
			assertEquals(Tile.Color.JOKER, t.getColor());
			assertTrue(t.isJoker());
			t = testHand.remove(3);//r11
			assertEquals(6, testHand.size());
			assertEquals(Tile.Color.RED, t.getColor());
			assertEquals(11, t.getNumber());
		} catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}
	}
	@Test
	public void testSort() {
		Hand testHand = new Hand(new ArrayList<Tile>());
		try {
			testHand.add(new Tile("R5"));
			testHand.add(new Tile("B3"));
			testHand.add(new Tile("O6"));
			testHand.add(new Tile("J"));
			testHand.add(new Tile("B7"));
			testHand.add(new Tile("R11"));
			testHand.add(new Tile("G1"));
			testHand.add(new Tile("G10"));
			//R5 B3 O6 J B7 R11 G1 G10
			testHand.sort();
			//B3 B7 R5 R11 G1 G10 O6 J
			assertEquals(Tile.Color.BLUE, testHand.getTile(0).getColor());
			assertEquals(Tile.Color.BLUE, testHand.getTile(1).getColor());
			assertEquals(Tile.Color.RED, testHand.getTile(2).getColor());
			assertEquals(Tile.Color.RED, testHand.getTile(3).getColor());
			assertEquals(Tile.Color.GREEN, testHand.getTile(4).getColor());
			assertEquals(Tile.Color.GREEN, testHand.getTile(5).getColor());
			assertEquals(Tile.Color.ORANGE, testHand.getTile(6).getColor());
			assertEquals(Tile.Color.JOKER, testHand.getTile(7).getColor());
			assertEquals(3, testHand.getTile(0).getNumber());
			assertEquals(7, testHand.getTile(1).getNumber());
			assertEquals(5, testHand.getTile(2).getNumber());
			assertEquals(11, testHand.getTile(3).getNumber());
			assertEquals(1, testHand.getTile(4).getNumber());
			assertEquals(10, testHand.getTile(5).getNumber());
			assertEquals(6, testHand.getTile(6).getNumber());
			assertEquals(0, testHand.getTile(7).getNumber());
		}catch(InvalidTileException e) {
			fail(e.getErrMsg());
		}
	}
	
	@Test
	public void test_checkSum1(){
		ArrayList<Tile> arr = new ArrayList<Tile>(); 
		arr.add(btile11);
		arr.add(btile12);
		arr.add(btile13);	
		int i = hand.checkSum(arr);
		assertEquals(36, i);
	}
	
	@Test
	public void test_checkSum2(){
		ArrayList<Tile> arr = new ArrayList<Tile>(); 
		arr.add(btile11);
		arr.add(aJoker);	
		arr.add(btile13);
		int i = hand.checkSum(arr);
		assertEquals(36, i);
	}
	
	@Test
	public void test_checkSum3(){
		ArrayList<Tile> arr = new ArrayList<Tile>(); 
		arr.add(bJoker);
		arr.add(btile12);
		arr.add(aJoker);	
		int i = hand.checkSum(arr);
		assertEquals(36, i);
	}
	
	@Test
	public void test_hasSameColor() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R13");
			atile2 = new Tile("R4");
			atile3 = new Tile("G5");
			atile4 = new Tile("B6");
			atile5 = new Tile("O7");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		tileArray.add(atile1);
		assertTrue(hand.hasSameColor(tileArray, atile2));
		assertFalse(hand.hasSameColor(tileArray, atile3));
		assertFalse(hand.hasSameColor(tileArray, atile4));
		assertFalse(hand.hasSameColor(tileArray, atile5));
		assertFalse(hand.hasSameColor(tileArray, aJoker));
	}
	
	@Test
	public void test_indexOf() {
		Hand testHand = new Hand(new ArrayList<Tile>());
		try {
			testHand.add(new Tile("R5"));
			testHand.add(new Tile("B3"));
			testHand.add(new Tile("O6"));
			testHand.add(new Tile("J"));
			testHand.add(new Tile("B7"));
			testHand.add(new Tile("R11"));
			testHand.add(new Tile("G1"));
			testHand.add(new Tile("G10"));
			//R5 B3 O6 J B7 R11 G1 G10
			testHand.sort();
			//B3 B7 R5 R11 G1 G10 O6 J
			testHand.handIndexOf(testHand.getTile(3));
			assertEquals(3, testHand.handIndexOf(testHand.getTile(3)));
		}catch(InvalidTileException e) {
			fail(e.getErrMsg());
		}
	}
	@Test
	public void test_findRun0() {
		try {
			atile1 = new Tile("R13");
			atile2 = new Tile("R4");
			atile3 = new Tile("R2");
			atile4 = new Tile("R5");
			atile5 = new Tile("B6");
			atile6 = new Tile("O7");
			atile7 = new Tile("R3");
			atile8 = new Tile("R8");
			atile9 = new Tile("G9");
			atile10 = new Tile("R10");
			atile11 = new Tile("R11");
			atile12 = new Tile("R12");
			atile13 = new Tile("R1");
			aJoker = new Tile("J");
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
		
		tileArray = hand.findRun();
		assertEquals(Tile.Color.RED, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(4).getColor());
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(2, tileArray.get(1).getNumber());
		assertEquals(3, tileArray.get(2).getNumber());
		assertEquals(4, tileArray.get(3).getNumber());
		assertEquals(5, tileArray.get(4).getNumber());
	}
	@Test
	public void test_findRun2() {
		//has joker
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("O2");
			atile2 = new Tile("R3");
			atile3 = new Tile("R4");
			atile4 = new Tile("O5");
			atile5 = new Tile("R6");
			atile6 = new Tile("O6");
			atile7 = new Tile("R1");
			atile8 = new Tile("R7");
			atile9 = new Tile("G9");
			atile10 = new Tile("R8");
			atile11 = new Tile("R10");
			atile12 = new Tile("R11");
			atile13 = new Tile("R12");
			aJoker = new Tile("J");
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
		
		tileArray = hand.findRun();
		assertEquals(Tile.Color.RED, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(6, tileArray.get(0).getNumber());
		assertEquals(7, tileArray.get(1).getNumber());
		assertEquals(8, tileArray.get(2).getNumber());
	}
	@Test
	public void test_findRun3() {
		//has 2 joker, only use 2
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("O2");
			atile3 = new Tile("B3");
			atile4 = new Tile("R4");
			atile5 = new Tile("O5");
			atile6 = new Tile("B6");
			atile7 = new Tile("O6");
			atile8 = new Tile("R7");
			atile9 = new Tile("B9");
			atile10 = new Tile("O8");
			atile11 = new Tile("R10");
			atile12 = new Tile("O11");
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
		hand.add(aJoker);
		hand.add(bJoker);
		
		tileArray = hand.findRun();
		assertEquals(Tile.Color.ORANGE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.ORANGE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(Tile.Color.ORANGE, tileArray.get(3).getColor());
		assertEquals(5, tileArray.get(0).getNumber());
		assertEquals(6, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
		assertEquals(8, tileArray.get(3).getNumber());
	}
	
	@Test
	public void test_findRun4() {
		//has 2 joker play 2
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("O2");
			atile3 = new Tile("O13");
			atile4 = new Tile("R4");
			atile5 = new Tile("O5");
			atile6 = new Tile("O8");
			atile7 = new Tile("O11");
			atile8 = new Tile("R7");
			atile9 = new Tile("G5");
			atile13 = new Tile("R7");
			atile10 = new Tile("O5");
			atile11 = new Tile("B13");
			atile12 = new Tile("R12");
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
		hand.add(aJoker);
		hand.add(bJoker);
		
		tileArray = hand.findRun();
		assertEquals("[O11, JK, O13]", tileArray.toString());

	}

	@Test
	public void test_findRun5() {
		//null
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("O2");
			atile3 = new Tile("O13");
			atile4 = new Tile("R4");
			atile5 = new Tile("O5");
			atile6 = new Tile("O8");
			atile7 = new Tile("O11");
			atile8 = new Tile("R7");
			atile9 = new Tile("G5");
			atile13 = new Tile("R7");
			atile10 = new Tile("O5");
			atile11 = new Tile("B13");
			atile12 = new Tile("R12");
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
		
		tileArray = hand.findRun();
		assertEquals(null, tileArray);
	}
	
	@Test
	public void test_findGroup() {
		// no joker
		//O B G R last to first
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R9");
			atile6 = new Tile("B6");
			atile7 = new Tile("O6");
			atile8 = new Tile("R6");
			atile9 = new Tile("G9");
			atile10 = new Tile("B9");
			atile11 = new Tile("B11");
			atile12 = new Tile("O9");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
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
		hand.add(bJoker);
		tileArray = hand.findGroup();
		assertEquals(Tile.Color.ORANGE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(2).getColor());
		assertEquals(6, tileArray.get(0).getNumber());
		assertEquals(6, tileArray.get(1).getNumber());
		assertEquals(6, tileArray.get(2).getNumber());
	}
	
	@Test
	public void test_findGroup2() {
		//has joker
		//B G R last to first
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R9");
			atile6 = new Tile("B6");
			atile7 = new Tile("O6");
			atile8 = new Tile("R1");
			atile9 = new Tile("G9");
			atile10 = new Tile("B10");
			atile11 = new Tile("B11");
			atile12 = new Tile("O1");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
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
		hand.add(aJoker);
		tileArray = hand.findGroup();
		assertEquals(Tile.Color.ORANGE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(1, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
	}
	
	
	@Test
	public void test_findGroup3() {
		//has 2 joker use 1
		//B R last to first
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R9");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R8");
			atile9 = new Tile("G1");
			atile10 = new Tile("B9");
			atile11 = new Tile("B11");
			atile12 = new Tile("G3");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
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
		tileArray = hand.findGroup();
		//System.out.println(tileArray);
		assertEquals("[G1, R1, JK]", tileArray.toString());
	}
	
	@Test
	public void test_findGroup4() {
		//has 2 joker use 2
		//B R last to first
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R9");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R8");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("B11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
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
		tileArray = hand.findGroup();
		assertEquals(Tile.Color.RED, tileArray.get(0).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(0, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
	}
	
	@Test
	public void test_findGroup5() {
		//null
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R9");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R8");
			atile9 = new Tile("G10");
			atile10 = new Tile("B11");
			atile11 = new Tile("B11");
			atile12 = new Tile("G5");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
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
		//hand.add(bJoker);
		hand.add(aJoker);
		tileArray = hand.findGroup();
		assertEquals(null, tileArray);
	}
	
	@Test
	public void test_SortbyColor() {//B G O R
		try {
			hand.add(new Tile("R5"));
			hand.add(new Tile("B3"));
			hand.add(new Tile("O5"));
			hand.add(new Tile("J"));
			hand.add(new Tile("B7"));
			hand.add(new Tile("R11"));
			hand.add(new Tile("G5"));
			hand.add(new Tile("G10"));
			//R5 B3 O5 J B7 R11 G5 G10
			hand.sortByNum();
			//J B3 O5 G5 R5 B7 G10 R11
			assertEquals(Tile.Color.JOKER, hand.getTile(0).getColor());
			assertEquals(Tile.Color.BLUE, hand.getTile(1).getColor());
			assertEquals(Tile.Color.ORANGE, hand.getTile(2).getColor());
			assertEquals(Tile.Color.GREEN, hand.getTile(3).getColor());
			assertEquals(Tile.Color.RED, hand.getTile(4).getColor());
			assertEquals(Tile.Color.BLUE, hand.getTile(5).getColor());
			assertEquals(Tile.Color.GREEN, hand.getTile(6).getColor());
			assertEquals(Tile.Color.RED, hand.getTile(7).getColor());
			
			assertEquals(0, hand.getTile(0).getNumber());
			assertEquals(3, hand.getTile(1).getNumber());
			assertEquals(5, hand.getTile(2).getNumber());
			assertEquals(5, hand.getTile(3).getNumber());
			assertEquals(5, hand.getTile(4).getNumber());
			assertEquals(7, hand.getTile(5).getNumber());
			assertEquals(10, hand.getTile(6).getNumber());
			assertEquals(11, hand.getTile(7).getNumber());		
		}catch(InvalidTileException e) {
			fail(e.getErrMsg());
		}
	}
	
	@Test
	public void test_findInitial() {
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
		//1,2,3
		//3 4 j
		//11 11 j
		int count = 0;
		count = hand.checkInitialSum();
		assertEquals(56,count);
	}
	//B2, B5, B7, B10, R3, R10, G6, G9, G13, G13, O1, O2, O5, O8, O11, O13, JK
	@Test
	public void test_findInitial2() {
		try {
			atile1 = new Tile("B2");
			atile2 = new Tile("B5");
			atile3 = new Tile("B7");
			atile4 = new Tile("B10");
			atile5 = new Tile("R3");
			atile6 = new Tile("R10");
			atile7 = new Tile("G6");
			atile8 = new Tile("G9");
			atile9 = new Tile("G13");
			atile10 = new Tile("G13");
			atile11 = new Tile("O1");
			atile12 = new Tile("O2");
			atile13 = new Tile("O5");
			atile14 = new Tile("O8");
			atile15 = new Tile("O11");
			atile16 = new Tile("O13");
			aJoker = new Tile("J");
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
		hand.add(atile14);
		hand.add(atile15);
		hand.add(atile16);
		hand.add(bJoker);
		//1,2,3
		//3 4 j
		//11 11 j
		int count = 0;

		count = hand.checkInitialSum();
		assertEquals(36,count);
	}
	@Test
	public void test_play2Table() throws AbleToAddBothSideException {
		//test find, and play
		HashMap<Tile,Integer> tile2play = new HashMap<Tile,Integer>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("G4");
			atile6 = new Tile("R6");
			atile7 = new Tile("O4");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B4");
			atile11 = new Tile("O11");
			atile12 = new Tile("G5");
			atile13 = new Tile("R5");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		melda.add(atile4);
		meldb.add(atile5);
		meldb.add(atile8);
		meldb.addTail(aJoker);
		meldList.add(melda);
		meldList.add(meldb);
		assertEquals(2, meldList.size());
//[[R1, R2, R3, R4], [G4, R4, JK]]
		hand.add(atile6);	//r6
		hand.add(atile13);//r5
		hand.add(bJoker);//joker
		hand.add(atile10);//B4
//B4, R5, R6, JK
		tile2play = hand.findMeldsOnTable(meldList);
		assertEquals(1, tile2play.size());
		assertEquals("{B4=1}", tile2play.toString());
		/*hand.remove(0);
		meldList.get(1).addTail(atile10);*/
		hand.play2Table(tile2play, meldList);
//[[R1, R2, R3, R4], [G4, R4, JK, B4]]
//R5, R6, JK
		tile2play = hand.findMeldsOnTable(meldList);
		assertEquals(1, tile2play.size());
		assertEquals("{R5=0}", tile2play.toString());
		hand.play2Table(tile2play, meldList);
//[[R1, R2, R3, R4,R5], [G4, R4, JK, B4]]
//R6, JK		
		tile2play = hand.findMeldsOnTable(meldList);
		assertEquals("{R6=0}", tile2play.toString());
		hand.play2Table(tile2play, meldList);
//[[R1, R2, R3, R4, R5, R6], [G4, R4, JK, B4]]
//JK
		tile2play = hand.findMeldsOnTable(meldList);
		assertEquals("{JK=0}", tile2play.toString());
	}
	
	@Test
	public void test_canPlayAll() throws AbleToAddBothSideException {
		//dont care about table
		HashMap<Tile,Integer> tile2play = new HashMap<Tile,Integer>();
		boolean canPlay = false;
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("G4");
			atile6 = new Tile("R6");
			atile7 = new Tile("O4");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B4");
			atile11 = new Tile("O5");
			atile12 = new Tile("G5");
			atile13 = new Tile("R5");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		melda.add(atile4);
		meldb.add(atile5);
		meldb.add(atile8);
		meldb.addTail(aJoker);
		meldList.add(melda);
		meldList.add(meldb);
		assertEquals(2, meldList.size());
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile3);
		hand.add(atile4);
		hand.add(atile11);
		hand.add(atile12);
		hand.add(atile13);
		hand.add(aJoker);
		
		canPlay = hand.canPlayAll(meldList);
		assertTrue(canPlay);
	}
	
	@Test
	public void test_canPlayAll2() throws AbleToAddBothSideException {
		HashMap<Tile,Integer> tile2play = new HashMap<Tile,Integer>();
		boolean canPlay = false;
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("G4");
			atile6 = new Tile("R8");
			atile7 = new Tile("O4");
			atile8 = new Tile("R4");
			atile9 = new Tile("B6");
			atile10 = new Tile("B4");
			atile11 = new Tile("R6");
			atile12 = new Tile("G7");
			atile13 = new Tile("B5");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		meldb.add(atile4);
		meldb.add(atile5);
		meldb.addTail(bJoker);

		meldList.add(melda);
		meldList.add(meldb);
//[[R1, R2, R3], [R4, G4, JK]]
		assertEquals(2, meldList.size());
		hand.add(atile6);//R8*
		hand.add(atile7);//O4
		hand.add(atile8);//R4 
		hand.add(atile9);//B6
		hand.add(atile10);//B4
		hand.add(atile11);//R6
		hand.add(atile12);//G7*
		hand.add(atile13);//B5
		hand.add(aJoker);//J
		canPlay = hand.canPlayAll(meldList);
//[B4, B5, B6], [R4, JK, R6], {O4=1} = 7
		assertFalse(canPlay);
//B4, B5, B6, R4, R6, R8, G7, O4, JK
		hand.remove(5);
		hand.remove(5);
//B4, B5, B6, R4, R6, O4, JK
//[[R1, R2, R3], [R4, G4, JK]]
		canPlay = hand.canPlayAll(meldList);
		assertTrue(canPlay);
	}
	
	@Test
	public void test_findMoveRun() throws AbleToAddBothSideException {
		//test find, and play
		HashMap<ArrayList<Tile>,Integer> tile2play = new HashMap<ArrayList<Tile>,Integer>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("G4");
			atile6 = new Tile("R6");
			atile7 = new Tile("O4");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B4");
			atile11 = new Tile("R6");
			atile12 = new Tile("B5");
			atile13 = new Tile("R5");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		melda.add(atile4);
		meldb.add(atile5);
		meldb.add(atile8);
		meldb.addTail(aJoker);
		meldList.add(melda);
		meldList.add(meldb);		
		assertEquals(2, meldList.size());
//[[R1, R2, R3, R4], [G4, R4, JK]]
		hand.add(atile6);	//r6
		hand.add(atile13);//r5
		hand.add(atile12);//b5
		hand.add(atile10);//b6
//[R1, R2, R3, R4]
//B4, B5, R5, R6
		//System.out.println(meldb);
		tile2play = hand.findRunMove(melda);
		assertEquals("{[R5, R6]=3}", tile2play.toString());	//move 0 3
//[JK, G4, R4,]
//B4, B5, R5, R6	
		//System.out.println(meldb);
		tile2play = hand.findRunMove(meldb);
		//assertEquals(null, tile2play);
		//System.out.println(meldb);
		meldb.addTail(atile10);//b4
//[JK, G4, R4, B4]
//B4, B5, R5, R6
		hand.remove(3);
		hand.remove(2);
		tile2play = hand.findRunMove(meldb);
		assertEquals(null, tile2play);
		
		
	}

	@Test
	public void test_findMoveSet() throws AbleToAddBothSideException {
		//test find, and play
		HashMap<ArrayList<Tile>,Integer> tile2play = new HashMap<ArrayList<Tile>,Integer>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("O1");
			atile3 = new Tile("B1");
			atile4 = new Tile("G1");
			atile5 = new Tile("G4");
			atile6 = new Tile("O1");
			atile7 = new Tile("B1");
			atile8 = new Tile("R4");
			atile9 = new Tile("G10");
			atile10 = new Tile("B4");
			atile11 = new Tile("O11");
			atile12 = new Tile("B5");
			atile13 = new Tile("R5");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile4);//g1
		melda.add(atile1);//r2
		melda.add(atile2);//o1
		melda.add(atile3);//b1
		meldb.add(atile1);
		meldb.add(atile2);
		meldb.addTail(aJoker);
		meldList.add(melda);
		meldList.add(meldb);
		assertEquals(2, meldList.size());
//[[R1, O1, B1, G1], [R1, O1, JK]]
		hand.add(atile6);	//o1
		hand.add(atile7);//b1
//[[R1, G1, O1, B1], [R1, O1, JK]]
//O1,B1
		tile2play = hand.findSetMove(melda);
//[[G1, O1, B1, R1], [R1, O1, JK]]
		assertEquals("{[O1, B1]=0}", tile2play.toString());
//[JK, G4, R4,]
//B4, B5, R5, R6		
		tile2play = hand.findSetMove(meldb);
		assertEquals(null, tile2play);
		meldb.addTail(atile4);//b4
//[R1, O1, JK, G1]
//B4, B5, R5, R6
		tile2play = hand.findSetMove(meldb);
		assertEquals("{[O1, B1]=3}", tile2play.toString());
		hand.remove(0);
		hand.remove(0);
		hand.add(atile6);	//o1
		hand.add(atile4);//G1
		tile2play = hand.findSetMove(meldb);
		assertEquals("{[O1, G1]=0}", tile2play.toString());

		try {
			atile1 = new Tile("G4");
			atile2 = new Tile("G5");
			atile3 = new Tile("G6");
			atile4 = new Tile("G7");
			atile5 = new Tile("O4");
			atile6 = new Tile("R4");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld meldc = new Meld();
		meldc.add(atile1);
		meldc.add(atile2);
		meldc.add(atile3);
		meldc.add(atile4);
		hand.remove(0);
		hand.remove(0);
		hand.add(atile5);
		hand.add(atile6);
		//System.out.println(hand);
		//System.out.println(meldc);
		tile2play = hand.findSetMove(meldc);
		assertEquals("{[O4, R4]=0}", tile2play.toString());
		
		try {
			atile1 = new Tile("O2");
			atile2 = new Tile("G2");
			atile3 = new Tile("R2");
			atile4 = new Tile("B2");
			atile5 = new Tile("G2");
			atile6 = new Tile("B2");
		}catch(InvalidTileException e) {
			fail();
		}
		meldc = new Meld();
		meldc.add(atile1);
		meldc.add(atile2);
		meldc.add(atile3);
		meldc.addTail(atile4);
		hand.remove(0);
		hand.remove(0);
		hand.add(atile5);
		hand.add(atile6);
		System.out.println("hand:" +hand);
		System.out.println("meld;" +meldc);
		tile2play = hand.findSetMove(meldc);
		assertEquals("{[G2, B2]=0}", tile2play.toString());

	}
	public void testRoundScore1() {
		try {
			Hand testHand = new Hand(new ArrayList<Tile>());
			testHand.add(new Tile("R5"));
			testHand.add(new Tile("B3"));
			testHand.add(new Tile("O6"));
			testHand.add(new Tile("J"));
			testHand.add(new Tile("B7"));
			testHand.add(new Tile("R11"));
			testHand.add(new Tile("G1"));
			testHand.add(new Tile("G10"));
			testHand.getRoundScore();
			assertEquals(73, testHand.totalHandScore);
		} catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}

	}

	public void testRoundScore2() {
		try {
			Hand testHand = new Hand(new ArrayList<Tile>());
			testHand.add(new Tile("R5"));
			testHand.add(new Tile("B3"));
			testHand.add(new Tile("O6"));
			testHand.add(new Tile("J"));
			testHand.add(new Tile("B7"));
			testHand.add(new Tile("R11"));
			testHand.add(new Tile("G1"));
			testHand.add(new Tile("G10"));
			testHand.remove(3);
			testHand.remove(0);
			testHand.getRoundScore();
			assertEquals(38, testHand.totalHandScore);
		} catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}

	}
}
