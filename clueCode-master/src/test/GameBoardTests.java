package test;

import static org.junit.Assert.*;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;
import clueGame.RoomCell.DoorDirection;

public class GameBoardTests {
public static Board testBoard;

	@BeforeClass
	public static void setUp() {
		testBoard = new Board();
		//testBoard.loadConfigFiles();
	}

	
	@Test
	public void boardDimTest() {
		int rowNum = testBoard.getNumRows();
		int colNum = testBoard.getNumColumns();
		Assert.assertEquals(25, rowNum);
		Assert.assertEquals(25, colNum);
		Assert.assertEquals(625, rowNum*colNum);
	}
	
//	@Test
//	public void roomCellCtorTest() {
//		
//	}

	@Test
	public void doorDirTest() {
		RoomCell testRoom = testBoard.getRoomCellAt(6,1);
		boolean isDoor = testRoom.isDoorway();
		Assert.assertEquals(true, isDoor);
		Assert.assertEquals(DoorDirection.DOWN,testRoom.getDoorDirection());
		
		testRoom = testBoard.getRoomCellAt(4,1);
		isDoor = testRoom.isDoorway();
		Assert.assertEquals(false, isDoor);
		
		int numDoors=0;
		for (int i = 0; i<testBoard.getNumColumns();i++)
		{
			for (int j = 0; j<testBoard.getNumRows();j++)
			{
				testRoom=testBoard.getRoomCellAt(i, j);
				if (testRoom != null) {
					isDoor = testRoom.isDoorway();
				} else {
					isDoor = false;
				}
				if (isDoor == true) {
					numDoors++;
				}
			}
		}
		Assert.assertEquals(38, numDoors);
	}
	
	@Test
	public void checkRoomInitial() {
		RoomCell testRoom = testBoard.getRoomCellAt(3,3);
		char roomInitial = testRoom.getInitial();
		Assert.assertEquals('O', roomInitial);
		
		testRoom = testBoard.getRoomCellAt(3,12);
		roomInitial = testRoom.getInitial();
		Assert.assertEquals('S', roomInitial);
		
		testRoom = testBoard.getRoomCellAt(12,3);
		roomInitial = testRoom.getInitial();
		Assert.assertEquals('R', roomInitial);
	}
	
	@Test
	public void checkCalcIndex() {
		int index = testBoard.calcIndex(0, 24);
		Assert.assertEquals(24, index);
		
		index = testBoard.calcIndex(1, 0);
		Assert.assertEquals(25, index);
		
		index = testBoard.calcIndex(1, 24);
		Assert.assertEquals(49, index);
		
		index = testBoard.calcIndex(24, 0);
		Assert.assertEquals(600, index);

	}
	
	@Test
	public void checkRoomLegend() {
		Map<Character, String> testRoom = testBoard.getRooms();
		Assert.assertEquals(testRoom.get('C'), "Conservatory");
		Assert.assertEquals(testRoom.get('K'), "Kitchen");
		Assert.assertEquals(testRoom.get('B'), "Ballroom");
		Assert.assertEquals(testRoom.get('L'), "Library");
		Assert.assertEquals(testRoom.get('S'), "Study");
		Assert.assertEquals(testRoom.get('D'), "Dining Room");
		Assert.assertEquals(testRoom.get('O'), "Lounge");
		Assert.assertEquals(testRoom.get('H'), "Hall");
		Assert.assertEquals(testRoom.get('X'), "Closet");
		Assert.assertEquals(testRoom.size(), 10);
	}
	
	@Test (expected = clueGame.BadConfigFormatException.class)
	public void exceptionTest() {
		testBoard = new Board("clueLegend.csv", "clueMap.csv");//files are switched
	}
}
