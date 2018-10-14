package rrrummy;
import java.util.ArrayList;

import junit.framework.TestCase;

public class MeldTest extends TestCase{
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
		}catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}
		testMeld = new Meld();
	}
	public void testConstructor() {
		init();
		assertEquals(0, testMeld.size());
	}
	
	public void testAdd() {
		//add as a run
		init();
		assertEquals(0, testMeld.size());
		assertTrue(testMeld.addHead(r4));
		assertEquals(1, testMeld.size());
		assertFalse(testMeld.addHead(g8));
		assertEquals(1, testMeld.size());
		assertTrue(testMeld.addTail(r5));
		assertTrue(testMeld.addTail(r6));
		assertFalse(testMeld.addTail(r2));
		assertTrue(testMeld.addHead(r3));
		assertTrue(testMeld.addHead(r2));
		assertTrue(testMeld.addHead(r1));
		assertEquals(6, testMeld.size());
	}
	
	public void testAdd2() {
		//add as a set
		init();
		assertEquals(0, testMeld.size());
		assertTrue(testMeld.addHead(r8));
		assertEquals(1, testMeld.size());
		assertTrue(testMeld.addHead(g8));
		assertEquals(2, testMeld.size());
		assertTrue(testMeld.addTail(o8));
		assertTrue(testMeld.addTail(b8));
		//set can only have up to 4 tiles
		assertFalse(testMeld.addHead(b8));
		assertFalse(testMeld.addHead(joker));
	}
	
	public void testAddJoker() {
		init();
		//joker can be added because joker is the first tile in this meld
		try {
			assertTrue(testMeld.add(joker));
		}catch(AbleToAddBothSideException e) {
			fail("Joker can be added by using add() method to an empty meld");
		}
		assertTrue(testMeld.addTail(g8));
		assertFalse(testMeld.addHead(r7));
		assertTrue(testMeld.addTail(b8));
		//after above, Tile can only be added as set from now
		assertTrue(testMeld.addTail(o8));
		assertEquals(4, testMeld.size());
	}
	
	public void testAddJoker2() {
		init();
		assertTrue(testMeld.addHead(r2));
		try {
			testMeld.add(joker); 
			fail("add() method CANNOT be used to add a joker when both side can be added");
		}catch (AbleToAddBothSideException e) {}
		assertTrue(testMeld.addHead(r1));
		try {
			assertTrue(testMeld.add(joker));
		}catch (AbleToAddBothSideException e) {
			fail("Joker can be added by using add() method when only one side is able to add to");
		}
	}
	public void testAddJoker3() {
		init();
		assertTrue(testMeld.addHead(r2));
		assertTrue(testMeld.addTail(r3));
		assertTrue(testMeld.addHead(joker)); 
		assertTrue(testMeld.addTail(joker));
	}
	
	public void testRemove() {
		init();
		assertTrue(testMeld.addTail(r4));
		assertTrue(testMeld.addTail(r5));
		assertTrue(testMeld.addTail(r6));
		assertTrue(testMeld.addTail(r7));
		assertEquals(4, testMeld.size());
		//test removeHead
		Tile t = testMeld.removeHead();
		assertEquals(t, r4);
		assertEquals(3, testMeld.size());
		//test removeTail
		Tile t2 = testMeld.removeTail();
		assertEquals(t2, r7);
		assertEquals(2,testMeld.size());
	}
	
	public void testCut() {
		init();
		assertTrue(testMeld.addHead(r1));
		assertTrue(testMeld.addTail(r2));
		assertTrue(testMeld.addTail(r3));
		assertTrue(testMeld.addTail(r4));
		assertTrue(testMeld.addTail(r5));
		assertTrue(testMeld.addTail(r6));
		assertTrue(testMeld.addTail(r7));
		assertTrue(testMeld.addTail(r8));
		ArrayList<Tile> returnArr = testMeld.cut(3); // cut at r4
		assertEquals(4, testMeld.size());
		assertEquals(4, returnArr.size());
		assertEquals(Tile.Color.RED, returnArr.get(0).getColor());
		assertEquals(1, returnArr.get(0).getNumber());
		assertEquals(Tile.Color.RED, returnArr.get(returnArr.size()-1).getColor());
		assertEquals(4, returnArr.get(returnArr.size()-1).getNumber());
		assertEquals(r5, testMeld.removeHead());
		assertEquals(r8, testMeld.removeTail());
	}
	
	public void testReplace() {
		init();
		assertTrue(testMeld.addTail(r1));
		assertTrue(testMeld.addTail(r2));
		assertTrue(testMeld.addTail(r3));
		assertTrue(testMeld.addTail(r4));
		assertTrue(testMeld.addTail(joker));
		assertTrue(testMeld.addTail(r6));
		assertTrue(testMeld.addTail(r7));
		assertTrue(testMeld.addTail(r8));
		assertEquals(null, testMeld.replace(g8));
		assertEquals(joker, testMeld.replace(r5));
	}
	
	public void tsetIsValid() {
		init();
		assertTrue(testMeld.addTail(r1));
		assertFalse(testMeld.isValid());
		assertTrue(testMeld.addTail(r2));
		assertFalse(testMeld.isValid());
		assertTrue(testMeld.addTail(joker));
		assertTrue(testMeld.isValid());
		assertTrue(testMeld.addTail(r4));
		assertTrue(testMeld.isValid());
	}
	
	public void testIsRunAndIsSet() {
		init();
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addHead(r8));// R8
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addHead(g8));// G8 R8
		assertFalse(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addTail(o8));// G8 R8 O8
		assertFalse(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertEquals(o8, testMeld.removeTail());//G8 R8
		assertFalse(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertEquals(g8, testMeld.removeHead()); //R8
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
		assertTrue(testMeld.addHead(r7)); // R7 R8
		assertTrue(testMeld.isRun());
		assertFalse(testMeld.isSet());
		assertTrue(testMeld.addHead(r6)); // R6 R7 R8
		assertTrue(testMeld.isRun());
		assertFalse(testMeld.isSet());
		assertEquals(r6, testMeld.removeHead()); //R7 R8
		assertTrue(testMeld.isRun());
		assertFalse(testMeld.isSet());
		assertEquals(r7, testMeld.removeHead()); // R8
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
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
		assertTrue(testMeld.addHead(r7));// R6 jk R7
		assertTrue(testMeld.isRun());
		assertFalse(testMeld.isSet());
		assertEquals(r6, testMeld.removeHead()); //jk R7
		assertTrue(testMeld.isRun());
		assertTrue(testMeld.isSet());
	}
}
