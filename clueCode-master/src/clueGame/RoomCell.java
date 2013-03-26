package clueGame;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection {
		UP ("U"), DOWN ("D"), LEFT ("L"), RIGHT ("R"), NONE ("N");
		private String value;
		DoorDirection(String doorType) {
			value = doorType;
		}
	}

	private DoorDirection doorDirection;
	
	public RoomCell() {
		super.setRoomInitial('X');
	}
	
	public RoomCell(String codeIn) {
		setRoomInitial(codeIn.charAt(0));
		if (codeIn.length() == 1) {
			doorDirection = DoorDirection.NONE;
		} else {
			String doorCode = String.valueOf(codeIn.charAt(1));
			if (doorCode.contains("U") || doorCode.contains("D") || doorCode.contains("L") || doorCode.contains("R")) {
				if (doorCode.equalsIgnoreCase("U")) doorDirection = DoorDirection.UP;
				else if (doorCode.equalsIgnoreCase("D")) doorDirection = DoorDirection.DOWN;
				else if (doorCode.equalsIgnoreCase("L")) doorDirection = DoorDirection.LEFT;
				else if (doorCode.equalsIgnoreCase("R")) doorDirection = DoorDirection.RIGHT;
				else System.out.println(doorCode);
			} else {
				doorDirection = DoorDirection.NONE;
//				if (doorCode.hashCode() != 13) {
//				}
			}
		}
	}
	
	
	
	@Override
	public Boolean isRoom() {
		return true;
	}
	
	@Override
	public Boolean isDoorway() {
		if (doorDirection == DoorDirection.UP || doorDirection == DoorDirection.DOWN || doorDirection == DoorDirection.LEFT || doorDirection == DoorDirection.RIGHT) {
			return true;
		} return false;
//		if(doorDirection==DoorDirection.NONE) {
//			return false;
//		} else
//			return true;
	}
	
	@Override
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	@Override
	void draw() {
		// TODO Auto-generated method stub
		
	}

}
