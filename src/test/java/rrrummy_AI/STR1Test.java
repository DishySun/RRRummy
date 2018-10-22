package rrrummy_AI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rrrummy.InvalidTileException;
import rrrummy.Table;
import rrrummy.Tile;

public class STR1Test {
	private AI ai;
	private AIstrategy aISty;
	private GameData data;
	private Table table;
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
		ai = new AI("Hunter");
		data = new GameData();
		aISty = new STR1(data);
		table = new Table();
		ai.setSTY(aISty);
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
	public void test_playInitialA() {
		ai.draw(atile1);
		ai.draw(atile2);
		ai.draw(atile3);
		ai.draw(atile4);
		ai.draw(atile5);
		ai.draw(atile6);
		ai.draw(atile7);
		ai.draw(atile8);
		ai.draw(atile9);
		ai.draw(atile10);
		ai.draw(atile11);
		ai.draw(atile12);
		ai.draw(atile13);
		ai.draw(aJoker);
		ai.getSTY().playInitial(null);
		assertEquals(4, ai.handSize());
	}

	public void test_playInitialB() {
		ai.draw(btile1);
		ai.draw(btile2);
		ai.draw(btile3);
		ai.draw(btile4);
		ai.draw(btile5);
		ai.draw(btile6);
		ai.draw(btile7);
		ai.draw(btile8);
		ai.draw(btile9);
		ai.draw(btile10);
		ai.draw(btile11);
		ai.draw(btile12);
		ai.draw(btile13);
		ai.draw(bJoker);
		ai.getSTY().playInitial(table);
		assertEquals(14, ai.handSize());
	}
}
