package rrrummy;

import java.util.ArrayList;

import junit.framework.TestCase;

public class MeldTest extends TestCase {
	private Meld testMeld;
	private Tile joker;
	private Tile r1;
	private Tile r2;
	private Tile r3;
	private Tile r4;
	private Tile r5;
	private Tile r6;
	private Tile r7;
	private Tile r8;
	private Tile b8;
	private Tile o8;
	private Tile g8;
	private Tile r13;
	private Tile r11;
	private Tile b1;
	private Tile b13;
	

	private void init() {
		joker = new Tile();
		try {
			r1 = new Tile("R1");
			r2 = new Tile("R2");
			r3 = new Tile("R3");
			r4 = new Tile("R4");
			r5 = new Tile("R5");
			r6 = new Tile("R6");
			r7 = new Tile("R7");
			r8 = new Tile("R8");
			b8 = new Tile("B8");
			o8 = new Tile("O8");
			g8 = new Tile("G8");
			r13 = new Tile("R13");
			r11 = new Tile("R11");
			b1 = new Tile("B1");
			b13 = new Tile("B13");
			
		} catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}
		testMeld = new Meld();
	}

	public void testConstructor() {
		init();
		assertEquals(0, testMeld.size());
	}

	public void testAdd() {
		// add as a run
		init();
		try {
			assertEquals(0, testMeld.size());
			assertTrue(testMeld.add(r4));
			assertEquals(1, testMeld.size());
			assertFalse(testMeld.add(g8));
			assertEquals(1, testMeld.size());
			assertTrue(testMeld.add(r5));
			assertTrue(testMeld.add(r6));
			assertFalse(testMeld.add(r2));
			assertTrue(testMeld.add(r3));
			assertTrue(testMeld.add(r2));
			assertTrue(testMeld.add(r1));
			assertEquals(6, testMeld.size());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}

	public void testAdd2() {
		// add as a set
		init();
		try {
			assertEquals(0, testMeld.size());
			assertTrue(testMeld.add(r8));
			assertEquals(1, testMeld.size());
			assertTrue(testMeld.add(g8));
			assertEquals(2, testMeld.size());
			assertTrue(testMeld.add(o8));
			assertTrue(testMeld.add(b8));
			// set can only have up to 4 tiles
			assertFalse(testMeld.add(b8));
			assertFalse(testMeld.addHead(joker));
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}

	public void testAddJoker() {
		init();
		// joker can be added because joker is the first tile in this meld
		try {
			assertTrue(testMeld.add(joker));
			assertTrue(testMeld.addHead(g8));
			assertFalse(testMeld.add(r7));
			assertTrue(testMeld.add(b8));
			// after above, Tile can only be added as set from now
			assertTrue(testMeld.add(o8));
			assertEquals(4, testMeld.size());
		} catch (AbleToAddBothSideException e) {
			fail("Joker can be added by using add() method to an empty meld");
		}
	}

	public void testAddJoker2() {
		init();
		try {
			assertTrue(testMeld.add(r2));
		}catch(AbleToAddBothSideException e) {
			fail();
		}
		try {
			testMeld.add(joker);
			fail("add() method CANNOT be used to add a joker when both side can be added");
		} catch (AbleToAddBothSideException e) {
		}
		try {
		assertTrue(testMeld.add(r1));
		}
		catch(AbleToAddBothSideException e) {
			fail();
		}
		try {
			assertTrue(testMeld.add(joker));
		} catch (AbleToAddBothSideException e) {
			fail("Joker can be added by using add() method when only one side is able to add to");
		}
	}

	
	//test if it's run after add joker
	public void testAddJoker3() {
		init();
		try {
			assertTrue(testMeld.add(r2));
			assertTrue(testMeld.add(r3));
			assertTrue(testMeld.addHead(joker));
			assertTrue(testMeld.addTail(joker));
			assertTrue(testMeld.isRun());
			assertFalse(testMeld.isSet());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}

	
	//test if it's set after add joker
	public void testAddJoker4() {
		init();
		try {
			assertTrue(testMeld.add(o8));
			assertTrue(testMeld.add(b8));
			assertTrue(testMeld.addHead(joker));
			assertTrue(testMeld.addTail(joker));
			assertFalse(testMeld.isRun());
			assertTrue(testMeld.isSet());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}
	
	
	//it can be either set or run
	public void testAddJoker5() {
		init();
		try {
			assertTrue(testMeld.addHead(joker));
			assertTrue(testMeld.addTail(joker));
			assertTrue(testMeld.add(r2));
			assertTrue(testMeld.isRun());
			assertTrue(testMeld.isSet());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}
	
	//it should add r1 to head and add r3 to be run
	public void testAddJoker6() {
		init();
		try {
			assertTrue(testMeld.add(joker));
			assertTrue(testMeld.add(r1));
			assertTrue(testMeld.isRun());
			assertTrue(testMeld.isSet());
			assertTrue(testMeld.add(r3));
			assertTrue(testMeld.isRun());
			assertFalse(testMeld.isSet());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}
	
	//it should add r13 to tail and add r11 to be run
	public void testAddJoker7() {
		init();
		try {
			assertTrue(testMeld.add(joker));
			assertTrue(testMeld.add(r13));
			assertTrue(testMeld.isRun());
			assertTrue(testMeld.isSet());
			assertTrue(testMeld.add(r11));
			assertTrue(testMeld.isRun());
			assertFalse(testMeld.isSet());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}
	
	//it should add r1 to head and add b1 as set
		public void testAddJoker8() {
			init();
			try {
				assertTrue(testMeld.add(joker));
				assertTrue(testMeld.add(r1));
				assertTrue(testMeld.isRun());
				assertTrue(testMeld.isSet());
				assertTrue(testMeld.add(b1));
				assertFalse(testMeld.isRun());
				assertTrue(testMeld.isSet());
			} catch (AbleToAddBothSideException e) {
				fail();
			}
		}
		
		//it should add r13 to tail and add b13 as set
		public void testAddJoker9() {
			init();
			try {
				assertTrue(testMeld.add(joker));
				assertTrue(testMeld.add(r13));
				assertTrue(testMeld.isRun());
				assertTrue(testMeld.isSet());
				assertTrue(testMeld.add(b13));
				assertFalse(testMeld.isRun());
				assertTrue(testMeld.isSet());
			} catch (AbleToAddBothSideException e) {
				fail();
			}
		}
	
	

	public void testRemove() {
		init();
		try {
			assertTrue(testMeld.add(r4));
			assertTrue(testMeld.add(r5));
			assertTrue(testMeld.add(r6));
			assertTrue(testMeld.add(r7));
			assertEquals(4, testMeld.size());
			// test removeHead
			Tile t = testMeld.removeHead();
			assertEquals(t, r4);
			assertEquals(3, testMeld.size());
			// test removeTail
			Tile t2 = testMeld.removeTail();
			assertEquals(t2, r7);
			assertEquals(2, testMeld.size());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}

	public void testCut() {
		init();
		try {
			assertTrue(testMeld.add(r1));
			assertTrue(testMeld.add(r2));
			assertTrue(testMeld.add(r3));
			assertTrue(testMeld.add(r4));
			assertTrue(testMeld.add(r5));
			assertTrue(testMeld.add(r6));
			assertTrue(testMeld.add(r7));
			assertTrue(testMeld.add(r8));
			ArrayList<Tile> returnArr = testMeld.cut(3); // cut at r4
			assertEquals(4, testMeld.size());
			assertEquals(4, returnArr.size());
			assertEquals(Tile.Color.RED, returnArr.get(0).getColor());
			assertEquals(1, returnArr.get(0).getNumber());
			assertEquals(Tile.Color.RED, returnArr.get(returnArr.size() - 1).getColor());
			assertEquals(4, returnArr.get(returnArr.size() - 1).getNumber());
			assertEquals(r5, testMeld.removeHead());
			assertEquals(r8, testMeld.removeTail());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}

	public void testReplace() {
		init();
		try {
			assertTrue(testMeld.add(r1));
			assertTrue(testMeld.add(r2));
			assertTrue(testMeld.add(r3));
			assertTrue(testMeld.add(r4));
			assertTrue(testMeld.addTail(joker));
			assertTrue(testMeld.add(r6));
			assertTrue(testMeld.add(r7));
			assertTrue(testMeld.add(r8));
			assertEquals(null, testMeld.replace(g8, 4));
			assertEquals(joker, testMeld.replace(r5,4));
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}

	public void testReplace2() {
		init();
		try {
			assertTrue(testMeld.add(r1));
			assertTrue(testMeld.add(r2));
			assertTrue(testMeld.add(r3));
			assertTrue(testMeld.add(r4));
			assertEquals(null, testMeld.replace(g8,3));
			assertEquals(null, testMeld.replace(r4,3));
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}
	public void tsetIsValid() {
		init();
		try {
			assertTrue(testMeld.add(r1));
			assertFalse(testMeld.isValid());
			assertTrue(testMeld.add(r2));
			assertFalse(testMeld.isValid());
			assertTrue(testMeld.addTail(joker));
			assertTrue(testMeld.isValid());
			assertTrue(testMeld.add(r4));
			assertTrue(testMeld.isValid());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}

	public void testIsRunAndIsSet() {
		init();
		try {
			assertTrue(testMeld.isRun());
			assertTrue(testMeld.isSet());
			assertTrue(testMeld.add(r8));// R8
			assertTrue(testMeld.isRun());
			assertTrue(testMeld.isSet());
			assertTrue(testMeld.add(g8));// R8 G8
			assertFalse(testMeld.isRun());
			assertTrue(testMeld.isSet());
			assertTrue(testMeld.add(o8));// R8 G8 O8
			assertFalse(testMeld.isRun());
			assertTrue(testMeld.isSet());
			assertEquals(o8, testMeld.removeTail());// R8 G8
			assertFalse(testMeld.isRun());
			assertTrue(testMeld.isSet());
			assertEquals(g8, testMeld.removeTail()); // R8
			assertTrue(testMeld.isRun());
			assertTrue(testMeld.isSet());
			assertTrue(testMeld.add(r7)); // R7 R8
			assertTrue(testMeld.isRun());
			assertFalse(testMeld.isSet());
			assertTrue(testMeld.add(r6)); // R6 R7 R8
			assertTrue(testMeld.isRun());
			assertFalse(testMeld.isSet());
			assertEquals(r6, testMeld.removeHead()); // R7 R8
			assertTrue(testMeld.isRun());
			assertFalse(testMeld.isSet());
			assertEquals(r7, testMeld.removeHead()); // R8
			assertTrue(testMeld.isRun());
			assertTrue(testMeld.isSet());
		} catch (AbleToAddBothSideException e) {
			fail();
		}
	}

	public void testIsRunAndIsSetWithJoker() {
		init();
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addHead(joker)); // jk
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addTail(r8)); // jk R8
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addTail(o8)); // jk R8 O8
		assertFalse(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertEquals(joker, testMeld.removeHead()); // R8 O8
		assertFalse(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addTail(joker));// R8 O8 jk
		assertFalse(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertEquals(r8, testMeld.removeHead()); //O8 jk
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertEquals(o8, testMeld.removeHead()); //jk
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addHead(r6));//R6 jk
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addHead(r5));//R5 R6 jk
		assertTrue(testMeld.isRun());
		assertFalse(testMeld.isSet());
		assertEquals(r5, testMeld.removeHead()); //R6 jk
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addTail(r8));// R6 jk R8
		assertTrue(testMeld.isRun());
		assertFalse(testMeld.isSet());
		assertEquals(r6, testMeld.removeHead()); //jk R7
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
	}
}
