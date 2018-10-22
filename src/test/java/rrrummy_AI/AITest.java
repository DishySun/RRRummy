package rrrummy_AI;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rrrummy.InvalidTileException;
import rrrummy.Tile;
import rrrummy_AI.STR1;

public class AITest {
	private AI testAI;
	private AIstrategy AISty;
	private GameData data;
	private Tile atile1;
	private Tile atile2;
	private Tile atile3;
	private Tile atile4;
	private Tile atile5;
	private Tile atile6;
	private Tile atile7;
	private Tile atile8;
	private Tile atile9;
	private Tile atile10;
	private Tile atile11;
	private Tile atile12;
	private Tile atile13;
	private Tile aJoker;
	private Tile btile1;
	private Tile btile2;
	private Tile btile3;
	private Tile btile4;
	private Tile btile5;
	private Tile btile6;
	private Tile btile7;
	private Tile btile8;
	private Tile btile9;
	private Tile btile10;
	private Tile btile11;
	private Tile btile12;
	private Tile btile13;
	private Tile bJoker;
	@Before
	public void setUp() throws Exception {
		testAI = new AI("Hunter");
		data = new GameData();
		
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
	public void test_findRun() {
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
	public void test_findRun2() {
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
		assertEquals(Tile.Color.BLUE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(2).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(3).getColor());
		assertEquals(Tile.Color.BLUE, tileArray.get(4).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(0).getColor());
		assertEquals(10, tileArray.get(1).getNumber());
		assertEquals(11, tileArray.get(2).getNumber());
		assertEquals(12, tileArray.get(3).getNumber());
		assertEquals(13, tileArray.get(4).getNumber());
	}
	
	@Test
	public void test_findGroup() {
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
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.ORANGE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(6, tileArray.get(0).getNumber());
		assertEquals(6, tileArray.get(1).getNumber());
		assertEquals(6, tileArray.get(2).getNumber());
	}
	
	@Test
	public void test_findGroup2() {
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
		tileArray = testAI.findInitGroup();
		assertEquals(Tile.Color.BLUE, tileArray.get(0).getColor());
		assertEquals(Tile.Color.ORANGE, tileArray.get(1).getColor());
		assertEquals(Tile.Color.RED, tileArray.get(2).getColor());
		assertEquals(Tile.Color.JOKER, tileArray.get(3).getColor());
		assertEquals(6, tileArray.get(0).getNumber());
		assertEquals(6, tileArray.get(1).getNumber());
		assertEquals(6, tileArray.get(2).getNumber());
	}
	
	public void test_findRun4null() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
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
		testAI.draw(btile12);
		testAI.draw(btile13);
		testAI.draw(bJoker);
		tileArray = testAI.findInitRun();
		assertEquals(null, tileArray);
	}
	
	public void test_findGroup4null() {
		ArrayList<Tile> tileArray = new ArrayList<Tile>();
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
		testAI.draw(btile12);
		testAI.draw(btile13);
		testAI.draw(bJoker);
		tileArray = testAI.findInitGroup();
		assertEquals(null, tileArray);
	}
	
}
