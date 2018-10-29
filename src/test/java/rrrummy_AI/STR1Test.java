package rrrummy_AI;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rrrummy.InvalidTileException;
import rrrummy.Player;
import rrrummy.Table;
import rrrummy.Tile;

public class STR1Test {
	private Tile atile1,atile2,atile3,atile4,atile5,atile6,atile7,atile8,atile9,atile10,atile11,atile12,atile13;
	private Tile btile1,btile2,btile3,btile4,btile5,btile6,btile7,btile8,btile9,btile10,btile11,btile12,btile13;
	private Tile aJoker;
	private Tile bJoker;
	private AI ai;
	private AIstrategy aISty;
	private GameData data;
	private Table table;
	private ArrayList<Player> players;
	@Before
	public void setUp() throws Exception {
		table = new Table();
		ai = new AI("Hunter");
		data = new GameData();
		aISty = new STR1(data);
		ai.setSTY(aISty);
		players = new ArrayList<Player>();
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
			btile5 = new Tile("R10");
			btile6 = new Tile("B6");
			btile7 = new Tile("O7");
			btile8 = new Tile("R8");
			btile9 = new Tile("G9");
			btile10 = new Tile("O10");
			btile11 = new Tile("G11");
			btile12 = new Tile("G10");
			btile13 = new Tile("B10");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
	}

	@Test
	public void test_playInitial1() {
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
		//R8 J R10 R11 R12 R13
		players.add(ai);
		data.setValue(table, players);
		ai.getSTY().playInitial();
		assertEquals(8, ai.handSize());
		assertEquals(1,table.size());
		assertEquals("[R8, JK, R10, R11, R12, R13]", table.getMeld(0).toString());
	}
	
	@Test
	public void test_playInitial2() {
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
		//O10 G10 R10 B10
		
		players.add(ai);
		data.setValue(table, players);
		ai.getSTY().playInitial();
		assertEquals(10, ai.handSize());
		assertEquals(1,table.size());
		assertEquals("[O10, G10, R10, B10]", table.getMeld(0).toString());
	}
	@Test
	public void test_playInitial3() {
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
			bJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
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
		//B10 jk B12 
		//O10 B10 jk ----*
		players.add(ai);
		data.setValue(table, players);
		ai.getSTY().playInitial();
		assertEquals(11, ai.handSize());
		assertEquals(1,table.size());
		assertEquals("[O10, B10, JK]", table.getMeld(0).toString());
	}
	
	@Test
	public void test_playrest() {
		try {
			btile1 = new Tile("R1");
			btile2 = new Tile("O2");
			btile3 = new Tile("G3");
			btile4 = new Tile("G4");
			btile5 = new Tile("G5");
			btile6 = new Tile("B6");
			btile7 = new Tile("O7");
			btile8 = new Tile("R8");
			btile9 = new Tile("G8");
			btile10 = new Tile("O10");
			btile11 = new Tile("G10");
			btile12 = new Tile("B10");
			bJoker = new Tile("J");
			bJoker = new Tile("J");
		}catch(InvalidTileException e) {
			fail();
		}
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
		//G3 G4 G5
		//G8 R8 J; 
		//O10 G10 B10
		players.add(ai);
		data.setValue(table, players);
		ai.getSTY().playRest();;
		assertEquals(5, ai.handSize());
		assertEquals(3,table.size());
		assertEquals("[G8, R8, JK]", table.getMeld(0).toString());
		assertEquals("[O10, G10, B10]", table.getMeld(1).toString());
		assertEquals("[G3, G4, G5]", table.getMeld(2).toString());
	}
}
