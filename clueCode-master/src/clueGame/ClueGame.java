package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class ClueGame {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players;
	private Solution sol;
	private Suggestion newSuggestion;
	public Board board;
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	private String deckList, playerList;
	FileReader read;
	Scanner scan;
	Random rn = new Random();
	//test
	
	public ClueGame(){
		deck = new ArrayList<Card>();
		players = new ArrayList<Player>();
		sol = new Solution();
		sol.setSolution("", "", "");
		board = new Board();
		deckList = "Cards.txt";
		playerList = "Players.txt";
		loadConfigFiles();
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void deal(){
		while(deck.size()> 0){
			for (Player p: players){
				if(deck.size()> 0){
					int card = rn.nextInt(deck.size());
					Card c = deck.get(card);
					p.addCard(c);
					deck.remove(card);
				}

			}
		}
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
	
	public void loadPlayers() throws Exception{
		read = new FileReader(playerList);
		scan = new Scanner(read);
		int counter = 0;
		while (scan.hasNextLine()){
			String name = scan.nextLine();
			String color = scan.nextLine();
			int row = scan.nextInt();
			int col = scan.nextInt();
			if (scan.hasNextLine()){ 
				String n = scan.nextLine();
			}
			if (counter >0){
				players.add(new ComputerPlayer(name, color, board.calcIndex(row, col)));
			}else {
				players.add(new HumanPlayer(name, color, board.calcIndex(row, col)));
			}
		}
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

	public void setSol() {
		rn = new Random();
		int w = Math.abs(rn.nextInt() % 6) + 1;
		int r = Math.abs(rn.nextInt() % 9) + 1;
		int p = Math.abs(rn.nextInt() % 6) + 1;
		int cw= 0, cr = 0, cp = 0;
		//System.out.println(w+ " " + r + " " + p);
		String person = null, weapon = null, room = null;
		Iterator<Card> it= deck.iterator();
		while(it.hasNext()){
			Card c = it.next();
			switch(c.getCardType()){
			case ROOM:
				cr++;
				if (cr== r){
					room = c.getName();
					it.remove();
				}
				break;
			case WEAPON:
				cw++;
				if (cw == w){
					weapon = c.getName();
					it.remove();
				}
				break;
			case PERSON:
				cp++;
				if (cp == p){
					person = c.getName();
					it.remove();
				}
				break;
			}
		}
		sol.setSolution(person, weapon, room);
		/*System.out.println(person + weapon + room);
		System.out.println(deck.size());
		*/
	}
}
