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
	
/*	@Test
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
	}*/
	
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
}
