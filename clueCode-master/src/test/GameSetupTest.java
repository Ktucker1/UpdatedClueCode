package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

 
import clueGame.*;
import clueGame.Card.CardType;

public class GameSetupTest {
	private ClueGame newGame;
	ArrayList<Player> players;
	
	@Before
	public void setUp(){
		newGame = new ClueGame();
		//newGame.loadConfigFiles();
		 players = newGame.getPlayers();
	}
	
	@Test
	public void ctestCards1(){//checks there are correct total number of cards
		System.out.println(newGame.getDeck() + " this is the deck");
		Assert.assertEquals(21, newGame.getDeck().size());
	}
	
	@Test
	public void dtestCards2(){ //checks there are correct number of each type of cards
		int people = 0;
		int rooms = 0;
		int weapons = 0;
		
		for(Card c : newGame.getDeck()){
			Card.CardType car = c.getCardType();
			switch(car){
			case ROOM:
				rooms++;
				break;
			case PERSON:
				people++;
				break;
			case WEAPON: 
				weapons++;
				break;			
			}			
		}
		
		Assert.assertEquals(6, people);//there are 6 people
		Assert.assertEquals(9, rooms);//there are 9 rooms
		Assert.assertEquals(6, weapons);// there are 6 weapons
		
	}
	
	@Test
	public void etestCards3(){ // tests to see if deck has 3 specific cards
		Card rope = new Card("Rope", Card.CardType.WEAPON);
		Card plum = new Card("Professor Plum", Card.CardType.PERSON);
		Card kitchen = new Card("Kitchen", Card.CardType.ROOM);
		Boolean r = false, p = false, k = false;
		for (Card c: newGame.getDeck()){
			if (rope.equals(c)){
				r = true;
			} else if (plum.equals(c)){
				p = true;
			}else if (kitchen.equals(c)){
				k = true;
			}
		}
		
		assertTrue(r);
		assertTrue(p);
		assertTrue(k);
		
	}
	
	@Test
	public void ftestMakeSol(){
		newGame.setSol();
	}
	
	@Test
	public void gtestPlayers(){
		int index = newGame.board.calcIndex(7,0);
		Player scarlett = newGame.getPlayers().get(0);
		Assert.assertEquals("Ms. Scarlett", scarlett.getName());
		Assert.assertEquals("Red", scarlett.getColor());
		Assert.assertEquals(index, scarlett.getLocation());
		
		index = newGame.board.calcIndex(24,6);
		Player green = newGame.getPlayers().get(4);
		Assert.assertEquals("Mr. Green", green.getName());
		Assert.assertEquals("Green", green.getColor());
		Assert.assertEquals(index, green.getLocation());
		
		index = newGame.board.calcIndex(0,7);
		Player plum = newGame.getPlayers().get(5);
		Assert.assertEquals("Professor Plum", plum.getName());
		Assert.assertEquals("Purple", plum.getColor());
		Assert.assertEquals(index, plum.getLocation());
		
		
	}
	
	@Test
	public void ideal1(){ //test all cards are dealt
		newGame.deal();
		Assert.assertEquals(0, newGame.getDeck().size());
		
	}
	
	@Test
	public void jdeal2(){ //tests all players have similar number of cars (+- 1 about)
		newGame.deal();
		Assert.assertEquals(players.get(0).getListOfCards().size(), players.get(3).getListOfCards().size(), 1);
		Assert.assertEquals(players.get(1).getListOfCards().size(), players.get(4).getListOfCards().size(), 1);
		Assert.assertEquals(players.get(2).getListOfCards().size(), players.get(5).getListOfCards().size(), 1);
	}
	 
	@Test
	public void kdeal3 (){ //tests no 1 card is dealt twice
		//tests if first players first card shows up in anyone else's hand
		newGame.deal();
		int count=0;
		//System.out.println(players.get(0).getListOfCards().size() + " players");
		Card test = players.get(0).getListOfCards().get(0);
		for(Player p: players){
			for (Card c: p.getListOfCards()){
				if (c.equals(test)){
					count++;
				}
			}
			
		}
		Assert.assertEquals(1, count);
		//tests if third players second card shows up in anyone else's hand
		count=0;
		test = players.get(2).getListOfCards().get(1);
		for(Player p: players){
			for (Card c: p.getListOfCards()){
				if (c.equals(test)){
					count++;
				}
			}
			
		}
		Assert.assertEquals(1, count);
		//tests if second players third card shows up in anyone else's hand
		count=0;
		test = players.get(1).getListOfCards().get(2);
		for(Player p: players){
			for (Card c: p.getListOfCards()){
				if (c.equals(test)){
					count++;
				}
			}
			
		}
		Assert.assertEquals(1, count);
	}
}
