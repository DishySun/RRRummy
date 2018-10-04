package rrrummy;
import junit.framework.TestCase;

public class TileTest extends TestCase{
	Tile joker;
	Tile tile;
	Tile invalidTile;
	
	
	//constructor test
	public void testConstroctor() {
		//constructor for jokers
		joker = new Tile();
		assertEquals(Tile.Color.JOKER, joker.getColor());
		
		//constructor for numbered tile
		try {
		tile = new Tile (Tile.Color.BLUE, 12);
		assertEquals(Tile.Color.BLUE, tile.getColor());
		assertEquals(12, tile.getNumber());
		}catch (InvalidTileException e) {
			fail(e.getErrMsg());
		}
		
		//constructor for invalid tile
		try {
			invalidTile = new Tile("K2");
			fail("'K' should not be a valid color");
		}catch(InvalidTileException e) {
			System.out.println(e.getErrMsg());
			//e.printStackTrace();
		}
		
		try {
			invalidTile = new Tile("B17");
			fail("'17' should not be a valid number");
		}catch(InvalidTileException e) {
			System.out.println(e.getErrMsg());
			//e.printStackTrace();
		}
		
		try {
			invalidTile = new Tile("QP");
			fail("'Q' should not be a valid color and 'P' should not be a valid number");
		}catch(InvalidTileException e) {
			System.out.println(e.getErrMsg());
			//e.printStackTrace();
		}
		
		//
		try {
			invalidTile = new Tile("JAOBAOUOSJ");
			assertEquals(Tile.Color.JOKER, invalidTile.getColor());
		}catch(InvalidTileException e) {
			System.out.println(e.getErrMsg());
			fail("String strats with 'J' should be instancted to a joker tile");
		}
	}
	
	//b10 < r2 < r5 < g3 < g7 < o2 < jk
	public void testCompare() {
		Tile b10;
		Tile r2;
		Tile r5;
		Tile jk;
		try {
			b10	= new Tile ("B10");
			r2	= new Tile("R2");
			r5	= new Tile ("R5");
			jk	= new Tile();
			assertFalse(b10.isGreaterThan(r2));
			assertTrue(r2.isGreaterThan(b10));
			assertFalse(r2.isGreaterThan(r5));
			assertTrue(r5.isGreaterThan(r2));
			assertFalse(r5.isGreaterThan(jk));
			assertTrue(jk.isGreaterThan(r5));
			assertFalse(jk.isGreaterThan(jk));
			}catch (InvalidTileException e) {
				fail(e.getErrMsg());
			}
	}
}
