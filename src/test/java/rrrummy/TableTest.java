package rrrummy;
import junit.framework.TestCase;

public class TableTest extends TestCase{
	private Table testTable;
	private Tile r1;
	private Tile r2;
	private Tile r3;
	private Tile r4;
	private Tile r5;
	private Tile r6;
	private Tile r7;
	private Tile r8;
	private Tile r9;
	private Tile r10;
	private Tile r11;
	private Tile r12;
	private Tile r13;
	public void setUp(){
		testTable = new Table();
		try {
			r1 = new Tile("R1");
			r2 = new Tile("R2");
			r3 = new Tile("R3");
			r4 = new Tile("R4");
			r5 = new Tile("R5");
			r6 = new Tile("R6");
			r7 = new Tile("R7");
			r8 = new Tile("R8");
			r9 = new Tile("R9");
			r10 = new Tile("R10");
			r11 = new Tile("R11");
			r12 = new Tile("R12");
			r13 = new Tile("R13");
		}catch(Exception e) {fail();}
	}
	
	public void test_add() {
		assertEquals(0,testTable.size());
		testTable.add(r1);
		assertEquals(1,testTable.size());
		try {
			assertTrue(testTable.add(r2, 0));
			assertEquals(1,testTable.size());
			assertFalse(testTable.add(r3, 1));//out of bound 
			testTable.add(r1);
		}catch (AbleToAddBothSideException e) {
			fail();
		}
	}
	
	public void test_cut() {
		testTable.add(r1);
		try {
			assertTrue(testTable.add(r2, 0));
			assertTrue(testTable.add(r3, 0));
			assertTrue(testTable.add(r4, 0));
			assertTrue(testTable.add(r5, 0));
		}catch(AbleToAddBothSideException e) {
			fail();
		}
		testTable.cut(0, 2);//cut at r3
		assertEquals(testTable.size(),2);
	}
	
}
