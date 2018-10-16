package rrrummy_AI;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GameDataTest {
	private Observer observer;
	private GameData testGameData;
	@Before
	public void setUp() throws Exception {
		testGameData = new GameData();
	}

	@Test
	public void test_registerObserver() {
		observer = null;
		testGameData.registerObserver(observer);
		assertEquals(1,testGameData.getObserversSize());
	}

	@Test
	public void test_removeObserver() {
		observer = null;
		testGameData.registerObserver(observer);
		testGameData.removeObserver(observer);
		assertEquals(0,testGameData.getObserversSize());
	}
}
