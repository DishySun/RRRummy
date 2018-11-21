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

public class AILogicTest {
	private Tile atile1,atile2,atile3,atile4,atile5,atile6,atile7,atile8,atile9,atile10,atile11,atile12,atile13,atile14,atile15,atile16,aJoker,bJoker;
	private Tile btile1,btile2,btile3,btile4,btile5,btile6,btile7,btile8,btile9,btile10,btile11,btile12,btile13;
	private Hand hand;
	private AILogic logic;
	private Table table;
	private ArrayList<Tile> tileArray;
	private ArrayList<Meld> meldList;
	@Before
	public void setUp() throws Exception {
		meldList = new ArrayList<Meld>();
		hand = new Hand(new ArrayList<Tile>());
		logic = new AILogic(hand,meldList);
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
		assertTrue(logic.hasSameColor(tileArray, atile2));
		assertFalse(logic.hasSameColor(tileArray, atile3));
		assertFalse(logic.hasSameColor(tileArray, atile4));
		assertFalse(logic.hasSameColor(tileArray, atile5));
		assertFalse(logic.hasSameColor(tileArray, aJoker));
	}
	
	@Test
	public void test_checkSum1(){
		ArrayList<Tile> arr = new ArrayList<Tile>(); 
		arr.add(btile11);
		arr.add(btile12);
		arr.add(btile13);	
		int i = logic.checkSum(arr);
		assertEquals(36, i);
	}
	
	@Test
	public void test_checkSum2(){
		ArrayList<Tile> arr = new ArrayList<Tile>(); 
		arr.add(btile11);
		arr.add(aJoker);	
		arr.add(btile13);
		int i = logic.checkSum(arr);
		assertEquals(36, i);
	}
	
	@Test
	public void test_checkSum3(){
		ArrayList<Tile> arr = new ArrayList<Tile>(); 
		arr.add(bJoker);
		arr.add(btile12);
		arr.add(aJoker);	
		int i = logic.checkSum(arr);
		assertEquals(36, i);
	}
	
//B1, B1, B3, B13, R2, R3, R4, R5, R7, R8, R11, R13, G4, O6, O7, O9
	@Test
	public void test_findRun() {
		try {
			atile1 = new Tile("B1");
			atile2 = new Tile("B1");
			atile3 = new Tile("B3");
			atile4 = new Tile("R2");
			atile5 = new Tile("R3");
			atile6 = new Tile("R4");
			atile7 = new Tile("R5");
			atile8 = new Tile("R7");
			atile9 = new Tile("R8");
			atile10 = new Tile("R11");
			atile11 = new Tile("R13");
			atile12 = new Tile("G4");
			atile13 = new Tile("O6");
			btile1 = new Tile("O7");
			btile2 = new Tile("O9");
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
		hand.add(btile1);
		hand.add(btile2);
		System.out.println(hand);
		tileArray = logic.findRun();
		assertEquals("[R2, R3, R4, R5]", tileArray.toString());
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
		
		tileArray = logic.findRun();
		assertEquals("[R8, JK, R10, R11, R12, R13]", tileArray.toString());
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
		
		tileArray = logic.findRun();
		assertEquals("[R10, R11, JK]",tileArray.toString());
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
		
		tileArray = logic.findRun();
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
		
		tileArray = logic.findRun();
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
		
		tileArray = logic.findRun();
		assertEquals(null, tileArray);
	}
	
	@Test
	public void test_findSet() {
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
		tileArray = logic.findSet();
		assertEquals("[O9, G9, R9, B9]",tileArray.toString());

	}
	
	@Test
	public void test_findSet2() {
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
		tileArray = logic.findSet();
		assertEquals("[G9, R9, JK]",tileArray.toString());
	}
	
	
	@Test
	public void test_findSet3() {
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
		tileArray = logic.findSet();
		//System.out.println(tileArray);
		assertEquals("[R9, B9, JK, JK]", tileArray.toString());
	}
	
	@Test
	public void test_findSet4() {
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
		tileArray = logic.findSet();
		assertEquals("[B11, JK, JK]",tileArray.toString());
	}
	
	@Test
	public void test_findSet5() {
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
		tileArray = logic.findSet();
		assertEquals(null, tileArray);
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
		count = logic.checkInitialSum();
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

		count = logic.checkInitialSum();
		assertEquals(36,count);
	}
	
	@Test
	public void test_findMeldOnTable() throws AbleToAddBothSideException {
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
		tile2play = logic.findMeldsOnTable();
		assertEquals(1, tile2play.size());
		assertEquals("{B4=1}", tile2play.toString());
		hand.remove(0);
		meldList.get(1).addTail(atile10);
		tile2play = logic.findMeldsOnTable();
		assertEquals(1, tile2play.size());
		assertEquals("{B4=1}", tile2play.toString());
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
		tile2play = logic.findRunMove(melda);
		assertEquals("{[R5, R6]=3}", tile2play.toString());	//move 0 3
//[JK, G4, R4,]
//B4, B5, R5, R6	
		//System.out.println(meldb);
		tile2play = logic.findRunMove(meldb);
		//assertEquals(null, tile2play);
		//System.out.println(meldb);
		meldb.addTail(atile10);//b4
//[JK, G4, R4, B4]
//B4, B5, R5, R6
		hand.remove(3);
		hand.remove(2);
		tile2play = logic.findRunMove(meldb);
		assertEquals(null, tile2play);
		
		
	}
	
	@Test
	public void test_findMoveRun2() throws AbleToAddBothSideException {
		//test find, and play
		HashMap<ArrayList<Tile>,Integer> tile2play = new HashMap<ArrayList<Tile>,Integer>();
		try {
			atile1 = new Tile("G2");
			atile2 = new Tile("G3");
			atile3 = new Tile("G4");
			atile4 = new Tile("R4");
			atile5 = new Tile("B4");
			atile6 = new Tile("O4");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		melda = new Meld();
		melda.add(atile3);
		melda.add(atile4);
		melda.add(atile5);
		melda.add(atile6);
		meldList.add(melda);
		assertEquals(1, meldList.size());
		hand.add(atile1);	//r6
		hand.add(atile2);//r5
		
		tile2play = logic.findRunMove(melda);

		assertEquals("{[G2, G3]=0}", tile2play.toString());	
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
		tile2play = logic.findSetMove(melda);
//[[G1, O1, B1, R1], [R1, O1, JK]]
		assertEquals("{[O1, B1]=0}", tile2play.toString());
//[JK, G4, R4,]
//B4, B5, R5, R6		
		tile2play = logic.findSetMove(meldb);
		assertEquals(null, tile2play);
		meldb.addTail(atile4);//b4
//[R1, O1, JK, G1]
//B4, B5, R5, R6
		tile2play = logic.findSetMove(meldb);
		assertEquals("{[O1, B1]=3}", tile2play.toString());
		hand.remove(0);
		hand.remove(0);
		hand.add(atile6);	//o1
		hand.add(atile4);//G1
		tile2play = logic.findSetMove(meldb);
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
		tile2play = logic.findSetMove(meldc);
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
		tile2play = logic.findSetMove(meldc);
		assertEquals("{[G2, B2]=0}", tile2play.toString());

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
		logic = new AILogic(hand,meldList);
		canPlay = logic.canPlayAll();
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
			atile12 = new Tile("G9");
			atile13 = new Tile("B5");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
			btile1 = new Tile("G10");
			btile2 = new Tile("B9");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		Meld meldc;
		Meld meldd;
		melda = new Meld();
		meldb = new Meld();
		meldc = new Meld();
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
		//hand.add(atile6);//R8*
		hand.add(atile7);//O4
		hand.add(atile8);//R4 
		hand.add(atile9);//B6
		hand.add(atile10);//B4
		//hand.add(atile11);//R6
		hand.add(atile12);//G7*
		hand.add(atile13);//B5
		//hand.add(aJoker);//J
		canPlay = logic.canPlayAll();
//[B4, B5, B6], [R4, JK, R6], {O4=1} = 7
		assertFalse(canPlay);
//B4, B5, B6, R4, R6, R8, G7, O4, JK
		//hand.remove(5);
		//hand.remove(5);
//B4, B5, B6, R4, R6, O4, JK
//[[R1, R2, R3], [R4, G4, JK]]
		logic = new AILogic(hand,meldList);
		canPlay = logic.canPlayAll();
		System.out.println(meldList);
		assertFalse(canPlay);
		//hand.add(btile1);
		hand.add(btile2);
		meldc.add(atile10);
		meldc.add(atile13);
		meldc.addTail(aJoker);
		meldc.addTail(bJoker);
		meldList.add(meldc);
		System.out.println("-----");
		System.out.println(meldList);
		System.out.println(hand);
		logic = new AILogic(hand,meldList);
		canPlay = logic.canPlayAll();
		assertTrue(canPlay);
		System.out.println(hand);
		System.out.println(meldList);
		
	}
	
	@Test
	public void test_canPlayAll3() throws AbleToAddBothSideException {
		HashMap<Tile,Integer> tile2play = new HashMap<Tile,Integer>();
		boolean canPlay = false;
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("R6");
			atile7 = new Tile("R7");
			
			btile1 = new Tile("R2");
			btile2 = new Tile("R3");
			btile3 = new Tile("R5");
			btile4 = new Tile("R6");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;

		melda = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		melda.add(atile4);
		melda.add(atile5);
		melda.add(atile6);
		melda.add(atile7);
		meldList.add(melda);
//[[R1, R2, R3], [R4, G4, JK]]
		assertEquals(1, meldList.size());
		//hand.add(atile6);//R8*
		hand.add(btile1);//O4
		hand.add(btile2);//R4 
		logic = new AILogic(hand,meldList);
		
		canPlay = logic.canPlayAll();
		assertTrue(canPlay);
		hand.add(btile3);//O4
		hand.add(btile4);//R4 
		logic = new AILogic(hand,meldList);
		System.out.println("-----");
		System.out.println(meldList);
		System.out.println(hand);
		canPlay = logic.canPlayAll();
		System.out.println(meldList);
		System.out.println(hand);
		assertTrue(canPlay);
	}
	
	
	@Test
	public void test_canPlayAll4() throws AbleToAddBothSideException {
		HashMap<Tile,Integer> tile2play = new HashMap<Tile,Integer>();
		boolean canPlay = false;
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile();
			atile5 = new Tile("R5");
			atile6 = new Tile("R6");
			atile7 = new Tile("R7");
			
			btile1 = new Tile("R2");
			btile2 = new Tile("R3");
			btile3 = new Tile("R5");
			btile4 = new Tile("R4");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;

		melda = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		melda.add(atile4);
		melda.add(atile5);
		melda.add(atile6);
		melda.add(atile7);
		meldList.add(melda);
//[[R1, R2, R3], [R4, G4, JK]]
		assertEquals(1, meldList.size());
		//hand.add(atile6);//R8*
		hand.add(btile4);//R4 
		logic = new AILogic(hand,meldList);
		System.out.println(meldList);
		System.out.println(hand);
		canPlay = logic.canPlayAll();
		assertTrue(canPlay);
	}
	
	@Test
	public void test_findCutRun() throws AbleToAddBothSideException {
		//test two tile
		HashMap<ArrayList<Tile>,Integer> tile2play = new HashMap<ArrayList<Tile>,Integer>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
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
		meldList.add(melda);
		assertEquals(1, meldList.size());
		hand.add(atile2);	
		hand.add(atile3);
		tile2play = logic.findRunCut(melda);
		assertEquals("{[R2, R3]=0}", tile2play.toString());	//move 0 3
		hand.remove(1);
		hand.remove(0);
		hand.add(atile4);
		hand.add(atile5);
		tile2play = logic.findRunCut(melda);
		assertEquals("{[R4, R5]=2}", tile2play.toString());	//move 0 3
	}
	
	@Test
	public void test_findCutRun2() throws AbleToAddBothSideException {
		//test find, and play
		HashMap<ArrayList<Tile>,Integer> tile2play = new HashMap<ArrayList<Tile>,Integer>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("Joker");
			atile7 = new Tile("R7");
			atile8 = new Tile("R8");
			atile9 = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		melda = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		melda.add(atile4);
		melda.add(atile5);
		melda.add(atile6);
		meldList.add(melda);
		assertEquals(1, meldList.size());
		hand.add(atile3);
		tile2play = logic.findRunCut(melda);
		assertEquals("{[R3]=1}", tile2play.toString());	//move 0 3
		melda.add(atile7);
		melda.add(atile8);
		melda.add(atile9);
		hand.remove(0);
		hand.add(atile7);
		tile2play = logic.findRunCut(melda);
		assertEquals("{[R7]=5}", tile2play.toString());	//move 0 3
	}
	
	@Test
	public void test_replace() throws AbleToAddBothSideException {
		//test replace, run
		HashMap<Tile,Integer> tile2play = new HashMap<Tile,Integer>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("Joker");
			atile7 = new Tile("R6");
			atile8 = new Tile("R8");
			atile9 = new Tile("Joker");
			atile10 = new Tile("Joker");
			atile11 = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		melda = new Meld();
		melda.add(atile6);
		melda.addTail(atile2);
		melda.addTail(atile11);
		melda.addTail(atile10);
		melda.add(atile5);
		melda.add(atile9);
		meldList.add(melda);
		assertEquals(1, meldList.size());
		hand.add(atile7);
		tile2play = logic.findReplace(melda);
		assertEquals("{R6=5}", tile2play.toString());	
		hand.remove(0);
		hand.add(atile1);
		tile2play = logic.findReplace(melda);
		assertEquals("{R1=0}", tile2play.toString());	
		hand.remove(0);
		hand.add(atile3);
		tile2play = logic.findReplace(melda);
		assertEquals("{R3=2}", tile2play.toString());	
		hand.remove(0);
		hand.add(atile4);
		tile2play = logic.findReplace(melda);
		assertEquals("{R4=3}", tile2play.toString());	
	}
	
	@Test
	public void test_replace2() throws AbleToAddBothSideException {
		//test replace, set
		HashMap<Tile,Integer> tile2play = new HashMap<Tile,Integer>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("O1");
			atile3 = new Tile("B1");
			atile4 = new Tile("G1");
			atile5 = new Tile("R5");
			atile6 = new Tile("Joker");
			atile7 = new Tile("R6");
			atile8 = new Tile("R8");
			atile9 = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		Meld meldc;
		Meld meldd;
		melda = new Meld();
		meldb = new Meld();
		meldc = new Meld();
		meldd= new Meld();
		melda.add(atile6);
		melda.addTail(atile2);
		melda.add(atile3);
		melda.add(atile4);
		hand.add(atile1);
		hand.add(atile2);
		hand.add(atile6);
		tile2play = logic.findReplace(melda);
		assertEquals("{R1=1}", tile2play.toString());	
		hand.remove(0);
		meldb.add(atile1);
		meldb.add(atile2);
		meldb.add(atile3);
		meldb.add(atile6);
		hand.add(atile1);
		hand.add(atile4);
		hand.add(atile6);
		tile2play = logic.findReplace(meldb);
		assertEquals("{G1=3}", tile2play.toString());	
		meldc.add(atile1);
		meldc.addTail(atile9);
		meldc.addTail(atile6);
		meldc.add(atile4);
		hand.remove(0);
		hand.add(atile2);
		tile2play = logic.findReplace(meldc);
		assertEquals("{O1=1}", tile2play.toString());	
		hand.remove(0);
		hand.add(atile3);
		tile2play = logic.findReplace(meldc);
		assertEquals("{B1=1}", tile2play.toString());	
		meldd.add(atile1);
		meldd.addTail(atile2);
		meldd.addTail(atile6);
		meldd.add(atile4);
		tile2play = logic.findReplace(meldd);
		assertEquals("{B1=2}", tile2play.toString());	
	}
	
	@Test
	public void test_hasMapfromTable() throws AbleToAddBothSideException {
		//test replace, set
		boolean countMapfromHand;
		ArrayList<Tile> tile2play = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("R6");
			atile7 = new Tile("R7");
			atile8 = new Tile("R8");
			atile9 = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		meldb.add(atile7);
		meldb.add(atile8);
		meldb.addTail(atile9);
		meldList.add(melda);
		meldList.add(meldb);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		tile2play.add(atile4);
		tile2play.add(atile5);
		tile2play.add(atile6);
		System.out.println(tile2play);
		System.out.println(meldList);
		countMapfromHand = logic.hasMapfromTable(tile2play, meldList);
		assertEquals(true, countMapfromHand);	
	}
	
	@Test
	public void test_hasMapfromTable2() throws AbleToAddBothSideException {
		//test replace, set
		boolean countMapfromHand;
		ArrayList<Tile> tile2play = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("R6");
			atile7 = new Tile("R7");
			atile8 = new Tile("R8");
			atile9 = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		meldb.add(atile7);
		meldb.add(atile8);
		meldb.addHead(atile9);
		meldList.add(melda);
		meldList.add(meldb);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		tile2play.add(atile4);
		tile2play.add(atile5);
		tile2play.add(atile6);
		System.out.println(tile2play);
		System.out.println(meldList);
		countMapfromHand = logic.hasMapfromTable(tile2play, meldList);
		assertEquals(true, countMapfromHand);	
	}
	
	@Test
	public void test_hasMapfromTable3() throws AbleToAddBothSideException {
		//test replace, set
		boolean countMapfromHand;
		ArrayList<Tile> tile2play = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("R6");
			atile7 = new Tile("R7");
			atile8 = new Tile("R8");
			atile9 = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		meldb.add(atile7);
		meldb.add(atile8);
		meldb.addHead(atile9);
		meldList.add(melda);
		meldList.add(meldb);
		hand.add(atile9);
		hand.add(atile5);
		hand.add(atile6);
		tile2play.add(atile9);
		tile2play.add(atile5);
		tile2play.add(atile6);
		System.out.println(tile2play);
		System.out.println(meldList);
		countMapfromHand = logic.hasMapfromTable(tile2play, meldList);
		assertEquals(true, countMapfromHand);	
	}
	
	@Test
	public void test_hasMapfromTable4() throws AbleToAddBothSideException {
		//test replace, set
		boolean countMapfromHand;
		ArrayList<Tile> tile2play = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("R6");
			atile7 = new Tile("R7");
			atile8 = new Tile("R8");
			atile9 = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		meldb.add(atile7);
		meldb.add(atile8);
		meldb.addHead(atile9);
		meldList.add(melda);
		meldList.add(meldb);
		hand.add(atile9);
		hand.add(atile5);
		hand.add(atile6);
		tile2play.add(atile4);
		tile2play.add(atile5);
		tile2play.add(atile9);
		System.out.println(tile2play);
		System.out.println(meldList);
		countMapfromHand = logic.hasMapfromTable(tile2play, meldList);
		assertEquals(true, countMapfromHand);	
	}
	
	@Test
	public void test_hasMapfromTable5() throws AbleToAddBothSideException {
		//test replace, set
		boolean countMapfromHand;
		ArrayList<Tile> tile2play = new ArrayList<Tile>();
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
			aJoker = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		meldb.add(atile6);
		meldb.addTail(aJoker);
		meldb.add(atile8);
		meldList.add(melda);
		meldList.add(meldb);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		tile2play.add(atile4);
		tile2play.add(atile5);
		tile2play.add(atile6);
		System.out.println(tile2play);
		System.out.println(meldList);
		countMapfromHand = logic.hasMapfromTable(tile2play, meldList);
		assertEquals(true, countMapfromHand);	
	}
	
	@Test
	public void test_hasMapfromTable6() throws AbleToAddBothSideException {
		//test replace, set
		boolean countMapfromHand;
		ArrayList<Tile> tile2play = new ArrayList<Tile>();
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
			aJoker = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		meldb.add(atile6);
		meldb.addTail(aJoker);
		meldb.add(atile8);
		meldList.add(melda);
		meldList.add(meldb);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		tile2play.add(atile4);
		tile2play.add(atile5);
		tile2play.add(aJoker);
		System.out.println(tile2play);
		System.out.println(meldList);
		countMapfromHand = logic.hasMapfromTable(tile2play, meldList);
		assertEquals(true, countMapfromHand);	
	}
	
	@Test
	public void test_hasMapfromTable7() throws AbleToAddBothSideException {
		//test replace, set
		boolean countMapfromHand;
		ArrayList<Tile> tile2play = new ArrayList<Tile>();
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
			aJoker = new Tile("Joker");
		}catch(InvalidTileException e) {
			fail();
		}
		Meld melda;
		Meld meldb;
		melda = new Meld();
		meldb = new Meld();
		melda.add(atile1);
		melda.add(atile2);
		melda.add(atile3);
		meldb.add(atile6);
		meldb.addTail(aJoker);
		meldb.add(atile8);
		meldList.add(melda);
		meldList.add(meldb);
		hand.add(atile4);
		hand.add(atile5);
		hand.add(atile6);
		tile2play.add(aJoker);
		tile2play.add(atile5);
		tile2play.add(atile6);
		System.out.println(tile2play);
		System.out.println(meldList);
		countMapfromHand = logic.hasMapfromTable(tile2play, meldList);
		assertEquals(true, countMapfromHand);	
	}
}
