package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import clueGame.RoomCell.DoorDirection;


public class Board {
		
	private static ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows, numColumns;
	private Scanner legendIn, mapIn;
	private FileReader legendReader, mapReader;
	private Map<Integer, LinkedList<Integer>> adjacencyMatrix;
	private boolean[] visited ;
	private HashSet<BoardCell> targets;
	private String mapFile = "clueMap.csv", legendFile = "clueLegend.csv";
	
	
	
	public Board(){
		this("clueMap.csv", "clueLegend.csv");
	}
	
	public Board(String mapFile, String legendFile) {
		super();
		rooms = new HashMap<Character, String>();
		adjacencyMatrix = new HashMap<Integer, LinkedList<Integer>>();
		this.mapFile = mapFile;
		this.legendFile = legendFile;
		targets = new HashSet<BoardCell>();
		loadBoardConfigFiles();
		visited = new boolean[(numRows)*(numColumns)];
		for(int i=0;i<((numRows)*(numColumns));i++)
			visited[i]=false;
		calcAdjacencies();
	}
	
	private void initMapReader() {
		try {
			mapReader = new FileReader(mapFile);
			mapIn = new Scanner(mapReader);
		} catch(FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	private void initLegendReader() {
		try {
			legendReader = new FileReader(legendFile);
			legendIn = new Scanner(legendReader);
		} catch(FileNotFoundException e) {
			System.out.println(e.toString());
		}
	}
	
	public void loadBoardConfigFiles() throws BadConfigFormatException {

		initMapReader();
		initLegendReader();
		int properLength = configCheckMapRowLength(0);
		for (int i = 0; i < (configCheckNumberRows()-1); i++) {
			if (configCheckMapRowLength(i) != properLength) {
				throw new BadConfigFormatException("Error with board map formatting.");
			}
		}
		if (!configCheckLegendRowLength()) {
			throw new BadConfigFormatException("Error with legend formatting.");
		}
		populateRoomsFromLegend();
		populateCellsFromMap();
		int index = 0;
		for(int row = 0;row < numRows;row++){
			for(int col = 0; col < numColumns;col++){
				cells.get(index).setRow(row);
				cells.get(index).setCol(col);
			}
			index++;
		}
	}
	public int configCheckNumberRows() {
		initMapReader();

		int tempNumRows = 0;

		while (mapIn.hasNextLine()) {
			tempNumRows++;
			mapIn.nextLine();
		}

		numRows = tempNumRows;
		return tempNumRows;
	}

	public int configCheckMapRowLength(int rowIndex) {
		initMapReader();

		String currRow = new String();

		for (int i = 0; i <= rowIndex; i++) { //returns desired line from map csv
			if (mapIn.hasNextLine()) {
				currRow = mapIn.nextLine();
			}
		}
		int commas = 0;
		for(int i = 0; i < currRow.length(); i++) { //returns number of rooms as a function of commas in a row
			if(currRow.charAt(i) == ',') commas++;
		}
		numColumns = (commas + 1);
		return commas+1;
	}

	public Boolean configCheckLegendRowLength() {
		initLegendReader();
		
		String currRow = new String();
		Boolean isOkay = true;
		
		while(legendIn.hasNextLine()) {
			currRow = legendIn.nextLine();
			int commas = 0;
			for(int i = 0; i < currRow.length(); i++) { //returns number of items as a function of commas in a row
				if(currRow.charAt(i) == ',') commas++;
			}
			if (commas != 1) {
				isOkay = false;
			}
			//System.out.println(commas);
		}
		
		return isOkay;	
	}
	
	public void populateRoomsFromLegend() {
		initLegendReader();
		while (legendIn.hasNextLine()) {
			int commaIndex = 0;
			String currLine = legendIn.nextLine();
			while(currLine.charAt(commaIndex) != ',') {
				commaIndex++;
			}
			while(currLine.charAt(commaIndex + 1) == ' ') {
				commaIndex++;
			}
			rooms.put(currLine.charAt(0), currLine.substring(commaIndex + 1));
		}
	}
	
	public void populateCellsFromMap() throws BadConfigFormatException {
		cells = new ArrayList<BoardCell>();
		initMapReader();
		String currLine;
		mapIn.useDelimiter(",|\\n");
		while (mapIn.hasNext()) {
			
			currLine = mapIn.next();
			if (currLine.equalsIgnoreCase("W") || currLine.startsWith("W")) {
				//System.out.println("Walkway");
				cells.add(new WalkwayCell());
			} else {
				if (rooms.containsKey(currLine.charAt(0))) {
					cells.add(new RoomCell(currLine));
				} else {
					throw new BadConfigFormatException("Invalid character, " + currLine + ", detected in room map.");
				}
			}
		}		
	}
	
	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public int calcIndex(int row, int column) {
		return row*numColumns + column;
	}
	
	public RoomCell getRoomCellAt(int row, int column) {
		if (cells.get(calcIndex(row, column)).isRoom()) {
			return (RoomCell) cells.get(calcIndex(row, column));
		} else return null;
	}
	

	public static BoardCell getCellAt(int index) {
		return cells.get(index);
	}

	
	public void calcAdjacencies() {
		for (int i = 0; i < ((numColumns) * (numRows)); i++) {
			adjacencyMatrix.put(i, calcAdjacencyHelper(i));
		}
	}
	
	public LinkedList<Integer> getAdjacencies( int location){
		LinkedList<Integer> adjList = new LinkedList<Integer>();
		adjList = adjacencyMatrix.get(location);
		return adjList;
		
	}
	
	public LinkedList<Integer> calcAdjacencyHelper(int Location) {
		LinkedList<Integer> adjList = new LinkedList<Integer>();
		int row, column;
		BoardCell currCell;
		int currIndex;
		row = rowFromIndex(Location);
		column = colFromIndex(Location);
		Boolean currIsDoor = getCellAt(Location).isDoorway();
		Boolean currIsRoom = getCellAt(Location).isRoom();
		

		if (!currIsRoom || currIsDoor) {
			if (row != 0) {//UP
				if (Board.getCellAt(calcIndex(row-1,column)).isWalkway() == true || 
						(Board.getCellAt(calcIndex(row-1,column)).isDoorway() == true && !currIsDoor && 
						Board.getCellAt(calcIndex(row-1,column)).getDoorDirection() == DoorDirection.DOWN)) {
					adjList.push(calcIndex(row - 1, column));}
			}
			if (column != 0) {//LEFT
				if (Board.getCellAt(calcIndex(row,column-1)).isWalkway() == true || 
						(Board.getCellAt(calcIndex(row,column-1)).isDoorway() == true && !currIsDoor && 
						Board.getCellAt(calcIndex(row,column-1)).getDoorDirection() == DoorDirection.RIGHT)) {
					adjList.push(calcIndex(row, column - 1));}
			}
			if (column+1 < numColumns) {//RIGHT
				if (Board.getCellAt(calcIndex(row,column+1)).isWalkway() == true || 
						(Board.getCellAt(calcIndex(row,column+1)).isDoorway() == true && !currIsDoor &&
						Board.getCellAt(calcIndex(row,column+1)).getDoorDirection() == DoorDirection.LEFT)) {
					adjList.push(calcIndex(row, column + 1));
				}
			}
			if (row+1 < numRows) {//DOWN
				if (Board.getCellAt(calcIndex(row+1,column)).isWalkway() == true || 
						(Board.getCellAt(calcIndex(row+1,column)).isDoorway() == true && !currIsDoor &&
						Board.getCellAt(calcIndex(row+1,column)).getDoorDirection() == DoorDirection.UP)) {
					adjList.push(calcIndex(row + 1, column));
				}
			}
		}
		return adjList;
	}

	public int rowFromIndex (int index)
	{
		return (index/numColumns);
	}
	
	public int colFromIndex(int index)
	{
		return (index%numColumns);
	}
	
	public void calcTargets(int row,int col, int steps) {
		targets.clear();
		recTargets (row,col,steps);
	}
	
	public void recTargets(int row,int col, int steps) {
		int location= calcIndex(row, col);
		LinkedList<Integer> tempCopy = adjacencyMatrix.get(location);
		LinkedList<Integer> currAdj = new LinkedList<Integer>();
		
		
		//System.out.println(tempCopy.size());
		//System.out.println(adjacencyMatrix.size());
		//System.out.println(adjacencyMatrix.get(location).size());
		for (int i=0;i<adjacencyMatrix.get(location).size();i++)
		{
			//System.out.println(tempCopy.get(i));
			if (visited[tempCopy.get(i)]==false) {
				currAdj.add(tempCopy.get(i));
				//System.out.println(tempCopy.get(i));
			}
		}
		visited[location] = true;
		for (int i=0; i<currAdj.size();i++)
		{
			visited[currAdj.get(i)]=true;
			if (steps==1 || cells.get(currAdj.get(i)).isDoorway()) {
				//System.out.println("ADD TO TARGETS: " + currAdj.get(i));
				targets.add(getCellAt(currAdj.get(i)));
				//System.out.println("Target Size: " + targets.size());
			} else {
				//System.out.println("BEFORE REC" + steps);
				recTargets(rowFromIndex(currAdj.get(i)),colFromIndex(currAdj.get(i)),steps-1);
			}
			visited[currAdj.get(i)]=false;
		}
		visited[location] = false;

		}
	
	public HashSet<BoardCell> getTargets() {
		
		return targets;
	}

	public void loadCards() {
		// TODO Auto-generated method stub
		
	}
	
	
}

