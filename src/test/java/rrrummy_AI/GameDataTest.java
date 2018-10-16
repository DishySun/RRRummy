package rrrummy_AI;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GameDataTest {
	private Observer observers;
	private GameData testGameData;
	@Before
	public void setUp() throws Exception {
		testGameData = new GameData();
	}

	@Test
	public void test_registerObserver() {
		Observer observer = null;
		testGameData.registerObserver(observer);
		assertEquals(1,testGameData.getObserversSize());
	}

	@Test
	public void test_removeObserver() {
		Observer observer = null;
		testGameData.registerObserver(observer);
		testGameData.removeObserver(observer);
		assertEquals(0,testGameData.getObserversSize());
	}
}
