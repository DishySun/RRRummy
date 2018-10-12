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
	}
	
	public void testAdd2() {
		//add as a set
		init();
		assertEquals(0, testMeld.size());
		assertTrue(testMeld.add(r8));
		assertEquals(1, testMeld.size());
		assertTrue(testMeld.add(g8));
		assertEquals(2, testMeld.size());
		assertTrue(testMeld.add(o8));
		assertTrue(testMeld.add(b8));
		//set can only have up to 4 tiles
		assertFalse(testMeld.add(b8));
	}
	
	public void testAddJoker() {
		init();
		//joker can be added because joker is the first tile in this meld
		assertTrue(testMeld.add(joker));
		assertTrue(testMeld.add(g8));
		assertFalse(testMeld.add(r7));
		assertTrue(testMeld.add(b8));
		//after above, Tile can only be added as set from now
		assertTrue(testMeld.add(o8));
		assertEquals(4, testMeld.size());
	}
	
	public void testAddJoker2() {
		init();
		assertTrue(testMeld.add(r2));
		assertFalse(testMeld.add(joker)); //joker can be added both ends of the meld
		assertTrue(testMeld.add(r1));
		assertTrue(testMeld.add(joker));
	}
	public void testAddJoker3() {
		init();
		assertTrue(testMeld.add(r2));
		assertTrue(testMeld.addHead(joker)); 
	}
	
	public void testRemove() {
		init();
		testMeld.add(r4);
		testMeld.add(r5);
		testMeld.add(r6);
		testMeld.add(r7);
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
		testMeld.add(r1);
		testMeld.add(r2);
		testMeld.add(r3);
		testMeld.add(r4);
		testMeld.add(r5);
		testMeld.add(r6);
		testMeld.add(r7);
		testMeld.add(r8);
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
		testMeld.add(r1);
		testMeld.add(r2);
		testMeld.add(r3);
		testMeld.add(r4);
		testMeld.add(joker);
		testMeld.add(r6);
		testMeld.add(r7);
		testMeld.add(r8);
		assertEquals(null, testMeld.replace(g8));
		assertEquals(joker, testMeld.replace(r5));
	}
	
	public void isValid() {
		init();
		testMeld.add(r1);
		assertFalse(testMeld.isValid());
		testMeld.add(r2);
		assertFalse(testMeld.isValid());
		testMeld.add(r3);
		assertTrue(testMeld.isValid());
		testMeld.add(r4);
		assertTrue(testMeld.isValid());
		testMeld.add(joker);
		assertTrue(testMeld.isValid());
		testMeld.add(r6);
		assertTrue(testMeld.isValid());
		testMeld.add(r7);
		assertTrue(testMeld.isValid());
		testMeld.add(r8);
		assertTrue(testMeld.isValid());
	}
}
