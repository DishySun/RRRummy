package rrrummy;
import junit.framework.TestCase;

public class HandTest extends TestCase{
	public void testConstructor() {
		Hand testHand = new Hand();
		assertEquals(0, testHand.size());
	}
	
	public void testAddRemove() {
		Hand testHand = new Hand();
		//R5 B3 O6 J B7 R11 G1 G10
		try {
			//add
			testHand.add(new Tile("R5"));
			assertEquals(1, testHand.size());
			testHand.add(new Tile("B3"));
			assertEquals(2, testHand.size());
			testHand.add(new Tile("O6"));
			assertEquals(3, testHand.size());
			testHand.add(new Tile("J"));
			assertEquals(4, testHand.size());
			testHand.add(new Tile("B7"));
			assertEquals(5, testHand.size());
			testHand.add(new Tile("R11"));
			assertEquals(6, testHand.size());
			testHand.add(new Tile("G1"));
			assertEquals(7, testHand.size());
			testHand.add(new Tile("G10"));
			assertEquals(8, testHand.size());
			assertEquals(Tile.Color.RED, testHand.getHand().get(0).getColor());
			assertEquals(5, testHand.getHand().get(0).getNumber());
			assertEquals(Tile.Color.BLUE, testHand.getHand().get(1).getColor());
			assertEquals(3, testHand.getHand().get(1).getNumber());
			assertEquals(Tile.Color.ORANGE, testHand.getHand().get(2).getColor());
			assertEquals(6, testHand.getHand().get(2).getNumber());
			assertEquals(Tile.Color.JOKER, testHand.getHand().get(3).getColor());
			assertEquals(0, testHand.getHand().get(3).getNumber());
			assertEquals(Tile.Color.BLUE, testHand.getHand().get(4).getColor());
			assertEquals(7, testHand.getHand().get(4).getNumber());
			assertEquals(Tile.Color.RED, testHand.getHand().get(5).getColor());
			assertEquals(11, testHand.getHand().get(5).getNumber());
			assertEquals(Tile.Color.GREEN, testHand.getHand().get(6).getColor());
			assertEquals(1, testHand.getHand().get(6).getNumber());
			assertEquals(Tile.Color.GREEN, testHand.getHand().get(7).getColor());
			assertEquals(10, testHand.getHand().get(7).getNumber());
			
			//remove
			Tile t = testHand.remove();
			assertEquals(7, testHand.size());
			assertEquals(Tile.Color.GREEN, t.getColor());
			assertEquals(10, t.getNumber());
			t = testHand.remove(3);
			assertEquals(6, testHand.size());
			assertEquals(Tile.Color.JOKER, t.getColor());
			assertEquals(0, t.getNumber());
		} catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}
	}
	
	public void testSort() {
		Hand testHand = new Hand();
		try {
			testHand.add(new Tile("R5"));
			testHand.add(new Tile("B3"));
			testHand.add(new Tile("O6"));
			testHand.add(new Tile("J"));
			testHand.add(new Tile("B7"));
			testHand.add(new Tile("R11"));
			testHand.add(new Tile("G1"));
			testHand.add(new Tile("G10"));
			//R5 B3 O6 J B7 R11 G1 G10
			testHand.sort();
			//B3 B7 R5 R11 G1 G10 O6 J
			assertEquals(Tile.Color.BLUE, testHand.getHand().get(0).getColor());
			assertEquals(Tile.Color.BLUE, testHand.getHand().get(1).getColor());
			assertEquals(Tile.Color.RED, testHand.getHand().get(2).getColor());
			assertEquals(Tile.Color.RED, testHand.getHand().get(3).getColor());
			assertEquals(Tile.Color.GREEN, testHand.getHand().get(4).getColor());
			assertEquals(Tile.Color.GREEN, testHand.getHand().get(5).getColor());
			assertEquals(Tile.Color.ORANGE, testHand.getHand().get(6).getColor());
			assertEquals(Tile.Color.JOKER, testHand.getHand().get(7).getColor());
			assertEquals(3, testHand.getHand().get(0).getNumber());
			assertEquals(7, testHand.getHand().get(1).getNumber());
			assertEquals(5, testHand.getHand().get(2).getNumber());
			assertEquals(11, testHand.getHand().get(3).getNumber());
			assertEquals(1, testHand.getHand().get(4).getNumber());
			assertEquals(10, testHand.getHand().get(5).getNumber());
			assertEquals(6, testHand.getHand().get(6).getNumber());
			assertEquals(0, testHand.getHand().get(7).getNumber());
		}catch(InvalidTileException e) {
			fail(e.getErrMsg());
		}
	}
}
