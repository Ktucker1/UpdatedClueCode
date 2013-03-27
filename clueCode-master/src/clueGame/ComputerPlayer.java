package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	private ArrayList<Card> unseenCards;
	
	public ComputerPlayer() {
		super();
		seenCards = new ArrayList<Card>();
		unseenCards = new ArrayList<Card>();
	}
	
	public ComputerPlayer(String n, String c, int index) {
		super(n, c, index);
	}
	
	public BoardCell pickLocation(HashSet<BoardCell> a){
		ArrayList<BoardCell> doorsInRange = new ArrayList<BoardCell>();
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>(a);
		Collections.shuffle(targets);
		for(BoardCell b: targets){
			if(b.isDoorway()){
				if(b.getRoomInitial() != this.lastRoomVisited){
					doorsInRange.add(b);
				}
			}
		}
		if(doorsInRange.size()>0){
			Collections.shuffle(doorsInRange);
			return doorsInRange.get(0);
		}else{
			return targets.get(0);
		}
	}

	public Suggestion createSuggestion(String room){
		ArrayList<Card> suggestion = new ArrayList<Card>();
		ArrayList<Card> remainCards =  new ArrayList<Card>();
			remainCards.addAll(unseenCards);
		Collections.shuffle(remainCards);
		boolean weapon = false;
		boolean person = false;
		//System.out.println(remainCards.size()+ " this is reamining size");
		for(int i=0;i<remainCards.size();i++){
			if(remainCards.get(i).getCardType() == Card.CardType.PERSON && person == false){
				suggestion.add(remainCards.get(i));
				//for(Player p:board.getPlayers()){
					//Move players to room in suggestion?
					//(p.getName().equals(remainCards.get(i).getName())){
					//	p.move()
					//	p.lastRoom = lastRoom;
					//}
				//}
			person = true;
			}else if(remainCards.get(i).getCardType() == Card.CardType.WEAPON && weapon == false){
				suggestion.add(remainCards.get(i));
				weapon = true;
			}
		}
		suggestion.add(new Card(room,Card.CardType.ROOM));
		//System.out.print(suggestion.size() +  " this is the size");
		//System.out.println(suggestion.get(0).getName() + " this is 0");
		//System.out.println(suggestion.get(1).getName() + " this is 1");
		//System.out.println(suggestion.get(2).getName() + " this is 2");
		if(suggestion.get(0).getCardType() == Card.CardType.PERSON){
			Suggestion newSuggestion = new Suggestion(suggestion.get(0),suggestion.get(1),suggestion.get(2));
			return newSuggestion;
		}else{
			Suggestion newSuggestion = new Suggestion(suggestion.get(1),suggestion.get(0),suggestion.get(2));
			return newSuggestion;
		}
	}

	public void setLastRoom(char c) {
	this.lastRoomVisited = c;
	}
	public char getLastRoom(){
		return this.lastRoomVisited;
	}

	public ArrayList<Card> getSeenCards() {
		return this.seenCards;
	}
	public ArrayList<Card> getUnseenCards(){
		return this.unseenCards;
	}
	public void addSeen(Card i) {
		this.seenCards.add(i);
	}
	public void addUnseen(Card i){
		this.unseenCards.add(i);
	}
}
