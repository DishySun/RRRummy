package rrrummy_AI;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import rrrummy.*;
import rrrummy_AI.STR1;

public class AITest {
	private Table table;
	private AI testAI;
	private AIstrategy AISty;
	private GameData data;
	private Tile atile1,atile2,atile3,atile4,atile5,atile6,atile7,atile8,atile9,atile10,atile11,atile12,atile13;
	private Tile btile1,btile2,btile3,btile4,btile5,btile6,btile7,btile8,btile9,btile10,btile11,btile12,btile13;
	private Tile aJoker;
	private Tile bJoker;
	@Before
	public void setUp() throws Exception {
		testAI = new AI("Hunter");
		data = new GameData();
		table = new Table();
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
	public void test_Constructure() {
		testAI = new AI("Tony");
		assertEquals("Tony", testAI.getName());
	}
	
	@Test
	public void test_setSTY() {
		AISty = new STR1(data);
		testAI.setSTY(AISty);
		assertEquals(STR1.class, testAI.getSTY().getClass());
	}
	
	@Test
	public void test_findInitRun0() {
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R8");
			atile9 = new Tile("G9");
			atile10 = new Tile("R10");
			atile11 = new Tile("R11");
			atile12 = new Tile("R12");
			atile13 = new Tile("R13");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();
		assertEquals(Tile.Color.RED, tileArray.get(0).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(4).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(5).getColor());
		assertEquals(8, tileArray.get(0).getNumber());
		assertEquals(0, tileArray.get(1).getNumber());
		assertEquals(10, tileArray.get(2).getNumber());
		assertEquals(11, tileArray.get(3).getNumber());
		assertEquals(12, tileArray.get(4).getNumber());
		assertEquals(13, tileArray.get(5).getNumber());
	}
	
	@Test
	public void test_findInitRun() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		tileArray = testAI.findInitRun();
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(2).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(3).getColor());
		assertEquals(10, tileArray.get(0).getNumber());
		assertEquals(11, tileArray.get(1).getNumber());
		assertEquals(12, tileArray.get(2).getNumber());
		assertEquals(13, tileArray.get(3).getNumber());
	}
	
	@Test
	public void test_findInitRun2() {
		//has joker
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(2).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(2).getColor());
		assertEquals(10, tileArray.get(0).getNumber());
		assertEquals(11, tileArray.get(1).getNumber());
		assertEquals(12, tileArray.get(2).getNumber());
		assertEquals(13, tileArray.get(3).getNumber());
	}
	
	@Test
	public void test_findInitRun3() {
		//has joker
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
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
			atile10 = new Tile("R8");
			atile11 = new Tile("R9");
			atile12 = new Tile("R10");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();
		assertEquals(Tile.Color.RED, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(4).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(5).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(6).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(7).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(8).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(9).getColor());
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(2, tileArray.get(1).getNumber());
		assertEquals(3, tileArray.get(2).getNumber());
		assertEquals(4, tileArray.get(3).getNumber());
		assertEquals(5, tileArray.get(4).getNumber());
		assertEquals(6, tileArray.get(5).getNumber());
		assertEquals(Tile.Color.JOKER, tileArray.get(6).getColor());
		assertEquals(8, tileArray.get(7).getNumber());
		assertEquals(9, tileArray.get(8).getNumber());
		assertEquals(10, tileArray.get(9).getNumber());
	}
	
	
	
	@Test
	public void test_findInitRun4() {
		//has joker
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
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
			atile13 = new Tile("R7");
			atile10 = new Tile("R8");
			atile11 = new Tile("R9");
			atile12 = new Tile("R11");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();
		assertEquals(Tile.Color.RED, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(4).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(5).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(6).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(7).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(8).getColor());
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(2, tileArray.get(1).getNumber());
		assertEquals(3, tileArray.get(2).getNumber());
		assertEquals(4, tileArray.get(3).getNumber());
		assertEquals(5, tileArray.get(4).getNumber());
		assertEquals(6, tileArray.get(5).getNumber());
		assertEquals(Tile.Color.JOKER, tileArray.get(6).getColor());
		assertEquals(8, tileArray.get(7).getNumber());
		assertEquals(9, tileArray.get(8).getNumber());
	}
	
	@Test //two parts >= 30, only take one part
	public void test_findInitRun5() {
		//has joker
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("O5");
			atile6 = new Tile("R6");
			atile7 = new Tile("O6");
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
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();
		assertEquals(Tile.Color.RED, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(4).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(6).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(7).getColor());
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(2, tileArray.get(1).getNumber());
		assertEquals(3, tileArray.get(2).getNumber());
		assertEquals(4, tileArray.get(3).getNumber());
		assertEquals(Tile.Color.JOKER, tileArray.get(4).getColor());
		assertEquals(6, tileArray.get(5).getNumber());
		assertEquals(7, tileArray.get(6).getNumber());
		assertEquals(8, tileArray.get(7).getNumber());
	}
	
	@Test //two parts >= 30, only take one part
	public void test_findInitRun6() {
		//has joker
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
			atile13 = new Tile("B12");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		testAI.draw(bJoker);
		tileArray = testAI.findInitRun();
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(3).getColor());
		assertEquals(9, tileArray.get(0).getNumber());
		assertEquals(12, tileArray.get(3).getNumber());
	}
	
	@Test
	public void test_findInitRun7() {
		//has joker
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("B5");
			atile6 = new Tile("B6");
			atile7 = new Tile("O6");
			atile8 = new Tile("R6");
			atile9 = new Tile("G5");
			atile13 = new Tile("R7");
			atile10 = new Tile("O8");
			atile11 = new Tile("R1");
			atile12 = new Tile("R12");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();

		assertEquals(Tile.Color.JOKER, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());

		assertEquals(0, tileArray.get(0).getNumber());
		assertEquals(12, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
	
	}
	
	@Test
	public void test_findInitRun8() {
		//has joker
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("B5");
			atile6 = new Tile("B6");
			atile7 = new Tile("O6");
			atile8 = new Tile("R6");
			atile9 = new Tile("G5");
			atile13 = new Tile("R7");
			atile10 = new Tile("O8");
			atile11 = new Tile("R1");
			atile12 = new Tile("R13");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();

		assertEquals(Tile.Color.JOKER, tileArray.get(0).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(0, tileArray.get(0).getNumber());
		assertEquals(0, tileArray.get(1).getNumber());
		assertEquals(13, tileArray.get(2).getNumber());
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
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		tileArray = testAI.findInitGroup();
		assertEquals(Tile.Color.ORANGE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.GREEN, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(9, tileArray.get(0).getNumber());
		assertEquals(9, tileArray.get(1).getNumber());
		assertEquals(9, tileArray.get(2).getNumber());
		assertEquals(9, tileArray.get(3).getNumber());
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
			atile8 = new Tile("R6");
			atile9 = new Tile("G9");
			atile10 = new Tile("B9");
			atile11 = new Tile("B11");
			atile12 = new Tile("O1");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		tileArray = testAI.findInitGroup();
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.GREEN, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(3).getColor());
		assertEquals(9, tileArray.get(0).getNumber());
		assertEquals(9, tileArray.get(1).getNumber());
		assertEquals(9, tileArray.get(2).getNumber());
		assertEquals(0, tileArray.get(3).getNumber());
	}
	
	
	@Test
	public void test_findGroup3() {
		//has 2 joker
		//B R last to first
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R9");
			atile6 = new Tile("B6");
			atile7 = new Tile("O6");
			atile8 = new Tile("R6");
			atile9 = new Tile("G1");
			atile10 = new Tile("B9");
			atile11 = new Tile("B11");
			atile12 = new Tile("J");
			atile13 = new Tile("B13");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		tileArray = testAI.findInitGroup();
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(3).getColor());
		assertEquals(9, tileArray.get(0).getNumber());
		assertEquals(9, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
		assertEquals(0, tileArray.get(3).getNumber());
	}
	
	@Test
	public void test_findInitRun4null() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("B4");
			atile5 = new Tile("R6");
			atile6 = new Tile("B1");
			atile7 = new Tile("O6");
			atile8 = new Tile("R6");
			atile9 = new Tile("G1");
			atile10 = new Tile("B3");
			atile11 = new Tile("B4");
			atile12 = new Tile("J");
			atile13 = new Tile("O1");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();
		assertTrue(tileArray.isEmpty());
	}
	
	
	
	@Test
	public void test_findGroup4null() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
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
			btile10 = new Tile("O1");
			btile11 = new Tile("G2");
			bJoker = new Tile("J");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(btile1);
		testAI.draw(btile2);
		testAI.draw(btile3);
		testAI.draw(btile4);
		testAI.draw(btile5);
		testAI.draw(btile6);
		testAI.draw(btile7);
		testAI.draw(btile8);
		testAI.draw(btile9);
		testAI.draw(btile10);
		testAI.draw(btile11);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findInitGroup();
		assertTrue(tileArray.isEmpty());
	}
	
	@Test
	public void test_checkSum1(){
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		tileArray.add(btile11);
		tileArray.add(btile12);
		tileArray.add(aJoker);
		int i = testAI.checkSum(tileArray);
		assertEquals(36, i);
	}
	
	@Test
	public void test_checkSum2(){
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		tileArray.add(aJoker);
		tileArray.add(btile11);
		tileArray.add(btile12);
		int i = testAI.checkSum(tileArray);
		assertEquals(33, i);
	}
	
	@Test
	public void test_checkSum3(){
		ArrayList<Tile> tileArray = new ArrayList<Tile>();		
		tileArray.clear();
		tileArray.add(aJoker);
		tileArray.add(btile11);
		tileArray.add(btile12);
		tileArray.add(bJoker);
		int i = testAI.checkSum(tileArray);
		assertEquals(46, i);
	}
	
	@Test
	public void test_checkSum4(){
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		tileArray.clear();
		tileArray.add(btile10);
		tileArray.add(aJoker);
		tileArray.add(bJoker);
		tileArray.add(btile13);
		int i = testAI.checkSum(tileArray);
		assertEquals(46, i);
	}
	
	@Test
	public void test_checkSum5(){
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		tileArray.clear();
		tileArray.add(aJoker);
		tileArray.add(bJoker);
		tileArray.add(btile12);
		tileArray.add(btile13);
		int i = testAI.checkSum(tileArray);
		assertEquals(46, i);
	}
	
	@Test
	public void test_checkSum6(){
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		tileArray.clear();
		tileArray.add(btile6);
		tileArray.add(aJoker);
		tileArray.add(bJoker);
		int i = testAI.checkSum(tileArray);
		assertEquals(21, i);
	}
	
	@Test
	public void test_hasSameColor1(){
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		tileArray.clear();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("O2");
			atile3 = new Tile("G3");
			atile4 = new Tile("J");
			atile5 = new Tile("B9");
			atile6 = new Tile("R9");
		}catch(InvalidTileException e) {
			fail();
		}
		tileArray.add(atile1);
		tileArray.add(atile2);
		tileArray.add(atile3);
		tileArray.add(atile4);
		tileArray.add(atile5);
		boolean i = testAI.hasSameColor(tileArray,atile6);
		assertTrue(i);
	}

	@Test
	public void test_hasSameColor2(){
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		tileArray.clear();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("O1");
			atile3 = new Tile("G1");
			atile4 = new Tile("J");
			atile5 = new Tile("B9");
		}catch(InvalidTileException e) {
			fail();
		}
		tileArray.add(atile1);
		tileArray.add(atile2);
		tileArray.add(atile3);
		tileArray.add(atile4);
		boolean i = testAI.hasSameColor(tileArray,atile5);
		assertFalse(i);
	}
	
	@Test
	public void test_play1() {
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("O1");
			atile3 = new Tile("G1");
			atile4 = new Tile("J");
			atile5 = new Tile("B9");
			atile6 = new Tile("B9");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		Meld meld = new Meld();
		try {
			meld.add(atile1);
			meld.add(atile2);
			meld.add(atile3);
		} catch (AbleToAddBothSideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("[R1, O1, G1]", meld.toString());
		testAI.playMeld(meld);
		assertEquals(0, testAI.handSize());
		
	}
	
	@Test
	public void test_findRun1noTable() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("B5");
			atile6 = new Tile("B6");
			atile7 = new Tile("O6");
			atile8 = new Tile("R6");
			atile9 = new Tile("G5");
			atile13 = new Tile("R7");
			atile10 = new Tile("O8");
			atile11 = new Tile("R1");
			atile12 = new Tile("R12");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findRun();

		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(4).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(5).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(6).getColor());
		
		assertEquals(5, tileArray.get(0).getNumber());
		assertEquals(6, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
		assertEquals(1, tileArray.get(3).getNumber());
		assertEquals(2, tileArray.get(4).getNumber());
		assertEquals(3, tileArray.get(5).getNumber());
		assertEquals(4, tileArray.get(6).getNumber());
	}
	
	@Test
	public void test_findGroup1noTable() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R1");
			atile4 = new Tile("R4");
			atile5 = new Tile("B2");
			atile6 = new Tile("B1");
			atile7 = new Tile("O6");
			atile8 = new Tile("R6");
			atile9 = new Tile("G6");
			atile13 = new Tile("R7");
			atile10 = new Tile("O8");
			atile11 = new Tile("R2");
			atile12 = new Tile("R12");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findGroup();

		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(4).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(5).getColor());
		assertEquals(Tile.Color.GREEN, tileArray.get(6).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(7).getColor());
		assertEquals(Tile.Color.ORANGE, tileArray.get(8).getColor());
		
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(1, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
		assertEquals(2, tileArray.get(3).getNumber());
		assertEquals(2, tileArray.get(4).getNumber());
		assertEquals(0, tileArray.get(5).getNumber());
		assertEquals(6, tileArray.get(6).getNumber());
		assertEquals(6, tileArray.get(7).getNumber());
		assertEquals(6, tileArray.get(8).getNumber());
	}
	
	@Test
	public void test_findComb30() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R1");
			atile4 = new Tile("R4");
			atile5 = new Tile("B2");
			atile6 = new Tile("B1");
			atile7 = new Tile("O4");
			atile8 = new Tile("R4");
			atile9 = new Tile("G4");
			atile13 = new Tile("R5");
			atile10 = new Tile("O4");
			atile11 = new Tile("R2");
			atile12 = new Tile("R3");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
			//sorted: J J R1 R1 B1 R2 R2 B2 R3 O4 O4 O4 R4 R4
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findComb30();
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(4).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(5).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(6).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(7).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(8).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(9).getColor());
		assertEquals(Tile.Color.GREEN, tileArray.get(10).getColor());
		assertEquals(Tile.Color.ORANGE, tileArray.get(11).getColor());
		
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(2, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
		assertEquals(1, tileArray.get(3).getNumber());
		assertEquals(2, tileArray.get(4).getNumber());
		assertEquals(0, tileArray.get(5).getNumber());
		assertEquals(2, tileArray.get(6).getNumber());
		assertEquals(3, tileArray.get(7).getNumber());
		assertEquals(4, tileArray.get(8).getNumber());
		assertEquals(4, tileArray.get(9).getNumber());
		assertEquals(4, tileArray.get(10).getNumber());
		assertEquals(4, tileArray.get(11).getNumber());
	}
	
	@Test
	public void test_findCombAll() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R1");
			atile4 = new Tile("R4");
			atile5 = new Tile("B2");
			atile6 = new Tile("B1");
			atile7 = new Tile("O4");
			atile8 = new Tile("R4");
			atile9 = new Tile("G4");
			atile13 = new Tile("R5");
			atile10 = new Tile("O4");
			atile11 = new Tile("R2");
			atile12 = new Tile("R3");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
			//sorted: J J R1 R1 B1 R2 R2 B2 R3 O4 O4 O4 R4 R4
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findCombAll();
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(4).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(5).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(6).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(7).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(8).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(9).getColor());
		assertEquals(Tile.Color.GREEN, tileArray.get(10).getColor());
		assertEquals(Tile.Color.ORANGE, tileArray.get(11).getColor());
		
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(2, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
		assertEquals(1, tileArray.get(3).getNumber());
		assertEquals(2, tileArray.get(4).getNumber());
		assertEquals(0, tileArray.get(5).getNumber());
		assertEquals(2, tileArray.get(6).getNumber());
		assertEquals(3, tileArray.get(7).getNumber());
		assertEquals(4, tileArray.get(8).getNumber());
		assertEquals(4, tileArray.get(9).getNumber());
		assertEquals(4, tileArray.get(10).getNumber());
		assertEquals(4, tileArray.get(11).getNumber());
	}
	
	@Test
	public void test_findMeldOnTable() throws AbleToAddBothSideException {
		HashMap<Tile,Integer> tile2play = new HashMap<Tile,Integer>();
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		
		
		try {
			atile1 = new Tile("R2");
			atile2 = new Tile("R3");
			atile3 = new Tile("R4");
			atile4 = new Tile("B8");
			atile5 = new Tile("B1");
			atile6 = new Tile("O1");
			atile7 = new Tile("R1");
			atile8 = new Tile("B3");
			atile9 = new Tile("R5");
			atile13 = new Tile("R1");
			atile10 = new Tile("B7");
			atile11 = new Tile("R6");
			atile12 = new Tile("G1");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		
		Meld meld1 = new Meld();
		Meld meld2 = new Meld();
		meld1.add(atile1);
		meld1.add(atile2);
		meld1.add(atile3);
		meld1.add(atile4);
		meld2.add(atile5);
		meld2.add(atile6);
		meld2.add(atile7);
		
		table.add(meld1);
		table.add(meld2);
		assertEquals(2, table.size());
		//R2 R3 R4
		//B1 O1 R1
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		//B3 B7 R5 R6 G1 Jk JK
		tile2play = testAI.findMeldsOnTable(table);
		//R5 R6 Jk JK
		//G1
		assertEquals(5, tile2play.size());
		assertTrue(tile2play.containsValue(0));
		assertTrue(tile2play.containsValue(1));
		assertEquals(0, tile2play.get(testAI.getHand(2)).intValue());
		assertEquals(0, tile2play.get(testAI.getHand(3)).intValue());
		assertEquals(1, tile2play.get(testAI.getHand(4)).intValue());
		assertEquals(0, tile2play.get(testAI.getHand(5)).intValue());
		assertEquals(0, tile2play.get(testAI.getHand(6)).intValue());
	}
	
	@Test
	public void test_ArrayList2MeldList() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R1");
			atile4 = new Tile("R4");
			atile5 = new Tile("B2");
			atile6 = new Tile("B1");
			atile7 = new Tile("O4");
			atile8 = new Tile("R4");
			atile9 = new Tile("G4");
			atile13 = new Tile("R5");
			atile10 = new Tile("O4");
			atile11 = new Tile("R2");
			atile12 = new Tile("R3");
			aJoker = new Tile("J");
			bJoker = new Tile("J");
			//sorted: J J R1 R1 B1 R2 R2 B2 R3 O4 O4 O4 R4 R4
		}catch(InvalidTileException e) {
			fail();
		}
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(bJoker);
		testAI.draw(aJoker);
		tileArray = testAI.findComb30();
		//[B1, B2, jk, R1, R2, jk, R2, R3, R4, R4, G4, O4]
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(2).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(3).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(4).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(5).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(6).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(7).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(8).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(9).getColor());
		assertEquals(Tile.Color.GREEN, tileArray.get(10).getColor());
		assertEquals(Tile.Color.ORANGE, tileArray.get(11).getColor());
		
		assertEquals(1, tileArray.get(0).getNumber());
		assertEquals(2, tileArray.get(1).getNumber());
		assertEquals(0, tileArray.get(2).getNumber());
		assertEquals(1, tileArray.get(3).getNumber());
		assertEquals(2, tileArray.get(4).getNumber());
		assertEquals(0, tileArray.get(5).getNumber());
		assertEquals(2, tileArray.get(6).getNumber());
		assertEquals(3, tileArray.get(7).getNumber());
		assertEquals(4, tileArray.get(8).getNumber());
		assertEquals(4, tileArray.get(9).getNumber());
		assertEquals(4, tileArray.get(10).getNumber());
		assertEquals(4, tileArray.get(11).getNumber());
		
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		meldList = testAI.arrayList2MeldList(tileArray);
		
		assertEquals("[B1, B2, JK]", meldList.get(0).toString());
		assertEquals("[R1, R2, JK]", meldList.get(1).toString());
		assertEquals("[R2, R3, R4]", meldList.get(2).toString());
		assertEquals("[R4, G4, O4]", meldList.get(3).toString());
	}
	
	@Test
	public void test_ArrayList2MeldList2() {
		try {
			atile1 = new Tile("R1");
			atile2 = new Tile("R2");
			atile3 = new Tile("R3");
			atile4 = new Tile("R4");
			atile5 = new Tile("R5");
			atile6 = new Tile("B6");
			atile7 = new Tile("O7");
			atile8 = new Tile("R8");
			atile9 = new Tile("G9");
			atile10 = new Tile("R10");
			atile11 = new Tile("R11");
			atile12 = new Tile("R12");
			atile13 = new Tile("R13");
			aJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
		testAI.draw(atile1);
		testAI.draw(atile2);
		testAI.draw(atile3);
		testAI.draw(atile4);
		testAI.draw(atile5);
		testAI.draw(atile6);
		testAI.draw(atile7);
		testAI.draw(atile8);
		testAI.draw(atile9);
		testAI.draw(atile10);
		testAI.draw(atile11);
		testAI.draw(atile12);
		testAI.draw(atile13);
		testAI.draw(aJoker);
		tileArray = testAI.findInitRun();
		//R8 JK R10 ....R13
		ArrayList<Meld> meldList = new ArrayList<Meld>();
		meldList = testAI.arrayList2MeldList(tileArray);
		
		assertEquals("[R8, JK, R10, R11, R12, R13]", meldList.get(0).toString());
	}
	
}
