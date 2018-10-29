package rrrummy_AI;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rrrummy.InvalidTileException;
import rrrummy.Player;
import rrrummy.Table;
import rrrummy.Tile;

public class STR3Test {
	private Tile atile1,atile2,atile3,atile4,atile5,atile6,atile7,atile8,atile9,atile10,atile11,atile12,atile13;
	private Tile aJoker;
	private Tile bJoker;
	private AI ai,ai2,ai3;
	private AIstrategy aISty;
	private GameData data;
	private Table table;
	private ArrayList<Player> players;
	@Before
	public void setUp() throws Exception {
		table = new Table();
		ai = new AI("Hunter");
		ai2 = new AI("Tony");
		ai3 = new AI("Bob");
		data = new GameData();
		aISty = new STR3(data);
		ai.setSTY(aISty);
		players = new ArrayList<Player>();
	}

	@Test
	public void test_playInitial1() {
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
		ai.draw(atile1);
		ai.draw(atile2);
		ai.draw(atile3);
		ai.draw(atile4);
		ai.draw(atile5);
		ai.draw(atile6);
		ai2.draw(atile7);
		ai2.draw(atile8);
		ai2.draw(atile9);
		ai3.draw(atile10);
		ai3.draw(atile11);
		ai3.draw(atile12);
		ai.draw(atile13);
		ai.draw(aJoker);
		
		players.add(ai);
		players.add(ai2);
		players.add(ai3);
		data.setValue(table, players);
		assertEquals(8, ai.handSize());
		ai.getSTY().playInitial();
		assertEquals3, ai.handSize());
		//ai.getSTY().playRest();
	}

	@Test
	public void test_playRest1() {
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
		
		players.add(ai);
		data.setValue(table, players);
		ai.getSTY().playRest();
	}
}
