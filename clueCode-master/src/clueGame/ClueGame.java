package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ClueGame {
	private ArrayList<Card> deck;
	private ArrayList<Player> players;
	private Solution sol;
	private Suggestion newSuggestion;
	public Board board;
	private String deckList, playerList;
	//test
	
	public ClueGame(){
		deck = new ArrayList<Card>();
		players = new ArrayList<Player>();
		sol = new Solution();
		sol.setSolution("", "", "");
		board = new Board();
		deckList = "Cards.txt";
		playerList = "Players.txt";
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void deal(){
	}
	
	public void loadConfigFiles(){
		
		try {
			loadCards();
			loadPlayers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadCards() throws FileNotFoundException {
		FileReader read;
		
			read = new FileReader(deckList);
			Scanner scan = new Scanner(read);
			
			int type;
			while (scan.hasNextLine()){
				type = scan.nextInt();
				String c = scan.findInLine(" ");
				String name = scan.nextLine();
			
				
				switch(type){
				case 1:
					deck.add(new Card(name, Card.CardType.WEAPON));
					break;
				case 2:
					deck.add(new Card(name, Card.CardType.ROOM));
					break;
				case 3: 
					deck.add(new Card(name, Card.CardType.PERSON));
					break;
				}
				
			}
			scan.close();
	}
	
	public void loadPlayers() throws BadConfigFormatException{
		
	}
	
	public void selectAnswer(){
	}
	
	public Card handleSuggestion(String person,String room,String weapon,Player accusingPerson){
		newSuggestion.setSuggetion(new Card(person,Card.CardType.PERSON),new Card(weapon, Card.CardType.WEAPON),new Card(room, Card.CardType.ROOM));
		ArrayList<Player> playerList = new ArrayList<Player>(players);
		ArrayList<Card> returnCard = new ArrayList<Card>();
		playerList.remove(accusingPerson);
		Collections.shuffle(playerList);
		for(Player p:playerList){
			if(p.getListOfCards().contains(newSuggestion.getPerson())){
				returnCard.add(newSuggestion.getPerson());
			}
			if(p.getListOfCards().contains(newSuggestion.getWeapon())){
					returnCard.add(newSuggestion.getWeapon());
			}
			if(p.getListOfCards().contains(newSuggestion.getRoom())){
				returnCard.add(newSuggestion.getRoom());	
			}
			if(returnCard.size() > 0){
				Collections.shuffle(returnCard);
				return returnCard.get(0);
			}
		}
		return null;
	}
	public boolean checkAccusation(Solution solution){
		if(solution.getSolutionPerson().equals(sol.getSolutionPerson()) && solution.getSolutionWeapon().equals(sol.getSolutionWeapon()) && solution.getSolutionRoom().equals(sol.getSolutionRoom())){
			return true;
		}else{
		return false;
	}
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public Solution getSol() {
		return sol;
	}

	public void setSol(Solution sol) {
		this.sol = sol;
	}
}
