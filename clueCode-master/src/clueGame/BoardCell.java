package clueGame;

import clueGame.RoomCell.DoorDirection;

//import clueGame.RoomCell.DoorDirection;

public abstract class BoardCell {

	//private Boolean walkway, door, room;
	private int row, column;
	private DoorDirection doorDirection = DoorDirection.NONE;
	
	public Boolean isWalkway() {
		return false;
	}
	public char getRoomInitial(){
		return ';';
	}
	public Boolean isRoom() {
		return false;
	}
	
	public Boolean isDoorway() {
		return false;
	}
	
	public DoorDirection getDoorDirection(){
		return doorDirection;
	}
	
	abstract void draw();

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return column;
	}

	public void setCol(int column) {
		this.column = column;
	}
	
}
