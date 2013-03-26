package test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

import experiment.IntBoard;

public class BoardAdjTargetTests {
	
	public static Board testBoard;
	public LinkedList<Integer> testList;
	public Set<BoardCell> testTargets;
	
	@BeforeClass
	public static void setUp() {
		testBoard = new Board();
	}
	
	@Before
	public void cleanTestList() {
		testList = new LinkedList<Integer>();
		testTargets = new HashSet<BoardCell>();
	}
	
	@Test
	public void testAdjacencySurroundedbyWalkways() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(17, 7));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(17, 6)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(17, 8)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(16, 7)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(18, 7)));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacencyBoardEdge1() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(17, 0));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(16, 0)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(17, 1)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyBoardEdge2() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(24, 7));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(24, 6)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(23, 7)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyBoardEdge3() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(16, 24));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(15, 24)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(16, 23)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyBoardEdge4() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(6, 24));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(7, 24)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(6, 23)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyNexttoRoomNotDoor1() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(9, 6));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(8, 6)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(10, 6)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(9, 7)));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacencyNexttoRoomNotDoor2() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(7, 19));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(7, 18)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(7, 20)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(6, 19)));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacencyNexttoValidDoor1() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(12, 6));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(12, 5)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(12, 7)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(11, 6)));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacencyNexttoValidDoor2() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(20, 18));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(20, 17)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(20, 19)));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(21, 18)));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacencyFromDoor1() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(6, 2));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(7, 2)));
		Assert.assertEquals(1, testList.size());
	}
	
	@Test
	public void testAdjacencyFromDoor2() {
		testList = testBoard.calcAdjacencyHelper(testBoard.calcIndex(19, 11));
		Assert.assertTrue(testList.contains(testBoard.calcIndex(18, 11)));
		Assert.assertEquals(1, testList.size());
	}

	
	@Test //old target test method
	public void testTargetsAlongWalkway1() {
		testBoard.calcTargets(5,6,4);
		Set<BoardCell> testTargets= testBoard.getTargets();
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(2, 7))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(3, 8))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(5, 10))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(6, 9))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(5, 8))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(7, 6))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(8, 7))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(9, 6))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(7, 4))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(8, 5))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(4, 7))));
		Assert.assertTrue(testTargets.contains(testBoard.getCellAt(testBoard.calcIndex(6, 7))));
		Assert.assertEquals(12, testTargets.size());
	}
	
	@Test //example test method. Gives warning on every line?!?
	public void testTargetsAlongWalkway2() {
		testBoard.calcTargets(15,8,2);
		Set<BoardCell> targets= testBoard.getTargets();
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(13, 8))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(14, 9))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(16, 9))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(17, 8))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(16, 7))));
		Assert.assertEquals(5, targets.size());
	}
	
	@Test
	public void testTargetsAlongWalkway3() {
		testBoard.calcTargets(17,15,3);
		Set<BoardCell> targets= testBoard.getTargets();
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(20, 15))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(19, 16))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(18, 13))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(17, 12))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(16, 17))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(18, 15))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(17, 14))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(16, 15))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(17, 16))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(15, 16))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(14, 15))));
		Assert.assertEquals(11, targets.size());
	}
	
	@Test
	public void testTargetsAlongWalkway4() {
		testBoard.calcTargets(4,16,1);
		Set<BoardCell> targets= testBoard.getTargets();
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(3, 16))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(5, 16))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(4, 17))));
		Assert.assertEquals(3, targets.size());
	}
	
	@Test
	public void testTargetstoEnterRoom1() {
		testBoard.calcTargets(22,7,3);
		Set<BoardCell> targets= testBoard.getTargets();
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(24, 6))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(23, 7))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(22, 6))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(21, 7))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(20, 6))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(19, 7))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(20, 8))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(21, 5))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(20, 8))));
		Assert.assertEquals(9, targets.size());
	}
	
	@Test
	public void testTargetstoEnterRoom2() {
		testBoard.calcTargets(6, 13, 2);
		Set<BoardCell> targets= testBoard.getTargets();
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(6, 11))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(5, 12))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(4, 13))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(5, 14))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(6, 15))));
		Assert.assertEquals(5, targets.size());
		
	}
	
	@Test
	public void testTargetsWhenLeavingRoom1() {
		testBoard.calcTargets(9,16,4);
		Set<BoardCell> targets= testBoard.getTargets();
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(11, 16))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(12, 17))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(10, 17))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(11, 18))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(9, 18))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(8, 17))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(8, 16))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(6, 17))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(7, 18))));
		Assert.assertEquals(9, targets.size());
		
	}
	
	@Test
	public void testTargetsWhenLeavingRoom2() {
		testBoard.calcTargets(17,19,2);
		Set<BoardCell> targets= testBoard.getTargets();
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(16, 18))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(15, 19))));
		Assert.assertTrue(targets.contains(testBoard.getCellAt(testBoard.calcIndex(16, 20))));
		Assert.assertEquals(3, targets.size());
		
	}
}
