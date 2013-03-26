package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {
	private String name;
	private ArrayList<Card> listOfCards;
	private String color;
	private int location;
	
	public Player(){
		listOfCards = new ArrayList<Card>();
	}
	public Player(String n, String c, int index){
		this.name = n;
		this.setColor(c);
		this.location = index;
		listOfCards = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(String person, String weapon, String room){
		ArrayList<Card> matchedCards = new ArrayList<Card>();
		Random random = new Random();
		boolean haveCards = false;
		Collections.shuffle(listOfCards);
		for(Card c: listOfCards){
			//System.out.println(c.getName() + " nAME OF CARD");
			if (c.getName().equals(person)|| c.getName().equals(weapon)||c.getName().equals(room)){
			matchedCards.add(c);
			haveCards = true;
			System.out.println(matchedCards.get(0).getName() + " this is a matched Card");
			}
		}
			if(haveCards){
				int cardChoice = random.nextInt(matchedCards.size());
				return matchedCards.get(cardChoice);
			
		}else{
			return null;
		}
	}
	public ArrayList<Card> getListOfCards() {
		return listOfCards;
	}

	public void setListOfCards(ArrayList<Card> listOfCards) {
		this.listOfCards = listOfCards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void addCard(Card card) {
		this.listOfCards.add(card);
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
