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
		tile = new Tile (Tile.Color.BLUE, 12);
		assertEquals(Tile.Color.BLUE, tile.getColor());
		assertEquals(12, tile.getNumber());
		
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
}
