package AI;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AITest {
	private AI testAI;
	private AIstrategy AISty;
	@Before
	public void setUp() throws Exception {
		testAI = new AI("Hunter");
	}

	@Test
	public void test_Constructure() {
		testAI = new AI("Tony");
		assertEquals("Tony", testAI.getName());
	}
	
	@Test
	public void test_setSTY() {
		testAI.setSTY(AISty);
	}
}
